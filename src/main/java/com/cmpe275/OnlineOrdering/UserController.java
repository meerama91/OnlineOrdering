package com.cmpe275.OnlineOrdering;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserService userSvc;

	@Autowired
	private AdminService adminSvc;
	
	@Autowired
	private MailSender mailOtp;
	
	@Autowired
	private SchedulerService schSvc;
	
	Utils u = new Utils();

	/** get the data for each menu item category
	 * @param model
	 * @return
	 * @author Somya
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/Menu/displayMenuItems", method = RequestMethod.GET)
	public String getDataForEachCategory(Model model, HttpServletRequest request)
			throws UnsupportedEncodingException {

		HttpSession session = request.getSession(false);
		if (session != null) {
			String name = (String) session.getAttribute("username");
			model.addAttribute("user", name);
		} else {
			model.addAttribute("user", "notset");
		}

		String category[] = { Utils.MAINCOURSE, Utils.DRINK, Utils.DESERT, Utils.APPETIZER };
		for (int i = 0; i < category.length; i++) {
			List<MenuItem> mi = userSvc.getMenuItems(category[i]);
			model.addAttribute("list_" + category[i].toString(), mi);
		}
		return "GetUserMenuItems";
	}

	/**
	 * mapping to the home page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("username")!=null) {
			model.addAttribute("user", session.getAttribute("username"));
			return "UserHome";
		}

		return "Login";

	}
	
	/**
	 * parse all the menu items from the request page
	 * @param orderid
	 * @param userid
	 * @return
	 */
	private Map<String, Integer> parseMenuItemsFromRequest(int orderid, int userid) {
		Map<String, Integer> menuitemMap = new TreeMap<String, Integer>();
		String menuitems = userSvc.getMenuDetailsForOrder(orderid, userid);
		if(menuitems == null)
			return null;
		menuitemMap = Utils.deserializeMenuItems(menuitems);
		return menuitemMap;
	}

	/**
	 * It will generate the Random Id, if the id exists, it will generate a new
	 * one.
	 * 
	 * @return the unique id
	 */
	private int getNextNonExistingOrderId(int user_id) {
		Random rn = new Random();
		rn.setSeed(System.currentTimeMillis());
		while (true) {
			int rand_id = rn.nextInt(Integer.SIZE - 1) % 10000;
			if (!userSvc.orderExists(user_id, rand_id)) {
				System.out.println("here");
				return rand_id;
			}
		}
	}

	/**
	 * This function will return the earliest pick up time for an order
	 * 
	 * @return
	 * @throws ParseException
	 * @author Somya
	 */
	@RequestMapping(value = "/Menu/Checkout", method = RequestMethod.POST)
	public String getEarliestPickUpTime(HttpServletRequest request, Model model, HttpServletResponse response)
			throws ParseException {

		int user_id = 0;
		HttpSession session = request.getSession(false);
		try {
			if (session != null) {
				user_id = (Integer) session.getAttribute("userID");
				model.addAttribute("userid", user_id);
			}
		} catch(Exception e) {
			return "Login";
		}

		String menuitems = request.getParameter("itemData");
		System.out.println("menuitems is-" + menuitems);
		if(menuitems.length() == 0) {
			session.removeAttribute("orderID");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			model.addAttribute("msg","No items in the cart, Select items -> Add to Cart -> Proceed to Checkout");
			return "OrderErrorException";
		}
		
		Map<String, Integer> mi = Utils.deserializeMenuItems(menuitems);
		List<String> daterange = Utils.getNextDates(Utils.getCurrdate());
		
		int orderid;
		if (session.getAttribute("orderID") != null) {
			orderid = (Integer) session.getAttribute("orderID");
			System.out.println("orderid is already set");
		} else {
			orderid = this.getNextNonExistingOrderId(user_id);
			session.setAttribute("orderID", orderid);
			System.out.println("orderid is set");
		}
	    
		int totalPrepTime = getTotalPrepTimeForMenu(mi);
		float totalPrice = this.getTotalPriceForMenu(mi);
		int earlytime = 0; // initialize with some random valid value
		String earlydate = "";
		String msg = "";
		boolean found = false;
		int currMin = Utils.getCurrTimeInMins();
		String menu_items_str = Utils.serializeMenuItems(mi);

		if (totalPrepTime > Utils.WORKINGDUR) {
			msg = "Order is too long to prepare in one day. Cannot accept the order. Please modify or cancel the order.";
			model.addAttribute("msg", msg);
			model.addAttribute("menulist", mi);
			model.addAttribute("orderid", orderid);
			model.addAttribute("totalprice", totalPrice);
			
			userSvc.placeOrder(user_id, 
					orderid, 
					menu_items_str,
					"pending",
					earlydate, 
					Utils.convertMinsToTime(earlytime), 
					totalPrice, 
					Utils.getCurrdate(),
					Utils.getCurrtime());

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "OrderError";
		}

		for (int id = 0; id < 30; id++) {
			earlytime = getEarliestPickupForDate(totalPrepTime,
					daterange.get(id), (id == 0 ? currMin : -1));

			if (earlytime > Utils.OPENTIME && earlytime <= Utils.CLOSETIME) {
				earlydate = daterange.get(id);
				System.out.println("Earliest pick up time is:" + earlytime
						+ " on:" + daterange.get(id));
				found = true;
				break;
			} else if(earlytime > Utils.WORKTIME && earlytime <= Utils.OPENTIME) {
				earlydate = daterange.get(id);
				earlytime = Utils.OPENTIME;
				found = true;
				break;
			}
		}

		if (!found) {
			msg = "Pick up for this order is not available till next 30 days! Please cancel or modify your order.";
			model.addAttribute("msg", msg);
			model.addAttribute("menulist", mi);
			model.addAttribute("orderid", orderid);
			model.addAttribute("totalprice", totalPrice);
			
			userSvc.placeOrder(user_id, 
					orderid, 
					menu_items_str,
					"pending",
					earlydate, 
					Utils.convertMinsToTime(earlytime), 
					totalPrice, 
					Utils.getCurrdate(),
					Utils.getCurrtime());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "OrderError";
		}

		// Adding Items to Model as well
		model.addAttribute("orderid", orderid);
		model.addAttribute("totalprice", totalPrice);
		model.addAttribute("earliestpickuptime", earlydate+" "+ Utils.convertMinsToTime(earlytime));
		model.addAttribute("menulist", mi);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("earlydate", format.parse(earlydate).toString().substring(0, 11));
		
		userSvc.placeOrder(user_id, 
				orderid, 
				menu_items_str,
				"pending",
				earlydate, 
				Utils.convertMinsToTime(earlytime), 
				totalPrice, 
				Utils.getCurrdate(),
				Utils.getCurrtime());

		return "checkout";
	}

	/**
	 * This function will return the earliest pick up time for any chef on one
	 * date
	 * 
	 * @param preptime
	 * @param date
	 * @return
	 * @author Somya
	 */
	private int getEarliestPickupForDate(int preptime, String date, int startMin) {
		int earlyTime = 9999;

		for (int chefid = 1; chefid <= 3; chefid++) {
			earlyTime = Math.min(earlyTime,
					getEarliestTimeForChef(chefid, date, preptime, startMin));
		}

		return earlyTime;
	}

	/**
	 * This function contains the algo, it will check the earliest time on a day
	 * for a chef.
	 * 
	 * @param chefid
	 * @param date
	 * @param preptime
	 * @return
	 * @author Somya
	 */
	private int getEarliestTimeForChef(int chefid, String date, int preptime,
			int startMin) {
		System.out.println("chefid:"+chefid+" starMin:"+startMin);
		Map<Integer, Integer> time = userSvc.getScheduleForChef(chefid, date);

		List<Integer> a = new ArrayList<Integer>();
		List<Integer> b = new ArrayList<Integer>();

		System.out.println("CHef Schedule:");
		for (Map.Entry<Integer, Integer> entry : time.entrySet()) {
			if (entry.getKey() < startMin || entry.getValue() < startMin) {
				startMin = Math.max(entry.getValue(), startMin);
				continue;
			}
			a.add(entry.getKey());
			b.add(entry.getValue());
			System.out.println(entry.getKey()+" "+entry.getValue());
		}

		int restOpen = Math.max(300, startMin);
		int restClose = 1260;

		// case where chef is all free
		if (a.size() == 0) {
			if (restOpen + preptime <= restClose)
				return restOpen + preptime;
			else
				return 9999;
		} else {
			// see if chef can accommodate it as the first order of the day
			if (a.get(0) - restOpen >= preptime) {
				// chef can do this as first meal of the day.
				return restOpen + preptime;
			}
			// see if chef can accommodate in the middle of the day schedule
			for (int i = 0; i < a.size() - 1; i++) {
				if (a.get(i + 1) - b.get(i) >= preptime)
					return b.get(i) + preptime;
			}
			// see if chef can accommodate in the end
			if (restClose - b.get(b.size() - 1) >= preptime)
				return b.get(b.size() - 1) + preptime;

			return 9999;
		}
	}

	/**
	 * This function will return the total prep time for a list of menu item
	 * names
	 * 
	 * @param MenuItemNames
	 * @return
	 * @author Somya
	 */
	private int getTotalPrepTimeForMenu(Map<String, Integer> MenuItemNames) {
		int totalPrepTime = 0;
		for (Map.Entry<String, Integer> entry : MenuItemNames.entrySet()) {
			// parse the order and get the total prep time for all items from database.
			int prepTime = adminSvc.getTotalPrepTimeForMenuItem(entry.getKey())
					* entry.getValue();
			totalPrepTime += prepTime;
		}

		return totalPrepTime;
	}

	/**
	 * This function will return the total price for a list of menu item names
	 * 
	 * @param MenuItemNames
	 * @return
	 * @author Somya
	 */
	private float getTotalPriceForMenu(Map<String, Integer> MenuItemNames) {
		float totalPrice = 0;
		for (Map.Entry<String, Integer> entry : MenuItemNames.entrySet()) {
			// parse the order and get the total prep time for all items from
			// database.
			float pr = adminSvc.getPriceForMenuItem(entry.getKey())
					* entry.getValue();
			totalPrice += pr;
		}

		System.out.println("Total price for the order is:" + totalPrice);
		return totalPrice;
	}

	/**
	 * This function will check if the order can be prepared at the custom time
	 * or not
	 * 
	 * @param time
	 * @param date
	 * @param preptime
	 * @return
	 * @author Somya
	 */
	@RequestMapping(value = "/Menu/finalCheckout", method = RequestMethod.POST)
	public String checkCustomTime(HttpServletRequest request, Model model, HttpServletResponse response) {

		//System.out.println("In Custom Time function : User Controller");
		// order id from session.
		int orderid;
		int user_id = 0;

		HttpSession session = request.getSession(false);
		if (session != null) {
			orderid = (Integer) session.getAttribute("orderID");
			user_id = (Integer) session.getAttribute("userID");
			//System.out.println("orderis id set in final checkout");
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return "OrderErrorException";
		}

		String datetime = "";
		String ordertype = request.getParameter("type");
		//System.out.println("<----Order Type::" + ordertype);
		if (ordertype.equals("confirm"))
			datetime = request.getParameter("early");
		else if (ordertype.equals("custom"))
			datetime = request.getParameter("time");
		else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			model.addAttribute("msg", "Choose one date time by selecting a radio button");
			model.addAttribute("orderid", orderid);
			return "OrderError";
		}

		String date = Utils.getDateFromDateTime(datetime);
		String time = Utils.getTimeFromDateTime(datetime);

		int pickuptime = 0;
		try {
			// changing the time in minutes from 24hour format
			pickuptime = Utils.getTimeinMins(time);
			System.out.println("Pickup entered by the user is:" + pickuptime);
			System.out.println("Current time in mins:" + Utils.getCurrTimeInMins());
			System.out.println("date is: " + date );
			System.out.println(Utils.getCurrdate());
			if ((date.equals(Utils.getCurrdate())) && (pickuptime <= Utils.getCurrTimeInMins())) {
				System.out.println("going inside check");
				model.addAttribute("msg", "Pickup time cannot be in past or current time");
				model.addAttribute("orderid", orderid);
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return "OrderError";
			}
			//System.out.println("Pickup time in mins::" + pickuptime);
		} catch (Exception e) {
			model.addAttribute("msg", "Enter a valid pickup date and time");
			model.addAttribute("orderid", orderid);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "OrderError";
		}

		Map<String, Integer> mi = this.parseMenuItemsFromRequest(orderid, user_id);
		//System.out.println("Menu Order Items:" + mi);
		if (mi.isEmpty() || mi == null) {
			model.addAttribute("msg", "Error occured in placing the order");
			session.removeAttribute("orderID");
			//System.out.println("Menu items is null or empty");
			userSvc.cancelOrderUnplaced(orderid, user_id);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "OrderErrorException";
		}

		int totalPrepTime = getTotalPrepTimeForMenu(mi);
		//System.out.println("Total prep time for order::" + totalPrepTime);

		Schedule sch = getAvailableScheduleForOrder(pickuptime, date, totalPrepTime);
		if (sch != null) {
			//System.out.println("Order Accepted..");
			String menu_items = Utils.serializeMenuItems(mi);

			// update the quantity in the menu item table
			userSvc.updateQuantity(mi);

			String newtime = Utils.convertMinsToTime(pickuptime);
			float totalPrice = this.getTotalPriceForMenu(mi);

			// update the order status in the database for this order.
			userSvc.placeOrder(user_id, orderid, menu_items, "Queued", date, newtime, totalPrice, Utils.getCurrdate(),
					Utils.getCurrtime());

			sch.setOrderid(orderid);
			sch.setDate(date);
			//System.out.println("Prep Start Time:" + sch.getBusystarttime());
			//System.out.println("Prep End Time:" + sch.getBusyendtime());
			//System.out.println("Chef ID:" + sch.getChefid());
			//System.out.println("Pickup time in mins:" + pickuptime);

			// add the order to the chef's schedule as well.
			userSvc.addOrderToChefSchedule(sch);

			// add the data in the menupopularity table as well.
			userSvc.addMenuPop(orderid, mi, Utils.getCurrdate(), Utils.getCurrtime());

			model.addAttribute("msg", "Order placement successful");
			session.removeAttribute("orderID");
			sendMail(user_id,orderid);
			return "OrderSuccess";
		} else {
			//System.out.println("No chef is free, ask him to modify the order");
			model.addAttribute("msg", "Order cannot be placed due to too many other orders at this time");
			model.addAttribute("orderid", orderid);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "OrderError";
		}
	}
	
	/**
	 * sends mail to the user with order details upon order success
	 * as a separate thread
	 * @param user id, order id
	 * @author Meera
	 */
	private void sendMail(int userid, int orderid) {
		// TODO Auto-generated method stub
		System.out.println("in sending mail id is " + userid);
		String email = userSvc.getEmail(userid);
		System.out.println("in sending mail, email is " + email);
		OrderDetails ord = schSvc.getOrder(orderid);
		System.out.println("in sending mail order is " + ord);
		if(email != "" && ord != null) {
			StringBuffer message = new StringBuffer("Thank you for ordering with us. Your order details are:\n");
			message.append("Order No. : " + orderid + "\n");
			
			String details = ord.getMenu_items();
			Map<String, Integer> m = Utils.deserializeMenuItems(details);
			for(String item: m.keySet()) {
				message.append(item);
				message.append(" : " + m.get(item) + "\n");
			}
			
			message.append("Pickup Date : " + ord.getpickup_date());
			message.append("\nPickup Time  : " + ord.getpickup_time());
			message.append("\nprice : " + ord.getPrice() + "$");
			
		
		Runnable r = new Notify(email, message.toString(), mailOtp);
		new Thread(r).start();
		} 
		
	}

	/**
	 * This method checks if any of the three chefs is free. If yes, then it
	 * will return the chef id of the free chef and the time when it can start
	 * to prepare
	 * 
	 * @return chefid who is free
	 * @author Somya
	 */
	private Schedule getAvailableScheduleForOrder(int pickuptime, String date,
			int preptime) {
		int chefid = 1;
		Schedule sch_1 = isChefFree(chefid, pickuptime, date, preptime);
		if (sch_1 == null) {
			Schedule sch_2 = isChefFree(chefid + 1, pickuptime, date, preptime);
			if (sch_2 == null) {
				Schedule sch_3 = isChefFree(chefid + 2, pickuptime, date,
						preptime);
				if (sch_3 == null) {
					return null;
				} else {
					sch_3.setChefid(chefid + 2);
					return sch_3;
				}
			} else {
				sch_2.setChefid(chefid + 1);
				return sch_2;
			}
		} else {
			sch_1.setChefid(chefid);
			return sch_1;
		}
	}

	/**
	 * Checks if a chef with the chef id is free for the date and time
	 * 
	 * @param chefid
	 * @return
	 * @author Somya
	 */
	private Schedule isChefFree(int chefid, int pickuptime, String date,int preptime) {

		//System.out.println("<------In function ischeffree---->");
		Map<Integer, Integer> time = new TreeMap<Integer, Integer>();
		time = userSvc.getScheduleForChef(chefid, date);

		int a[] = new int[time.size()];
		int b[] = new int[time.size()];
		int id = 0;
		for (Map.Entry<Integer, Integer> entry : time.entrySet()) {
			a[id] = entry.getKey();
			b[id] = entry.getValue();
			id++;
		}
		System.out.println("Chef:"+chefid+" schedule for:"+date);
		for(int i=0;i<a.length;i++)
			System.out.print(a[i]+"-"+b[i]);

		int minStartTime = 0;
		if(date.equals(Utils.getCurrdate()))
			minStartTime = Math.max(Math.max(pickuptime - preptime - 60, 300), Utils.getCurrTimeInMins());
		else
			minStartTime = Math.max(pickuptime - preptime - 60, 300);
		
		int maxStartTime = pickuptime - preptime;
		System.out.println("\nMinStartTime:" + minStartTime);
		System.out.println("MaxStartTime:" + maxStartTime);

		Schedule sch = new Schedule();

		// it means the chef is all free
		if (a.length == 0) {
			System.out.println("Yes, can accomodate, chef is all free!");
			sch.setBusystarttime(maxStartTime); // scheduled here late
			sch.setBusyendtime(pickuptime); // scheduled here late
			return sch;
		} else {
			// assuming all the start times are valid.
			// cases where the start time lies before the chef schedule start time
			if (minStartTime < a[0]) {
				if (maxStartTime <= a[0]) {
					if (maxStartTime == a[0]) {
						System.out.println("Yes, can be accomodated-1");
						sch.setBusystarttime(a[0] - preptime);
						sch.setBusyendtime(maxStartTime);
						return sch;
					} else {
						if (a[0] - maxStartTime >= preptime) {
							sch.setBusystarttime(maxStartTime);
							sch.setBusyendtime(pickuptime);
							return sch;
						} else if(minStartTime != maxStartTime) {
							sch.setBusystarttime(a[0] - preptime);
							sch.setBusyendtime(a[0]);
							return sch;
						} else 
							return null;
					}
				} else if(maxStartTime < b[0]) {
					if (a[0] - minStartTime >= preptime) {
						System.out.println("Will it ever come here?");
						sch.setBusystarttime(a[0] - preptime);
						sch.setBusyendtime(a[0]);
						return sch;
					} else {
						System.out.println("No, it cannot be accomodated");
						return null;
					}
				}
			}

			int maxLimit = 0;
			// cases where start time is within the chef schedule of the day
			for (int i = 0; i < a.length; i++) {

				// check if it is the last schedule of the day for this chef
				if (i == a.length - 1)
					maxLimit = 1260; // 9pm
				else
					maxLimit = a[i + 1];

				if (minStartTime <= b[i]) {
					if (((b[i] + preptime) <= maxLimit)
							&& ((b[i] + preptime) <= pickuptime)) {
						System.out.println("Yes, it can be accomodated-2");
						sch.setBusystarttime(Math.min(maxLimit, pickuptime)
								- preptime);
						sch.setBusyendtime(Math.min(maxLimit, pickuptime));
						return sch;
					} else if(maxStartTime <= maxLimit){
						System.out.println("No, it cannot be accomodated");
						return null;
					}
				} else {
					if (i <= a.length - 2 && minStartTime >= a[i + 1])
						continue;
					else {
						if (minStartTime + preptime <= maxLimit) {
							System.out.println("Yes, scheduling early");
							sch.setBusystarttime(minStartTime); 
							sch.setBusyendtime(minStartTime + preptime); 
							return sch;
						} else {
							System.out.println("No, it cant be accomodated");
							return null;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * This function will cancel the unplaced orders by the user.
	 * @param request
	 * @param model
	 * @return
	 * @author Somya
	 */
	@RequestMapping(value = "/Menu/cancelOrder", method = RequestMethod.POST)
	public String cancelOrder_Unplaced(HttpServletRequest request, Model model) {
		int orderid = Integer.parseInt(request.getParameter("orderId"));
		int user_id = 0;
		HttpSession session = request.getSession(false);
		try {
			if (session != null) {
				user_id = (Integer) session.getAttribute("userID");
				userSvc.cancelOrderUnplaced(orderid, user_id);
				model.addAttribute("msg", "Order has been successfully cancelled.");
			}
		} catch (Exception e) {
			model.addAttribute("msg", "Sorry order could not be cancelled.");
		} finally{
			session.removeAttribute("orderID");
			System.out.println("orderid removed after cancellation");
		}
		return "OrderSuccess";
	}
	
	/**
	 * This function will return the menu items ordered by the user in case he wants to revise the order
	 * @param request
	 * @param model
	 * @return
	 * @author Somya
	 */
	@RequestMapping(value = "/Menu/modifyOrder", method = RequestMethod.POST)
	public String modifyOrder(HttpServletRequest request, Model model, HttpServletResponse response) {
		int orderid = -1;
		int user_id = 0;
		HttpSession session = request.getSession(false);
		try {
			if (session != null) {
				user_id = (Integer) session.getAttribute("userID");
				orderid = (Integer)session.getAttribute("orderID");
				System.out.println("got orderid for modifying");
			}
		} catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return "OrderErrorException";
		}
		
		String menuItemDetails = userSvc.getMenuDetailsForOrder(orderid, user_id);
		if(menuItemDetails == null || menuItemDetails.length() == 0) {
			session.removeAttribute("orderID");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "OrderErrorException";
		}
		
		Map<String,Integer> mi = Utils.deserializeMenuItems(menuItemDetails);
		model.addAttribute("BulkList",mi);
		for(String s: mi.keySet()) {
			System.out.println("ModifyOrder:Order:" + s + mi.get(s));
		}
		
		String category[] = { Utils.MAINCOURSE, Utils.DRINK, Utils.DESERT, Utils.APPETIZER };
		for (int i = 0; i < category.length; i++) {
			List<MenuItem> milist;
			try {
				milist = userSvc.getMenuItems(category[i]);
				model.addAttribute("list_" + category[i].toString(), milist);
			} catch (UnsupportedEncodingException e) {
				session.removeAttribute("orderID");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return "OrderErrorException";
			}
		}
		return "GetUserMenuItems";
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/completedOrders", method = RequestMethod.GET)
	public String viewCompletedOrders(HttpServletRequest request,Model model){
		System.out.println("In function - View Completed Orders");
		int user_id = 0;
		HttpSession session = request.getSession(false);
		try {
			if (session != null) {
				user_id = (Integer) session.getAttribute("userID");
			}
		} catch(Exception e) {
		}
		
		List<OrderDetails> listod = userSvc.getUserOrders(user_id, "Fulfilled");
		List<Order> listo = new ArrayList<Order>();
		
		if(!listod.isEmpty()) {
			for(int i = 0; i < listod.size(); i++) {
				Order o = new Order(listod.get(i).getOrderid(),
						"",
						"",
						listod.get(i).getMenu_items(),
						listod.get(i).getStatus(),
						listod.get(i).getpickup_date(),
						listod.get(i).getpickup_time(),
						listod.get(i).getPrice());
				o.setMenumap(Utils.deserializeMenuItems(listod.get(i).getMenu_items()));
				listo.add(o);
			}
			model.addAttribute("orderlist",listo);
			return "ViewCompletedOrders";
		} else {
			model.addAttribute("msg","No orders to display");
			return "NoOrders";
		}
	}
	
	/**
	 * cancels order for user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteOrders", method = RequestMethod.POST) 
	public String deleteOrders(HttpServletRequest request, Model model) {
		String itemdata = request.getParameter("itemData");
		System.out.println(itemdata);
		
		String[] data = itemdata.split(";;");
		for(int i = 0; i < data.length; i++) {
			System.out.println("Data selected for delete is:"+ data[i]);
			adminSvc.deleteOrder(Integer.parseInt(data[i]));
			userSvc.cancelOrderChef(Integer.parseInt(data[i]));
			model.addAttribute("msg","Orders Cancelled Successfully.");
		}
		return "OrderCancelSuccess";
	}
	
	
	/**
	 * cancels order for user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.GET) 
	public String cancelOrder(HttpServletRequest request, Model model) {
		int user_id = 0;
		HttpSession session = request.getSession(false);
		try {
			if (session != null) {
				user_id = (Integer) session.getAttribute("userID");
			}
		} catch(Exception e) {
		}
		
		List<OrderDetails> listod = userSvc.getUserOrders(user_id, "Queued");
		List<Order> listo = new ArrayList<Order>();
		
		if(!listod.isEmpty()) {
		for(int i = 0; i < listod.size(); i++) {
			Order o = new Order(listod.get(i).getOrderid(),
					"",
					"",
					listod.get(i).getMenu_items(),
					listod.get(i).getStatus(),
					listod.get(i).getpickup_date(),
					listod.get(i).getpickup_time(),
					listod.get(i).getPrice());
			o.setMenumap(Utils.deserializeMenuItems(listod.get(i).getMenu_items()));
			listo.add(o);
		}
		model.addAttribute("orderlist",listo);
		return "CancelOrders";
		} else {
			model.addAttribute("msg","No orders to display");
			return "NoOrders";
		}
	}
	
	
	@RequestMapping(value = "/Menu", method = RequestMethod.GET)
	/**
	 * returns the home page
	 * 
	 * @return
	 * @author Somya
	 */
	public String home() {
		return "DisplayUserMenu";
	}
}
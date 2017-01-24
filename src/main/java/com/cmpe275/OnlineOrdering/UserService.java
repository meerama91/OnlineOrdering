package com.cmpe275.OnlineOrdering;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@PersistenceContext
	private EntityManager em;

	/**
	 * This method is used to get the details of a all menu items for a
	 * category.
	 * 
	 * @param category
	 *            of the menu item which is to be looked for
	 * @return Menu item details to the user view are returned
	 * @author Somya
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional
	public List<MenuItem> getMenuItems(String category) throws UnsupportedEncodingException {
		Query q = em.createQuery("Select m from MenuItem m where m.category=:arg1");
		q.setParameter("arg1", category);
		List<MenuItem> ret = new ArrayList<MenuItem>();

		try {
			@SuppressWarnings("unchecked")
			List<MenuItem> resultList = q.getResultList();

			for (int i = 0; i < resultList.size(); i++) {
				MenuItem mi = new MenuItem();
				mi.setName(resultList.get(i).getName());
				mi.setCategory(resultList.get(i).getCategory());
				mi.setCalories(resultList.get(i).getCalories());
				mi.setUnitprice(resultList.get(i).getUnitprice());

				byte[] binaryData;
				binaryData = resultList.get(i).getPicture();
				byte[] encodeBase64 = Base64.encodeBase64(binaryData);
				String base64Encoded = new String(encodeBase64, "UTF-8");
				mi.setpicpath(base64Encoded);
				ret.add(mi);
			}
		} catch (NoResultException e) {
			ret = null;
		}
		return ret;
	}
	
	/**
	 * It will add the order to the chef's schedule
	 * @param chefid
	 * @param orderid
	 * @param busyFrom
	 * @param busyTo
	 * @param date
	 * @author Somya
	 */
	@Transactional
	public void addOrderToChefSchedule(Schedule sch) {
		em.merge(sch);
	}
	
	/**
	 * check if the order exists
	 * @param user_id
	 * @param order_id
	 * @return
	 * @author Somya
	 */
	
	@Transactional
	public boolean orderExists(int user_id, int order_id) {
		OrderDetailsId or = new OrderDetailsId();
		or.userid = user_id;
		or.orderid = order_id;
		return em.find(OrderDetails.class, or) != null;
	}
	
	/**
	 * Update the database rows for the order id with the status confirmed (placed order)
	 * @param orderid
	 * @param status
	 * @param string 
	 * @param earlytime 
	 * @param totalPrice 
	 * @param currdate 
	 * @param currtime 
	 * @author Somya
	 */
	@Transactional
	public void placeOrder(int userid, 
			int orderid, 
			String menu_items, 
			String status,
			String earlydate, 
			String earlytime, 
			float totalPrice, 
			String currdate, 
			String currtime) {
		
		OrderDetails od = new OrderDetails();
		od.setUserid(userid);
		od.setOrderid(orderid);
		od.setStatus(status);
		od.setMenu_items(menu_items);
		od.setpickup_date(earlydate);
		od.setpickup_time(earlytime);
		od.setPrice(totalPrice);
		od.setOrderdate(currdate);
		od.setOrdertime(currtime);
		
		em.merge(od);
	}
	
	/**
	 * get the schedule for the chef
	 * @param chefid
	 * @param date
	 * @author Somya
	 * @return 
	 */
	@Transactional
	public Map<Integer, Integer> getScheduleForChef(int chefid, String date) {
		Query  q = em.createQuery("Select s from Schedule s where s.chefid=:arg1 and s.date=:arg2");
		q.setParameter("arg1", chefid);
		q.setParameter("arg2", date);
		
		Map<Integer, Integer>time = new TreeMap<Integer, Integer>();		
		try {
			@SuppressWarnings("unchecked")
			List<Schedule> resultList = q.getResultList();     
			for(int i = 0; i < resultList.size(); i++) {
				time.put(resultList.get(i).getBusystarttime(),resultList.get(i).getBusyendtime());
			}		
		} catch (NoResultException e) {
			time = null;
		}
		return time;

	}

	/**
	 * cancel the unplaced order for the user and order
	 * @param orderid
	 * @param userid
	 * @author Somya
	 */
	@Transactional
	public void cancelOrderUnplaced(int orderid, int userid) {
		Query q = em.createQuery("Delete from OrderDetails where orderid=:arg1 and userid=:arg2");
		q.setParameter("arg1", orderid);
		q.setParameter("arg2", userid);
		q.executeUpdate();
	}

	/**
	 * get the menu details for the order
	 * @param orderid
	 * @param userid
	 * @return
	 * @author Somya
	 */
	@Transactional
	public String getMenuDetailsForOrder(int orderid, int userid) {
		System.out.println("UserService:GetMenuDetailsForOrder: orderid-userid:"+orderid+" "+userid);
		try {
			Query q = em.createQuery("Select od from OrderDetails od where od.orderid=:arg1 and od.userid=:arg2");
			q.setParameter("arg1", orderid);
			q.setParameter("arg2", userid);
			OrderDetails od = new OrderDetails();
			od = (OrderDetails) q.getSingleResult();
			return od.getMenu_items();
		} catch(Exception ex) {
			System.out.println(ex.getStackTrace().toString());
			return null;
		}
	}

	/**
	 * get all the orders for the users
	 * @param user_id
	 * @param status
	 * @return
	 * @author Somya
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<OrderDetails> getUserOrders(int user_id, String status) {
		System.out.println("user_id is:"+user_id+" status:"+status);
		try {
			Query q = em.createQuery("SELECT od FROM OrderDetails od WHERE od.userid = :arg1 and od.status=:arg2");
			q.setParameter("arg1", user_id);
			q.setParameter("arg2", status);
			List<OrderDetails> orderlist = q.getResultList();
			System.out.println(orderlist);
			return orderlist;
		} catch(Exception ex) {
			return null;
		}
	}

	/**
	 * update the quantity of the ordered items
	 * 
	 * @param mi
	 * @author Somya
	 */
	@Transactional
	public void updateQuantity(Map<String, Integer> mi) {
		try {
			for (Entry<String, Integer> entry : mi.entrySet()) {
				String key = entry.getKey();
				Integer value = (Integer) entry.getValue();

				Query q = em.createQuery(
						"Update MenuItem mi Set mi.ordercount = mi.ordercount + :arg1 where mi.name = :arg2");
				q.setParameter("arg1", value);
				q.setParameter("arg2", key);
				q.executeUpdate();
			}
		} catch (Exception ex) {

		}
	}

	@Transactional
	public void addMenuPop(int orderid, Map<String, Integer> mi, String currdate, String currtime) {
		for (Entry<String, Integer> entry : mi.entrySet()) {
			MenuPopularity mp = new MenuPopularity();

		    String key = entry.getKey();
		    Integer value = (Integer) entry.getValue();
		    String category = getCategoryForMenuItem(key);
		    mp.setOrderid(orderid);
		    mp.setOrderdate(currdate);
		    mp.setOrdertime(currtime);
		    mp.setName(key);
		    mp.setQuantity(value);
		    System.out.println("category->"+category);
		    mp.setCategory(category);
		    
		    em.merge(mp);
		    
		}
	}

	@Transactional
	private String getCategoryForMenuItem(String name) {
		try {
			Query q = em.createQuery("Select category from MenuItem mi where mi.name = :arg1");
			q.setParameter("arg1", name);
			return (String) q.getSingleResult();
		}catch(Exception ex)
		{
			return null;
		}
	}

	/**
	 * gets the email of the user for a particular userid
	 * @param user id
	 * @author Meera
	 */
	@Transactional
	public String getEmail(int id) {
	
			try {
				Query q = em.createQuery("Select email from UserCredentials uc where uc.id = :arg1");
				q.setParameter("arg1", id);
				String mail = (String)q.getSingleResult();
				return mail;
			}catch(Exception ex)
			{
				System.out.println("error in gettig email is " + ex);
				return "";
			}
		
	}

	@Transactional
	/**
	 * 
	 * @param orderid
	 */
	public void cancelOrderChef(int orderid) {
		System.out.println("orderid-------------------->"+orderid);
		Query q = em.createQuery("Delete from Schedule where orderid=:arg1");
		q.setParameter("arg1", orderid);
		q.executeUpdate();		
	}

	
}

package com.cmpe275.OnlineOrdering;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@EnableScheduling
@RequestMapping("/")
public class AdminController {
	
	@Autowired
	private AdminService adminSvc;
	Utils u = new Utils();

	 /** get data request for some menu name will be mapped here
	 * @return It will return the required view
	 * @author Somya
	 */
	@RequestMapping(value = "/searchItem", method = RequestMethod.POST)
	public String getData(HttpServletRequest request, Model model, HttpServletResponse response) {
		String name = request.getParameter("name");
		MenuItem mi = adminSvc.getMenuItem(name);
		
		if (mi == null) {
			model.addAttribute("name",name);
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "ErrorFindMenuItem";
		}
		
        byte[] binaryData;
		try {
			binaryData = mi.getPicture();
	        byte[] encodeBase64 = Base64.encodeBase64(binaryData);
	        String base64Encoded = new String(encodeBase64, "UTF-8");
	        // add outputString to model and show it is on page
	        model.addAttribute("picPath", base64Encoded);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("name", name);
		model.addAttribute("id",mi.getId());
		model.addAttribute("category", mi.getCategory());
		model.addAttribute("calories", mi.getCalories());
		model.addAttribute("picture", mi.getPicture());
		model.addAttribute("unitprice", Float.toString(mi.getUnitprice()));
		model.addAttribute("preptime", mi.getPreptime());

		return "DeleteMenuItem";
	}
	
	/**
	 * Request for adding the menu item will be mapped here
	 * @param request
	 * @return view
	 * @author Somya
	 */
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public String addMenuItem(HttpServletRequest request,
			 					@RequestParam CommonsMultipartFile fileUpload,
			 					HttpServletResponse response,
			 					Model model) {
		
		int id = this.getNextNonExistingNumber();
		if(!adminSvc.exists(request.getParameter("name"))) {
			adminSvc.add(setParams(request, id, fileUpload
			));
		} else {
			model.addAttribute("msg", "Error! Menu Item already exists.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "AdminError";
		}
		
		
		if(validateImageFile(fileUpload))
			return "AddMenuItem";
		else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "ImageUploadError";
		}
	}

	/**
	 * This will reset all the orders placed in the system and clear the chef schedule
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String adminreset(Model model) {
		adminSvc.systemreset();
		return "ResetSuccess";
	}
	
	@RequestMapping(value = "/orderReport", method = RequestMethod.GET)
	/**
	 * returns the order report page
	 * @return
	 */
	public String orderreport() {
		return "orderReport";
	}
	
	
	
	/**
	 * This will show the orders to the admin
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/vieworders", method = RequestMethod.POST)
	public String viewOrders(Model model, HttpServletRequest request) {
		String startdate = request.getParameter("startDate");
    	String enddate = request.getParameter("endDate");
    	String sortby = request.getParameter("sortby");
    	System.out.println(startdate+" "+enddate+" "+sortby);
		
		System.out.println("In view all orders : Admin Controller.");
		List<Order> od = adminSvc.getAllOrders(startdate, enddate, sortby);
		if (od == null) {
			model.addAttribute("msg", "Currently there are no orders to be displayed");
		} else {
			model.addAttribute("orderlist", od);
		}
		return "ViewAllOrders";
	}

	
	
	/**
	 * Validate if the image file is valid or not
	 * @param fileUpload
	 * @return
	 * @author Somya
	 */
	private boolean validateImageFile(CommonsMultipartFile fileUpload) {
		if (fileUpload != null && fileUpload.getSize() > 0) {
			return true;
		} else
			return false;
	}

	/**
	 * It will generate the Random Id, if the id exists, it will generate a new one.
	 * @return the unique id
	 * @author Somya
	 */
	private int getNextNonExistingNumber() {
		Random rn = new Random();
		rn.setSeed(System.currentTimeMillis());
		while (true) {
			int rand_id = rn.nextInt(Integer.SIZE - 1) % 10000;
			if (!adminSvc.existsById(rand_id)) {
				return rand_id;
			}
		}
	}

	/**
	 * It will set the params in the profile object from the servlet request object
	 * @param request
	 * @param id
	 * @param fileUpload 
	 * @return the profile object will all the parameters
	 * @author Somya
	 */
	private MenuItem setParams(HttpServletRequest request, int id, CommonsMultipartFile fileUpload) {
		MenuItem mi = new MenuItem();
		mi.setId(id);
		mi.setName(request.getParameter("name"));
		mi.setCategory(request.getParameter("category"));
		mi.setCalories(request.getParameter("calories"));
		int ptime = Integer.parseInt(request.getParameter("preptime"));
		mi.setPreptime(ptime);
		mi.setUnitprice(Float.parseFloat(request.getParameter("unitprice")));
		mi.setPicture(fileUpload.getBytes());
		return mi;
	}	

	/**
	 * It will delete the entry of the menu item passed to it by the menu item
	 * name
	 * 
	 * @param name : menu item name
	 * @param model
	 * @return view
	 * @author Somya
	 */
	@RequestMapping(value = "/deleteMenuItem", method = RequestMethod.POST)
	public String deleteMenuItem(HttpServletRequest request, Model model) {
		String itemdata = request.getParameter("itemData");
		System.out.println(itemdata);
		
		String[] data = itemdata.split(";;");
		for(int i = 0; i < data.length; i++) {
			System.out.println("Data selected for delete is:"+ data[i]);
			adminSvc.delete(data[i]);
		}
		return "SuccessDeleteMenuItem";
	}
	
	/**
	 * It will show all the menu items to the admin which are available for delete
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/AdminDelete", method = RequestMethod.GET)
	public String showAllMenuItemsForDelete(Model model){		
		List<MenuItem>itemlist = adminSvc.getAllMenuItems();
		model.addAttribute("itemlist",itemlist);
		System.out.println(itemlist.get(0).getName());
		return "DeleteMenuItem";
	}
    
	/**
	 * Admin can search for a menu item
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/searchIt", method = RequestMethod.GET)
    public String searchIt(HttpServletRequest request) {
        System.out.println("entered register home");
        return "SearchMenuItem";
    }
    
    /**
     * Add a menu item for the admin
     * @param request
     * @return
     */
    @RequestMapping(value = "/addIt", method = RequestMethod.GET)
    public String addIt(HttpServletRequest request) {
        System.out.println("entered register home");
        return "AddMenuItem";
    }
    
   
    
    /**
     * Logout for the admin
     * @param request
     * @return
     */
    @RequestMapping(value = "/adminLogout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        System.out.println("entered register home");
        return "Login";
    }
    
    
    @RequestMapping(value = "/popularityReport", method = RequestMethod.POST)
	public String viewPopularityReport(HttpServletRequest request, Model model) {
    	String startdate = request.getParameter("startDate");
    	String enddate = request.getParameter("endDate");
    	
    	System.out.println(startdate+" "+enddate);
    	
    	String category[] = { u.MAINCOURSE, u.DRINK, u.DESERT, u.APPETIZER };
		for (int i = 0; i < category.length; i++) {
			List<MenuPopularity> mp = adminSvc.getPopMenuItems(startdate, enddate, category[i]);
			model.addAttribute("list_" + category[i].toString(), mp);
		}
		return "viewPopReport";
	}
	
    @RequestMapping(value = "/popReport", method = RequestMethod.GET)
	/**
	 * returns the home page
	 * @return
	 */
	public String popreport() {
		return "popReport";
	}
    
    
    
	@RequestMapping(value = "/AdminHome", method = RequestMethod.GET)
	/**
	 * returns the home page
	 * @return
	 */
	public String home() {
		return "AdminHome";
	}
}
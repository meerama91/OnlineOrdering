package com.cmpe275.OnlineOrdering;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class Scheduler {

	private List<Order> orders = new ArrayList<Order>();

	@Autowired
	SchedulerService schSvc;
	
	@Autowired
	private MailSender mailOtp;

	
	/**
	 * It is the scheduler which runs every minute , checking the order times
	 * and changing the status accordingly and sending notifications.
	 * @author Meera
	 */
	@Scheduled(cron = "58 * * * * *")
	public void scheduleStatus() {

		String currDate = Utils.getCurrdate();

		System.out.println("The date is now " + currDate);//add condition for not proceeding in the night
		
		orders = schSvc.getAllOrders(currDate);
		if (orders != null && !orders.isEmpty()) {
			for (Order o : orders) {
				System.out.println("the oder id is" + o.getOrderid());
				int startTime = Utils.getTimeinMins(o.getPrepStartTime());
				int endTime = Utils.getTimeinMins(o.getPrepEndTime());
				int pickupTime = Utils.getTimeinMins(o.getpickup_time());

				String status = statuscheck(startTime, endTime, pickupTime,
						o.getEmail(), o.getOrderid());
				System.out.println("statsu is " + status);
				updateStatus(status, o);
			}
		} else {
			System.out.println("its null");
		}
	}

	/**
	 * updates the status of the order and 
	 * remove fulfilled orders from chef's schedule.
	 * @param order, status
	 * @author Meera
	 */
	private void updateStatus(String status, Order ord) {
		
		if (status != "") {
			OrderDetails ordd = schSvc.getOrder(ord.getOrderid());
			if (ordd != null) {
				ordd.setStatus(status);
				schSvc.updateStatus(ordd);
				
			}
		}
	}

	/**
	 * checks the times and decides the status 
	 * of the order.
	 * @param start time, end time, pickup time,
	 * and email id of the user of that order. 
	 * @author Meera
	 */
	private String statuscheck(int startTime, int endtime, int pickuptime,
			String mail, int orderid) {
	
		int currTime = Utils.getCurrTimeInMins();
		System.out.println("current time is " + currTime);
		System.out.println("start time is " + startTime); //check sending mail part.
		if (currTime == startTime) {
			StringBuffer message = new StringBuffer(
					"Your order preparation for Order No. " + orderid);
			message.append(" has begun! ");
			message.append("It is ready to be picked up soon at ");
			message.append(Utils.convertMinsToTime(pickuptime));
			message.append(" hrs");
			System.out.println("sending mail that is has started to prepare");
  		 Runnable r = new Notify(mail, message.toString(), mailOtp);
			new Thread(r).start();

		}
		if (currTime >= startTime && currTime < endtime) {

			return "Inprocess";

		} else if (currTime >= endtime && currTime < pickuptime) {
			return "Prepared";
		} else if (currTime >= pickuptime) {
			return "Fulfilled";
		} else
			return "";

	}

}

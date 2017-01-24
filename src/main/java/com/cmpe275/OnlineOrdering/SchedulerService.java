package com.cmpe275.OnlineOrdering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchedulerService {

	@PersistenceContext
	private EntityManager em;

	/**
	 * get the order details of the order with the particular id.
	 * @param orderid
	 * @author Meera
	 */
	@Transactional
	public OrderDetails getOrder(int orderid) {

		Query q = em
				.createQuery("Select o from OrderDetails o where o.orderid=:arg1");
		q.setParameter("arg1", orderid);
		System.out.println("orderid in schsvc getorder is " + orderid );
		OrderDetails ob;
		try {
			ob = (OrderDetails) q.getSingleResult();
		} catch (Exception e) {
			System.out.println("error in get order is " + e);
			ob = null;
		}
		return ob;
	}

	/**
	 * update the status of the order
	 * @param order details
	 * @author Meera
	 */
	@Transactional
	public void updateStatus(OrderDetails ord) {
		
		em.merge(ord);

	}

	
	
	/**
	 * gets all orders scheduled for today by joining
	 * with all other order details.
	 * @param date
	 * @author Meera
	 */
	@Transactional
	public List<Order> getAllOrders(String date) {	
		
		String str = " order by s.busystarttime desc";
		
			
		Query q = em.createQuery("SELECT d.orderid, "
				+ "c.fullname,"
				+ "c.email,"
				+ "d.menu_items,"
				+ "d.status,"
				+ "d.pickup_date,"
				+ "d.pickup_time,"
				+ "d.price,"
				+ "s.busystarttime,"
				+ "s.busyendtime,"
				+ "d.orderdate "
				+ " FROM UserCredentials c, "
				+ "OrderDetails d, "
				+ "Schedule s WHERE "
				+ "c.id = d.userid "
				+ "and "
				+ "d.orderid = s.orderid"
				+ " and "
				+ " s.date = :arg1 "
				+ str);
		q.setParameter("arg1", date);
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = q.getResultList();
		List<Order> result = new ArrayList<Order>(resultList.size());
		for (Object[] row : resultList) {
			String busystarttime = Utils.convertMinsToTime((Integer)row[8]);
			String busyendtime = Utils.convertMinsToTime((Integer)row[9]);
			
		    result.add(new Order((Integer) row[0],
		                            (String) row[1],
		                            (String) row[2],
		                            (String) row[3],
		                            (String) row[4],
		                            (String) row[5],
		                            (String) row[6],
		                            (Float)  row[7],
		                            busystarttime,
		                            busyendtime,
		                            (String) row[10]
		    		));
		}
		
		for(int i = 0; i < result.size(); i++) {
			String menuItems = result.get(i).getMenu_items();
			Map<String, Integer> m = Utils.deserializeMenuItems(menuItems);
			result.get(i).setMenumap(m);
		}

		return result;
	}

}

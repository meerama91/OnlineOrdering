package com.cmpe275.OnlineOrdering;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Utils {
	
	public final static String MAINCOURSE = "maincourse";
	public final static String DESERT = "desert";
	public final static String APPETIZER = "appetizer";
	public final static String DRINK = "drink";
	public final static String ORDERPLACED = "PLACED";
	public final static int WORKINGDUR = 960;
	public final static int OPENTIME = 360;
	public final static int CLOSETIME = 1260;
	public final static int WORKTIME = 300;
	

	/**
	 * get the current system date
	 * 
	 * @return
	 * @author Somya
	 */
	public static String getCurrdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String curdate = df.format(new Date());
		return curdate;
	}

	/**
	 * get the current system time
	 * 
	 * @return
	 * @author Somya
	 */
	public static String getCurrtime() {
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		Date now = new Date();
		String currtime = sdfTime.format(now);
		return currtime;
	}

	/**
	 * This function will take the current date and return a list of dates for
	 * next 30 days.
	 * 
	 * @param curDate
	 * @return
	 * @throws ParseException
	 * @author Somya
	 */
	public static List<String> getNextDates(String curDate) throws ParseException {
		List<String> dateList = new ArrayList<String>();
		dateList.add(curDate);

		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 29; i++) {
			final Date date = format.parse(curDate);
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			dateList.add(format.format(calendar.getTime()));
			curDate = format.format(calendar.getTime());
		}
		return dateList;
	}

	/**
	 * removing the unnecessary spaces in the order details if any
	 * 
	 * @param the menu item name
	 * @return menu item name without trailing whitespace.
	 * @author Meera
	 */
	public static String removeSpace(String part) {

		if (Character.isWhitespace(part.charAt(0))) {
			part = part.substring(1);
			return part;
		} else
			return part;

	}

	/**
	 * 
	 * @param mi
	 * @return
	 * @author Somya
	 */
	public static Map<String, Integer> deserializeMenuItems(String mi) {
		if (mi.length() == 0 || mi == null)
			return new HashMap<String, Integer>();

		String[] items = mi.split(";;");
		Map<String, Integer> menu_items = new TreeMap<String, Integer>();
		for (String item : items) {
			String[] parts = item.split("::");
			parts[0] = removeSpace(parts[0]);
			menu_items.put(parts[0], Integer.parseInt(parts[1]));
		}
		return menu_items;
	}

	/**
	 * 
	 * @param menuItems
	 * @return
	 * @author Somya
	 */
	public static String serializeMenuItems(Map<String, Integer> menuItems) {
		String serializedString = " ";
		for (Map.Entry<String, Integer> entry : menuItems.entrySet()) {
			serializedString += entry.getKey() + "::"
					+ Integer.toString(entry.getValue());
			serializedString += ";;";
		}
		return serializedString;
	}

	/**
	 * convert the mins to the time
	 * 
	 * @param earlytime
	 * @return
	 * @author Somya
	 */
	public static String convertMinsToTime(int earlytime) {
		String startTime = "00:00";
		int h = earlytime / 60 + Integer.parseInt(startTime.substring(0, 2));
		int m = earlytime % 60 + Integer.parseInt(startTime.substring(3, 5));
		String hr = String.valueOf(h);
		String min = String.valueOf(m);

		if (h <= 9)
			hr = "0" + h;
		if (m <= 9)
			min = "0" + m;

		String newtime = hr + ":" + min;
		return newtime;
	}

	/**
	 * get the curr time in mins
	 * 
	 * @return
	 * @author Somya
	 */

	public static int getCurrTimeInMins() {
		Calendar cal = Calendar.getInstance();
		int currMin = cal.get(Calendar.HOUR_OF_DAY) * 60
				+ cal.get(Calendar.MINUTE);
		return currMin;
	}

	/**
	 * get the date from the date time
	 * 
	 * @param datetime
	 * @return
	 * @author Somya
	 */
	public static String getDateFromDateTime(String datetime) {
		// parsing the date and time from request
		String date = "";
		String parts[] = datetime.split(" ");
		if (parts.length == 2) {
			date = parts[0];
		} else {
			date = null;
		}
		return date;
	}

	/**
	 * get the time from date time
	 * 
	 * @param datetime
	 * @return
	 * @author Somya
	 */
	public static String getTimeFromDateTime(String datetime) {
		// parsing the date and time from request
		String time = "";
		String parts[] = datetime.split(" ");
		if (parts.length == 2) {
			time = parts[1];
		} else {
			time = null;
		}
		return time;
	}

	/**
	 * Check whether the email and password belong to admin credentials
	 * 
	 * @param email
	 *            , password
	 * @return boolean variable
	 * @author Somya
	 */

	public static boolean isAdmin(String email, String password) {
		// TODO Auto-generated method stub
		if (email.equals("admin") && password.equals("admin"))
			return true;
		return false;
	}

	/**
	 * It will generate a new random verification code.
	 * 
	 * @param none
	 * @return code generated
	 * @author Meera
	 */
	public static String generateCode() {

		StringBuffer code = new StringBuffer("");
		Random randomGenerator = new Random();
		for (int i = 0; i <= 3; ++i) {
			int randomInt = randomGenerator.nextInt(10);
			code.append(Integer.toString(randomInt));
		}
		return code.toString();
	}

	/**
	 * It will generate the domain name for the respective mobile number
	 * carrier.
	 * 
	 * @param carrier
	 *            name
	 * @return domain name
	 * @author Meera
	 */
	public static String getDomain(String carrier) {
		
		String email = "";
		if (carrier.equals("ve")) {
			email = "@vtext.com";
		} else if (carrier.equals("at")) {
			email = "@txt.att.net";
		} else if (carrier.equals("tm")) {
			email = "@tmomail.net";
		}
		return email;
	}

	public static int getTimeinMins(String time) {
		String parts[] = time.split(":");
		return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
	}
}

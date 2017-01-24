package com.cmpe275.OnlineOrdering;

import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private MailSender mailOtp;

	@Autowired
	private LoginService loginSvc;

	/**
	 * It will return the first login page which the user sees.
	 * 
	 * @param request
	 *            : http request
	 * @param model
	 * @return view
	 * @author Meera
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getData(HttpServletRequest request, Model model) {
		//System.out.println("entered user home");
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("username") != null) {
			System.out.println("the session is" + session.getId());
			return "AlreadyLogged";
		}
		return "Login";
	}

	/**
	 * It will check the user credentials, and decide whether it is an admin or
	 * a registered user and accordingly return the views.
	 * 
	 * @param email
	 *            , password
	 * @param model
	 * @return view
	 * @author Meera
	 */
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public String userLogin(HttpServletRequest request, Model model,HttpServletResponse response) {
		Password p = new Password();
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (Utils.isAdmin(email, password)) {
			return "AdminHome";
		}
		UserCredentials uc = loginSvc.getUser(email);

		if (uc == null) {
			model.addAttribute("msg","User is not registered. Register with us now!");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return "usernotfound";
		} else {
			try {
				if (p.checkPassword(password, uc.getPassword())) {
				} else {
					model.addAttribute("msg", "Invalid Password. Try again!");
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					return "usernotfound";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpSession session = request.getSession();
		if (session.isNew())
			System.out.println("yes");
		else
			System.out.println("no");
		session.setAttribute("userID", uc.getId());
		session.setAttribute("username", uc.getFullname());
		session.setAttribute("useremail", uc.getEmail());
		model.addAttribute("user", uc.getFullname());
		return "UserHome";

	}

	/**
	 * It will return the registration view.
	 * 
	 * @param http
	 *            request
	 * @return view
	 * @author Meera
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(HttpServletRequest request) {
		//System.out.println("entered register home");
		return "registration";
	}

	/**
	 * It will successfully logout the user, and invalidate the sessions.
	 * 
	 * @param
	 * @return
	 * @author Meera
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, Model model,
			HttpServletResponse response) {
		//System.out.println("entered register home");
		HttpSession session = request.getSession();
		if (session != null) {
			String name = (String) session.getAttribute("username");
			model.addAttribute("user", name);
			session.invalidate();

		} else {
			model.addAttribute("user", "you have already logged out!");
		}
		return "logout";
	}

	/**
	 * It will verify the user verification code entered, and successfully
	 * register the user.
	 * 
	 * @param user
	 *            details
	 * @return success or error message
	 * @author Meera
	 */
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest request, Model model, HttpServletResponse response)
			throws Exception {
		Password p = new Password();
		//System.out.println("entered registration into db");
		String email = request.getParameter("email");

		// try to get the user from db
		TempUser t = loginSvc.getTuser(email);
		String codeAssigned = "";
		if (t != null)
			codeAssigned = t.getCode();
		else {
			model.addAttribute("msg",
					"Please generate verification code and register.");
			return "AfterRegisterClick";
		}

		String code = request.getParameter("verCode");

		if (existingUser(email)) {
			model.addAttribute("msg",
					"This email has already been registered. Please proceed to login!");
			loginSvc.delTuser(t);
		} else {
			if (codeAssigned.equals(code)) {
				model.addAttribute(
						"msg",
						"You have registered successfully.Please proceed to login by clicking the login button!");
				UserCredentials user = new UserCredentials();
				user.setEmail(email);
				user.setAddress(request.getParameter("address"));
				user.setFullname(request.getParameter("fullname"));

				// Storing the hash of the password in the database
				String userPassword = request.getParameter("password");
				String hashPassword = p.getPasswordHash(userPassword);
				user.setPassword(hashPassword);

				user.setPhone(request.getParameter("phone"));
				user.setId(getNextNonExistingNumber());
				loginSvc.adduser(user);
				loginSvc.delTuser(t);

			} else {
				model.addAttribute(
						"msg",
						"Invalid verification code! Please click register button to register again.");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);

			}
		}
		return "AfterRegisterClick";
	}

	/**
	 * will check if the user is already registered.
	 * 
	 * @param email
	 * @return boolean value
	 * @author Meera
	 */

	private boolean existingUser(String email) {

		UserCredentials uc = loginSvc.getUser(email);
		if (uc == null)
			return false;
		return true;
	}

	/**
	 * It will send the verification code as email to the user.
	 * 
	 * @param code
	 *            , email
	 * @return none
	 * @author Meera
	 */
	private void sendMail(String code, String email) {

		StringBuffer message = new StringBuffer("Your verification code: ");
		message.append(code);
		SimpleMailMessage verifyMail = new SimpleMailMessage();
		verifyMail.setFrom("group5.275@gmail.com");
		verifyMail.setTo(email);
		verifyMail
				.setSubject("Online Ordering Registration - Verification Code");
		verifyMail.setText(message.toString());
		mailOtp.send(verifyMail);
		System.out.println("mail sent!");

	}

	/**
	 * It will generate the Random Id. If the id exists for temperory user, it
	 * will generate a new one.
	 * 
	 * @return the unique id
	 * @author Meera
	 */
	private int getNextNonExistingNumberTuser() {
		Random rn = new Random();
		rn.setSeed(System.currentTimeMillis());
		while (true) {
			int rand_id = rn.nextInt(Integer.SIZE - 1) % 10000;
			if (!loginSvc.existsByIdTuser(rand_id)) {
				return rand_id;
			}
		}
	}

	/**
	 * It will generate the Random Id for the new User who is successfully
	 * registering, if the id exists, it will generate a new one.
	 * 
	 * @return the unique id
	 * @author Meera
	 */
	private int getNextNonExistingNumber() {
		Random rn = new Random();
		rn.setSeed(System.currentTimeMillis());
		while (true) {
			int rand_id = rn.nextInt(Integer.SIZE - 1) % 10000;
			if (!loginSvc.existsById(rand_id)) {
				return rand_id;
			}
		}
	}

	/**
	 * It will send verification code as SMS to the user.
	 * 
	 * @param cerification
	 *            code, sms email according to mobile carrier
	 * @return none
	 * @author Meera
	 */
	private void sendSms(String code, String smsEmail) {

		StringBuffer message = new StringBuffer("Your verification code: ");
		message.append(code);
		SimpleMailMessage verifyMail = new SimpleMailMessage();
		verifyMail.setFrom("group5.275@gmail.com");
		verifyMail.setTo(smsEmail);

		verifyMail.setText(message.toString());
		mailOtp.send(verifyMail);
		System.out.println("sms sent!");

	}

	/**
	 * It will store the generated code and details about the user who is in the
	 * process of registering.
	 * 
	 * @param email
	 *            , phone, phone carrier name
	 * 
	 * @return none
	 * @author Meera
	 */
	@RequestMapping(value = "/verifyMail", method = RequestMethod.POST)
	public void verifyMail(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("entered otp! " + request.getParameter("phone"));
		String code = Utils.generateCode();
		String email = request.getParameter("email");

		String carrierMail = Utils.getDomain(request.getParameter("carrier"));

		sendMail(code, email);
		if (!carrierMail.isEmpty()) {

			sendSms(code, request.getParameter("phone") + carrierMail);
		}
		TempUser t = loginSvc.getTuser(email);

		if (t == null) {
			t = new TempUser();
			t.setCode(code);
			t.setEmail(email);
			t.setId(getNextNonExistingNumberTuser());
			loginSvc.addTuser(t);
		} else {
			t.setCode(code);
			loginSvc.updateTuser(t);
		}

	}

}

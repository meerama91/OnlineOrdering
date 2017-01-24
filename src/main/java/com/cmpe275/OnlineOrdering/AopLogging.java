package com.cmpe275.OnlineOrdering;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class AopLogging {
	
	/**
	 * Log before entering any function executed by User controller
	 * 
	 * @author Meera
	 */
	
	@Before("execution(* com.cmpe275.OnlineOrdering.UserController.*(..))")
	public void loggingBeforeU(JoinPoint jp) {
		System.out.println("USER CONTROLLER LOG: Entered :" + jp.getSignature().getName() );
	}
	
	/**
	 * Log after successfully executing any function executed by User controller
	 * 
	 * @author Meera
	 */
	
	@AfterReturning("execution(* com.cmpe275.OnlineOrdering.UserController.*(..))") 
	public void loggingAfterU(JoinPoint jp) {
		System.out.println("USER CONTROLLER LOG: Successfully returned after :" + jp.getSignature().getName() );
	}
	
	
	/**
	 * Log before entering any function executed by Admin controller
	 * 
	 * @author Meera
	 */
	@Before("execution(* com.cmpe275.OnlineOrdering.AdminController.*(..))")
	public void loggingBeforeA(JoinPoint jp) {
		System.out.println("ADMIN CONTROLLER LOG: Entered :" + jp.getSignature().getName() );
	}
	
	/**
	 * Log after successfully executing any function executed by Admin controller
	 * 
	 * @author Meera
	 */
	@AfterReturning("execution(* com.cmpe275.OnlineOrdering.AdminController.*(..))") 
	public void loggingAfterA(JoinPoint jp) {
		System.out.println("ADMIN CONTROLLER LOG: Successfully returned after :" + jp.getSignature().getName() );
	}
	
	

}

package com.cmpe275.OnlineOrdering;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

	@PersistenceContext
	private EntityManager em;

	/**
	 * It will add the verification code details for the user who is in process
	 * of registering to the tempuser table.
	 * 
	 * @param Temperory
	 *            details of user
	 * @author Meera
	 */
	@Transactional
	public void addTuser(TempUser t) {
		em.merge(t);
	}

	/**
	 * It will delete the verification code details for the user who is in
	 * process of registering from the tempuser table.
	 * 
	 * @param Temperory
	 *            details of user
	 * @author Meera
	 */
	@Transactional
	public void delTuser(TempUser t) {
		System.out.println(t.getId());
		em.remove(em.contains(t) ? t : em.merge(t));
	}

	/**
	 * It will add the user details of the user who has successfully registered
	 * to the usercredentials table.
	 * 
	 * @param User
	 *            details
	 * @author Meera
	 */
	@Transactional
	public void adduser(UserCredentials u) {
		em.merge(u);
	}

	/**
	 * It will check if the temporary user with the Id exists or not in the
	 * tempuser table.
	 * 
	 * @param Id
	 *            of the temp user
	 * @return true if it exists else false
	 * @author Meera
	 */
	public boolean existsByIdTuser(int id) {
		TempUser te = new TempUser();
		te.setId(id);
		return em.find(TempUser.class, id) != null;
	}

	/**
	 * It will check if the user with the Id exists or not in the
	 * UserCredentials table.
	 * 
	 * @param Id
	 *            of the user
	 * @return true if it exists else false
	 * @author Meera
	 */
	public boolean existsById(int id) {
		UserCredentials uc = new UserCredentials();
		uc.setId(id);
		return em.find(UserCredentials.class, id) != null;
	}

	/**
	 * It is used to update the temperory user with new verification code.
	 * 
	 * @param Temporary
	 *            details of user
	 * @author Meera
	 */
	@Transactional
	public void updateTuser(TempUser t) {
		em.merge(t);
	}

	/**
	 * It is used to get the details of the temporary verification code for the
	 * user.
	 * 
	 * @param email
	 * @return temporary user details
	 * @author Meera
	 */
	@Transactional
	public TempUser getTuser(String email) {
		TempUser t;
		Query q = em
				.createQuery("Select t from TempUser t where t.email=:arg1");
		q.setParameter("arg1", email);
		try {
			t = (TempUser) q.getSingleResult();

		} catch (NoResultException e) {
			t = null;

		}
		return t;
	}

	/**
	 * It is used to get the registered User details.
	 * 
	 * @param email
	 * @return User details
	 * @author Meera
	 */
	@Transactional
	public UserCredentials getUser(String email) {
		UserCredentials uc;
		Query q = em
				.createQuery("Select uc from UserCredentials uc where uc.email=:arg1");
		q.setParameter("arg1", email);
		try {
			uc = (UserCredentials) q.getSingleResult();

		} catch (NoResultException e) {
			uc = null;

		}
		return uc;
	}

}

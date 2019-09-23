package org.digam.issues.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.digam.issues.entity.User;

@Stateless
public class IssuesService {
	@PersistenceContext
	private EntityManager em;

	public List<User> getAll() {
		List<User> list = em.createQuery("FROM User u", User.class).getResultList();
		return list;
	}

	public void add(User newUser) {
		em.persist(newUser);
	}

}

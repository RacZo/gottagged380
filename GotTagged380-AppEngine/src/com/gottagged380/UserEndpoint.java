package com.gottagged380;

import java.util.List;

import com.gottagged380.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;


import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;

//import org.datanucleus.store.query.Query;
import javax.jdo.Query;

@Api(name = "userendpoint")
public class UserEndpoint {
	
	@ApiMethod(name = "username.check")
	@SuppressWarnings("unchecked")
	public UserName checkUserName(UserName user){
		
		List<User> users;
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(User.class);
		q.setFilter("name == '" + user.getname() +"'");
		//q.declareParameters("String nameParam");
		
		try{
			 users = (List<User>) q.execute();
			
		}finally{
			q.closeAll();
		}
		
		if(users.size() == 0 ){
			user.isAvailable(true);
			return user;
		}else{
			user.isAvailable(false);
		}
		
		return user;
	}
	
	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 * @throws Exception 
	 */
	@ApiMethod(name = "user.get")
	public User getUser(@Named("id") String id) throws Exception {
		PersistenceManager mgr = getPersistenceManager();
		User user = null;
		try {
			user = mgr.getObjectById(User.class, id);
		}catch (Exception e){
			
			throw new Exception("User is not registered");
			
		}
		finally {
			mgr.close();
		}
		
	
		return user;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param user the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "user.insert")
	public User insertUser(User user) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsUser(user)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(user);
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param user the entity to be updated.
	 * @return The updated entity.
	 */
	
	@ApiMethod(name = "user.update")
	public User updateUser(User user) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsUser(user)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(user);
		} finally {
			mgr.close();
		}
		return user;
	}


	private boolean containsUser(User user) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(User.class, user.getEmail());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}

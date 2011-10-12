package sk.funix.userstory.service.impl;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.funix.userstory.config.security.Role;
import sk.funix.userstory.domain.User;
import sk.funix.userstory.dao.RoleDAO;
import sk.funix.userstory.dao.UserDAO;
import sk.funix.userstory.service.UserService;

/**
 * @author Nicolas Milliard
 *
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService  {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	

	public UserServiceImpl(){
		
	}
	
	/**
	 * @param userDAO
	 */
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.UserService#addNewUser(sk.funix.userstory.domain.User)
	 */
	@Transactional
	public User addNewUser(User user) {
		return userDAO.makePersistent(user);
	}
	
	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.UserService#updateAnUser(sk.funix.userstory.domain.User)
	 */
	@Transactional
	public void updateAnUser(User user) {
		userDAO.makePersistent(user);
	}

	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.UserService#deleteAnUser(sk.funix.userstory.domain.User)
	 */
	@Transactional
	public void deleteAnUser(User user) {
		userDAO.makeTransient(user);
	}
	
	@Transactional
	public void deleteAnUserById(Long id) {
		User user = userDAO.findById(id, false);
		userDAO.makeTransient(user);
	}

	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.UserService#findUserById(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	public User findUserById(Long id) {
		return userDAO.findById(id, false);
	}

	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.UserService#findAllUsers()
	 */
	@Transactional(readOnly = true)
	public List<User> findAllUsers() {
		return userDAO.findAll();
	}
	
	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.UserService#findUsersEntries(int, int)
	 */
	@Transactional(readOnly = true)
	public List<User> findUsersEntries(int firstResult, int maxResults) {
		return userDAO.findEntries(firstResult, maxResults);
	}

	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.UserService#countAllUsers()
	 */
	@Transactional(readOnly = true)
	public long countAllUsers() {
		return userDAO.countAll();
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String openId)
			throws UsernameNotFoundException, DataAccessException {
		
		List<User> listUser = userDAO.findByOpenId(openId);
		if (0 == listUser.size()){
			throw new UsernameNotFoundException(openId);
		}
		
		return listUser.get(0);
	}
	
	@Transactional
	public User createNewOpenIdUser(OpenIDAuthenticationToken token) {
		logger.debug("IN createNewOpenIdUser(OpenIDAuthenticationToken token): {}", token);
		User user = new User();
		User userPersistent = null;
		try{
			user.setOpenId(token.getIdentityUrl());
			
			// Role User
			Role roleUser = roleDAO.findByAuthority(Role.AUTHORITY_USER);
			if (roleUser == null){
				roleUser = new Role();
				roleUser.setAuthority(Role.AUTHORITY_USER);
				roleUser = roleDAO.makePersistent(roleUser);
			}
			
			user.setRoles(new HashSet<Role>());
			user.getRoles().add(roleUser);
			
		    for (final OpenIDAttribute attribute : token.getAttributes()) {
		        if ("email".equalsIgnoreCase(attribute.getName())) {
		        	logger.debug("createNewOpenIdUser(String openId), email: {}", attribute.getValues().get(0));
		        	user.setEmail(attribute.getValues().get(0));
		        }else if ("name".equalsIgnoreCase(attribute.getName())) {
		        	logger.debug("createNewOpenIdUser(String openId), name: {}", attribute.getValues().get(0));
		        	user.setName(attribute.getValues().get(0));
		        }
		    }
		    userPersistent = userDAO.makePersistent(user);
		}catch (Throwable e) {
			logger.debug("Exception createNewOpenIdUser(String openId): {}", e);
		}
		
		
	    logger.debug("OUT createNewOpenIdUser(OpenIDAuthenticationToken token)");
		return userPersistent;
	}

}

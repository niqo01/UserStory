package sk.funix.userstory.dao;

import java.util.List;

import sk.funix.userstory.domain.User;

/**
 * @author Nicolas Milliard
 *
 */
public interface UserDAO extends GenericDAO<User, Long> {
	
	List<User> findByOpenId(String name);

}

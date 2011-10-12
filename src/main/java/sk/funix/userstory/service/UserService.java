package sk.funix.userstory.service;

import java.util.List;

import org.springframework.security.openid.OpenIDAuthenticationToken;

import sk.funix.userstory.domain.User;

/**
 * @author Nicolas Milliard
 *
 */
public interface UserService {

	/**
	 * @param user
	 * @return
	 */
	User addNewUser(User user);
	
	/**
	 * @param token
	 * @return
	 */
	User createNewOpenIdUser(OpenIDAuthenticationToken token);

	/**
	 * @param user
	 */
	void deleteAnUser(User user);
	
	/**
	 * @param user
	 */
	void deleteAnUserById(Long id);

	/**
	 * @param user
	 */
	void updateAnUser(User user);

	/**
	 * @param id
	 * @return
	 */
	User findUserById(Long id);

	/**
	 * @return
	 */
	List<User> findAllUsers();

	/**
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<User> findUsersEntries(int firstResult, int maxResults);

	/**
	 * @return
	 */
	long countAllUsers();
}

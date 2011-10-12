package sk.funix.userstory.dao;

import java.util.List;

import sk.funix.userstory.domain.Story;
import sk.funix.userstory.domain.User;
import sk.funix.userstory.dao.GenericDAO;


/**
 * @author Nicolas Milliard
 *
 */
public interface StoryDAO extends GenericDAO<Story, Long> {
	
	/**
	 * @param user
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Story> findEntriesStoriesByUser(User user, int firstResult, int maxResults);

	/**
	 * @param user
	 * @return
	 */
	public List<Story> findAllStoriesByUser(User user);
	
	/**
	 * @param user
	 * @return
	 */
	public long countAllByUser(User user);
	
	/** Check if the story is own by the user.
	 * @param user
	 * @param idStory
	 * @return
	 */
	public boolean isOwner(User user, Long idStory);

}

package sk.funix.userstory.service;

import java.util.List;

import sk.funix.userstory.domain.Story;
import sk.funix.userstory.domain.User;

/**
 * @author Nicolas Milliard
 *
 */
public interface StoryService {


	/** Create a new Story own by the authenticate user.
	 * @param story
	 * @return
	 */
	public Story addNewStory(Story story);


	/** Update a Story own by the authenticate user.
	 * @param story
	 */
	public void updateAnStory(Story story);


	/** Delete a Story own by the authenticate user.
	 * @param story
	 */
	public void deleteAnStory(Story story);


	/** delete a Story by id own by the authenticate user.
	 * @param id
	 */
	public abstract void deleteAnStoryById(Long id);


	/** Find a Story by id own by the authenticate user.
	 * @param id
	 * @return
	 */
	public Story findStoryById(Long id);


	/** Find all Stories own by the authenticate user.
	 * @return
	 */
	public List<Story> findAllStories();


	/** Find a range of Stories own by the authenticate user.
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Story> findStorysEntries(int firstResult,
			int maxResults);


	/** Count all stories own by the authenticate user.
	 * @return
	 */
	public long countAllStories();
	

	/** Count all stories of one specific user.
	 * @param user
	 * @return
	 */
	public long countAllSharedStoriesByUser(User user);
	
	/** Find all stories for one specific user.
	 * @param user
	 * @return
	 */
	public List<Story> findAllSharedStoriesByUser(User user);

	
	/** Find a range of stories for one specific user
	 * @param user
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Story> findSharedEntriesStoriesByUser(User user, int firstResult, int maxResults);


}
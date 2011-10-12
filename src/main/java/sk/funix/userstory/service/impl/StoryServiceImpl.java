package sk.funix.userstory.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.funix.userstory.config.security.SecurityUtil;
import sk.funix.userstory.dao.StoryDAO;
import sk.funix.userstory.domain.Story;
import sk.funix.userstory.domain.User;
import sk.funix.userstory.service.StoryService;

import org.apache.commons.lang.NotImplementedException;

/**
 * @author Nicolas Milliard
 *
 */
@Service
public class StoryServiceImpl implements StoryService  {

	private static final Logger logger = LoggerFactory.getLogger(StoryServiceImpl.class);

	@Autowired
	private StoryDAO storyDAO;
	

	public StoryServiceImpl(){
		
	}
	
	/**
	 * @param storyDAO
	 */
	public StoryServiceImpl(StoryDAO storyDAO) {
		this.storyDAO = storyDAO;
	}


	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.impl.StoryService#addNewStory(sk.funix.userstory.domain.Story)
	 */
	@Transactional
	public Story addNewStory(Story story) {
		story.setUser(SecurityUtil.getUserConnected());
		return storyDAO.makePersistent(story);
	}
	

	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.impl.StoryService#updateAnStory(sk.funix.userstory.domain.Story)
	 */
	@Transactional
	public void updateAnStory(Story story) {
		checkSecurity(story.getId());
		storyDAO.makePersistent(story);
	}


	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.impl.StoryService#deleteAnStory(sk.funix.userstory.domain.Story)
	 */
	@Transactional
	public void deleteAnStory(Story story) {
		checkSecurity(story.getId());
		storyDAO.makeTransient(story);
	}
	
	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.impl.StoryService#deleteAnStoryById(java.lang.Long)
	 */
	@Transactional
	public void deleteAnStoryById(Long id) {
		checkSecurity(id);
		storyDAO.makeTransientByID(id);
	}


	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.impl.StoryService#findStoryById(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	public Story findStoryById(Long id) {
		checkSecurity(id);
		Story story = storyDAO.findById(id, false);
		return story;
	}


	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.impl.StoryService#findAllStorys()
	 */
	@Transactional(readOnly = true)
	public List<Story> findAllStories() {
		final User userConnected = SecurityUtil.getUserConnected();
		return storyDAO.findAllStoriesByUser(userConnected);
	}
	
	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.impl.StoryService#findStorysEntries(int, int)
	 */
	@Transactional(readOnly = true)
	public List<Story> findStorysEntries(int firstResult, int maxResults) {
		final User userConnected = SecurityUtil.getUserConnected();
		return storyDAO.findEntriesStoriesByUser(userConnected,firstResult, maxResults);
	}

	/* (non-Javadoc)
	 * @see sk.funix.userstory.service.impl.StoryService#countAllStorys()
	 */
	@Transactional(readOnly = true)
	public long countAllStories() {
		final User userConnected = SecurityUtil.getUserConnected();
		return storyDAO.countAllByUser(userConnected);
	}
	
	/** Check if the user own the story
	 * @param user
	 */
	private void checkSecurity(Long idStory){
		logger.debug("IN checkSecurity(Long idStory)");
		final User userConnected = SecurityUtil.getUserConnected();
		if (!SecurityUtil.isUserConnectedAdmin()
				&& !storyDAO.isOwner(userConnected, idStory)){
			throw new AuthorizationServiceException("Non authorized for this operation");
		}
		logger.debug("OUT checkSecurity(Long idStory)");
	}

	//TODO Shared Methods
	public long countAllSharedStoriesByUser(User user) {
		throw new NotImplementedException();
	}

	//TODO Shared Methods
	public List<Story> findAllSharedStoriesByUser(User user) {
		throw new NotImplementedException();
	}

	//TODO Shared Methods
	public List<Story> findSharedEntriesStoriesByUser(User user,
			int firstResult, int maxResults) {
		throw new NotImplementedException();
	}
	
}

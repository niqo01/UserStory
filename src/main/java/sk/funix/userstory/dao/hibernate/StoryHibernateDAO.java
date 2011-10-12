package sk.funix.userstory.dao.hibernate;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import sk.funix.userstory.dao.StoryDAO;
import sk.funix.userstory.domain.Story;
import sk.funix.userstory.domain.User;

/**		
 * @author Nicolas Milliard
 *
 */
@Repository
public class StoryHibernateDAO extends GenericHibernateDAO<Story, Long> implements
		StoryDAO {
	private static final Logger logger = LoggerFactory.getLogger(StoryHibernateDAO.class);
	
	public StoryHibernateDAO(){
		
	}
	
	/* (non-Javadoc)
	 * @see sk.funix.userstory.dao.StoryDAO#findEntriesStoriesByUser(sk.funix.userstory.domain.User, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Story> findEntriesStoriesByUser(User user, int firstResult, int maxResults){
    	return getEntityManager().createQuery("select s from Story s where s.user.id = :id")
    		.setParameter("id", user.getId())
    		.setFirstResult(firstResult)
    		.setMaxResults(maxResults)
    		.getResultList();
    }
	
	/* (non-Javadoc)
	 * @see sk.funix.userstory.dao.StoryDAO#findAllStoriesByUser(sk.funix.userstory.domain.User)
	 */
	@SuppressWarnings("unchecked")
	public List<Story> findAllStoriesByUser(User user){
    	return getEntityManager().createQuery("select s from Story s where s.user.id = :id")
    		.setParameter("id", user.getId())
    		.getResultList();
    }
	
    /* (non-Javadoc)
     * @see sk.funix.userstory.dao.StoryDAO#countAllByUser(sk.funix.userstory.domain.User)
     */
    public long countAllByUser(User user){
    	return (Long) getEntityManager().createQuery("select count(*) " +
    								"from Story s " +
    								"where s.user.id = :id")
    							.setParameter("id", user.getId())
    							.getSingleResult();
    }
    
    /* (non-Javadoc)
     * @see sk.funix.userstory.dao.StoryDAO#isOwner(sk.funix.userstory.domain.User, java.lang.Long)
     */
    public boolean isOwner(User user, Long idStory){
		Long idOwner = 
		(Long)getEntityManager().createQuery("select s.user.id from Story s where s.id = :idStory")
			.setParameter("idStory", idStory)
			.getSingleResult();

    	return (idOwner.equals(user.getId()))?true:false;
    }
	
}

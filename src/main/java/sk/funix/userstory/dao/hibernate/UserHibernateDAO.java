package sk.funix.userstory.dao.hibernate;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel; 

import org.springframework.stereotype.Repository;

import sk.funix.userstory.dao.UserDAO;
import sk.funix.userstory.domain.User;

/**
 * @author Nicolas Milliard
 *
 */
@Repository
public class UserHibernateDAO extends GenericHibernateDAO<User, Long> implements
		UserDAO {
	
	
	public UserHibernateDAO(){
		
	}
	
	public List<User> findByOpenId(String openId) {
		Metamodel mm = this.getEntityManager().getMetamodel();
		EntityType<User> user_ = mm.entity(User.class);
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    	CriteriaQuery<User> cq = cb.createQuery(User.class);
    	Root<User> user = cq.from(User.class);
    	cq.select(user).where(cb.equal(user.get(user_.getSingularAttribute("openId")), openId));
    	return getEntityManager().createQuery(cq).getResultList();
    }
}

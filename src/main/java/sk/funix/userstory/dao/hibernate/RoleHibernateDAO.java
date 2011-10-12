package sk.funix.userstory.dao.hibernate;



import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.springframework.stereotype.Repository;

import sk.funix.userstory.config.security.Role;
import sk.funix.userstory.dao.RoleDAO;


/**		
 * @author Nicolas Milliard
 *
 */
@Repository
public class RoleHibernateDAO extends GenericHibernateDAO<Role, Long> implements
		RoleDAO {
	
	
	public RoleHibernateDAO(){
		
	}

	public Role findByAuthority(String authority) {
		Metamodel mm = this.getEntityManager().getMetamodel();
		EntityType<Role> role_ = mm.entity(Role.class);
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    	CriteriaQuery<Role> cq = cb.createQuery(Role.class);
    	Root<Role> user = cq.from(Role.class);
    	cq.select(user).where(cb.equal(user.get(role_.getSingularAttribute("authority")), authority));
    	List<Role> listRole = getEntityManager().createQuery(cq).getResultList();
    	if (listRole.size() == 0){
    		return null;
    	}
    	return getEntityManager().createQuery(cq).getResultList().get(0);
	}
	
}

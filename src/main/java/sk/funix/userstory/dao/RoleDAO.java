package sk.funix.userstory.dao;




import sk.funix.userstory.config.security.Role;
import sk.funix.userstory.dao.GenericDAO;


/**
 * @author Nicolas Milliard
 *
 */
public interface RoleDAO extends GenericDAO<Role, Long> {

	Role findByAuthority(String authority);

}

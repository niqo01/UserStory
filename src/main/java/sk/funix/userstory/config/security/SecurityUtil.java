package sk.funix.userstory.config.security;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import sk.funix.userstory.domain.User;

/** Utils for Security
 * @author Nicolas Milliard
 *
 */
@Component
@Scope("session")
public class SecurityUtil {

	/** Return the connected user
	 * @return
	 */
	public static User getUserConnected(){
		SecurityContext ctx = SecurityContextHolder.getContext();
		return (User) ctx.getAuthentication().getPrincipal();
	}
	
	/** Test if the user connected is an Administrator
	 * @return
	 */
	public static boolean isUserConnectedAdmin(){
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (!ctx.getAuthentication().isAuthenticated()){
			return false;
		}
		for (Role role : ((User) ctx.getAuthentication().getPrincipal()).getRoles()){
			if (role.getAuthority().equals(Role.AUTHORITY_ADMIN)){
				return true;
			}
		}
		return false;
	}
}

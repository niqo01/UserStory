/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited*/
package sk.funix.userstory.config.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.AuthenticationCancelledException;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.util.Assert;

import sk.funix.userstory.service.UserService;

/**
 * Finalises the OpenID authentication by obtaining local authorities for the authenticated user.
 * <p>
 * The authorities are obtained by calling the configured <tt>UserDetailsService</tt>.
 * The <code>UserDetails</code> it returns must, at minimum, contain the username and <code>GrantedAuthority[]</code>
 * objects applicable to the authenticated user. Note that by default, Spring Security ignores the password and
 * enabled/disabled status of the <code>UserDetails</code> because this is
 * authentication-related and should have been enforced by another provider server.
 * <p>
 * The <code>UserDetails</code> returned by implementations is stored in the generated <code>AuthenticationToken</code>,
 * so additional properties such as email addresses, telephone numbers etc can easily be stored.
 *
 * @author Robin Bramley, Opsera Ltd.
 * @author Nicolas Milliard
 */
public class OpenIDAuthenticationProvider  implements AuthenticationProvider, InitializingBean{
    //~ Instance fields ================================================================================================

    private UserDetailsService userDetailsService;

    //~ Methods ========================================================================================================

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "The userDetailsService must be set");
    }

    /* (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.Authentication)
     */
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        if (!supports(authentication.getClass())) {
            return null;
        }

        if (authentication instanceof OpenIDAuthenticationToken) {
            OpenIDAuthenticationToken response = (OpenIDAuthenticationToken) authentication;
            OpenIDAuthenticationStatus status = response.getStatus();

            // handle the various possibilities
            if (status == OpenIDAuthenticationStatus.SUCCESS) {
                // Lookup user details
            	//@Nicolas Milliard
            	// If the user doesn't exist, the application create one
            	UserDetails userDetails;
            	try{
            		userDetails = userDetailsService.loadUserByUsername(response.getIdentityUrl());
            	}catch (UsernameNotFoundException e) {
            		userDetails = ((UserService)userDetailsService).createNewOpenIdUser(response);
				}
                return createSuccessfulAuthentication(userDetails, response);

            } else if (status == OpenIDAuthenticationStatus.CANCELLED) {
                throw new AuthenticationCancelledException("Log in cancelled");
            } else if (status == OpenIDAuthenticationStatus.ERROR) {
                throw new AuthenticationServiceException("Error message from server: " + response.getMessage());
            } else if (status == OpenIDAuthenticationStatus.FAILURE) {
                throw new BadCredentialsException("Log in failed - identity could not be verified");
            } else if (status == OpenIDAuthenticationStatus.SETUP_NEEDED) {
                throw new AuthenticationServiceException(
                        "The server responded setup was needed, which shouldn't happen");
            } else {
                throw new AuthenticationServiceException("Unrecognized return value " + status.toString());
            }
        }

        return null;
    }

    /**
     * Handles the creation of the final <tt>Authentication</tt> object which will be returned by the provider.
     * <p>
     * The default implementation just creates a new OpenIDAuthenticationToken from the original, but with the
     * UserDetails as the principal and including the authorities loaded by the UserDetailsService.
     *
     * @param userDetails the loaded UserDetails object
     * @param auth the token passed to the authenticate method, containing
     * @return the token which will represent the authenticated user.
     */
    protected Authentication createSuccessfulAuthentication(UserDetails userDetails, OpenIDAuthenticationToken auth) {
        return new OpenIDAuthenticationToken(userDetails, userDetails.getAuthorities(),
                auth.getIdentityUrl(), auth.getAttributes());
    }

    /**
     * Used to load the authorities for the authenticated OpenID user.
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
     */
    public boolean supports(Class<? extends Object> authentication) {
        return OpenIDAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

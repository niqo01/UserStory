package sk.funix.userstory.domain;

import java.beans.Beans;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import sk.funix.userstory.config.security.Role;
import sk.funix.userstory.config.util.BusinessKey;
import sk.funix.userstory.config.util.Method;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


/**
 * @author Nicolas Milliard
 *
 */
@Entity
@Table(name = "T_USER")
@XStreamAlias("user")
public class User extends Beans implements Serializable, UserDetails {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BusinessKey(include = Method.TO_STRING)
    private Long id;
    
	@Version
	@BusinessKey(include = Method.TO_STRING)
	private Integer version;
	
	@NotNull
	@BusinessKey(include = Method.TO_STRING)
	private String openId;

    @Size(min = 3)
    @BusinessKey
    private String name;

    //@Pattern(regexp = "")
    @BusinessKey
    private String email;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Add methods for Spring security
	 * 
	 */
	@ManyToMany(cascade=CascadeType.PERSIST)
	private Set<Role> roles;
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
          Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
          for (Role role : getRoles()) {
               authorities.add(new GrantedAuthorityImpl(role.getAuthority()));
          }
          return authorities;
	}


	public String getUsername() {
		return this.getName();
	}
	
	public void setUsername(String name) {
		this.setName(name);
	}
	
	@XStreamOmitField
	@Transient
	private boolean isAccountNonExpired = false;
	@XStreamOmitField
	@Transient
	private boolean isAccountNonLocked = false;
	@XStreamOmitField
	@Transient
	private boolean isCredentialsNonExpired = false;
	@XStreamOmitField
	@Transient
	private boolean isEnabled = false;

	@Transient
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Transient
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	@Transient
	public String getPassword() {
		return "";
	}
	
}

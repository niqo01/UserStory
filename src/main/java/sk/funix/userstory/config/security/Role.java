package sk.funix.userstory.config.security;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import sk.funix.userstory.config.util.Bean;
import sk.funix.userstory.config.util.BusinessKey;
import sk.funix.userstory.config.util.Method;

@Entity
@Table(name = "T_ROLE", 
		uniqueConstraints = {@UniqueConstraint(columnNames={"authority"})})
@XStreamAlias("role")
public class Role extends Bean implements GrantedAuthority{
	
	public static final String AUTHORITY_USER = "ROLE_USER";
	public static final String AUTHORITY_ADMIN = "ROLE_ADMIN";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@BusinessKey(include = Method.TO_STRING)
	private int id;

	@BusinessKey
	private String authority;

	public int getId(){
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}


}

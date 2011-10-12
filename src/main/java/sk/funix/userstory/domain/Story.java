package sk.funix.userstory.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import sk.funix.userstory.config.util.Bean;
import sk.funix.userstory.config.util.BusinessKey;
import sk.funix.userstory.config.util.Method;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Nicolas Milliard
 *
 */
@Entity
@Table(name = "T_STORY")
@XStreamAlias("story")
@Cacheable
public class Story extends Bean implements Serializable{

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
	@BusinessKey
	@ManyToOne
	private User user;
	
	@NotNull
	@BusinessKey
	private String name;
	
	private String comment;
	
	@Valid
	@OneToMany(mappedBy="story")
	private List<Act> listAct;
	
	@ManyToMany
	private List<User> listAuthorizeUser;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public List<Act> getListAct() {
		return listAct;
	}

	public void setListAct(List<Act> listAct) {
		this.listAct = listAct;
	}

	public List<User> getListAuthorizeUser() {
		return listAuthorizeUser;
	}

	public void setListAuthorizeUser(List<User> listAuthorizeUser) {
		this.listAuthorizeUser = listAuthorizeUser;
	}
	
}

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
import javax.persistence.Table;
import javax.persistence.Version;
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
@Table(name = "T_ACT")
@XStreamAlias("act")
@Cacheable
public class Act extends Bean implements Serializable {
	
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

	@BusinessKey
	@NotNull
	@ManyToOne
	private Story story;
	
	@ManyToMany
	private List<Event> listEvent;
	
	private String comment;

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

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public List<Event> getListEvent() {
		return listEvent;
	}

	public void setListEvent(List<Event> listEvent) {
		this.listEvent = listEvent;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
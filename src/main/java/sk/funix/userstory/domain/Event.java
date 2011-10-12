package sk.funix.userstory.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "T_EVENT")
@XStreamAlias("event")
public class Event extends Bean implements Serializable, Comparable<Event> {

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

	@OneToOne
	private Media media;

	@NotNull
	@BusinessKey
	private Date date;

	private Float latitude;

	private Float longitude;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	@Override
	public int compareTo(Event anOtherEvent) {
		return this.date.compareTo(anOtherEvent.getDate());
	}
}

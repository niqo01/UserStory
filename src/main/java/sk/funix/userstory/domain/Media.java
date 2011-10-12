package sk.funix.userstory.domain;

import java.io.Serializable;

import javax.activation.DataSource;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import sk.funix.userstory.config.util.Bean;
import sk.funix.userstory.config.util.BusinessKey;
import sk.funix.userstory.config.util.Method;

/** Abstract base class implementation 
 * for the {@link MediaSource} interfaces.
 * @author Nicolas Milliard
 *
 */
@Entity
@Table(name = "T_MEDIA")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="mediatype",
    discriminatorType=DiscriminatorType.STRING
)
public abstract class Media extends Bean implements DataSource, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BusinessKey(include = Method.TO_STRING)
    private Long id;
	
	protected String contentType;
	protected String name;
	protected long contentLength = -1;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}
	

	public String getName() {
		return name;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}

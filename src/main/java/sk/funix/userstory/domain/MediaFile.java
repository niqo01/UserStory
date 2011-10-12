package sk.funix.userstory.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** Type File Media.
 * @author Nicolas Milliard
 *
 */
@Entity
@DiscriminatorValue("FILE")
@XStreamAlias("mediaFile")
public class MediaFile extends Media {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private File aFile;

	public MediaFile(String contentType, File aFile) {
		this.contentType = contentType;
		this.aFile = aFile;
		this.contentLength = aFile.length();
		this.name = aFile.getName();
	}


	public InputStream getInputStream() throws IOException {
		return new FileInputStream(aFile);
	}


	public OutputStream getOutputStream() throws IOException {
		throw new UnsupportedOperationException("Cannot modify the file");
	}

}

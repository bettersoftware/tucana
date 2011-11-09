/**
 * 
 */
package org.tucana.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @author kamann
 *
 */
@Entity
@XStreamAlias("constallation")
public class Constellation implements Serializable{
	private static final long serialVersionUID = 5723129346307261594L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String code;
	private String genitiveName;
	private String hemisphere;
	private String author;
	private int authorYear;
	private double area;
	private double greatestMagnitude;
	private String visibilityArea;
	private int numberOfStarsGreater3M;
	private int numberOfStarsGreater4M;
	@Lob
	@XStreamOmitField
	private byte[] starCardData;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@XStreamImplicit(itemFieldName="names")
	private List<ConstellationName> names = new ArrayList<ConstellationName>();

	/**
	 * @return The latin name of the constellation. All names for a foreign language you'll find in {@link ConstellationName}
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the official 3-letter code of the IAU for this constellation
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the latin genitive name of this constellation you need for names of stars of this constellation
	 */
	public String getGenitiveName() {
		return genitiveName;
	}

	public void setGenitiveName(String genitiveName) {
		this.genitiveName = genitiveName;
	}

	/**
	 * @return Is the constellation visible at the northern 'N', southern 'S' or both 'N S' hemisphere?
	 */
	public String getHemisphere() {
		return hemisphere;
	}

	public void setHemisphere(String hemisphere) {
		this.hemisphere = hemisphere;
	}

	/**
	 * @return The first person who writes about this constellation
	 */
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return The year the first notice the author has done
	 */
	public int getAuthorYear() {
		return authorYear;
	}

	public void setAuthorYear(int authorYear) {
		this.authorYear = authorYear;
	}

	/**
	 * @return The area of the sky this constellation have in (°)²
	 */
	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	/**
	 * Not used at this time
	 * @return nothing
	 */
	public String getVisibilityArea() {
		return visibilityArea;
	}

	public void setVisibilityArea(String visibilityArea) {
		this.visibilityArea = visibilityArea;
	}

	/**
	 * @return The # of stars greater 3m containing in this constellation
	 */
	public int getNumberOfStarsGreater3M() {
		return numberOfStarsGreater3M;
	}

	public void setNumberOfStarsGreater3M(int numberOfStarsGreater3M) {
		this.numberOfStarsGreater3M = numberOfStarsGreater3M;
	}

	/**
	 * @return The # of stars greater 4m containing in this constellation
	 */
	public int getNumberOfStarsGreater4M() {
		return numberOfStarsGreater4M;
	}

	public void setNumberOfStarsGreater4M(int numberOfStarsGreater4M) {
		this.numberOfStarsGreater4M = numberOfStarsGreater4M;
	}

	/**
	 * @return not used at this time
	 */
	public double getGreatestMagnitude() {
		return greatestMagnitude;
	}

	public void setGreatestMagnitude(double greatestMagnitude) {
		this.greatestMagnitude = greatestMagnitude;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Constellation [id=" + id + ", name=" + name + ", code=" + code
				+ ", genitiveName=" + genitiveName + ", hemisphere="
				+ hemisphere + ", author=" + author + ", authorYear="
				+ authorYear + ", area=" + area + ", greatestMagnitude="
				+ greatestMagnitude + ", visibilityArea=" + visibilityArea
				+ ", numberOfStarsGreater3M=" + numberOfStarsGreater3M
				+ ", numberOfStarsGreater4M=" + numberOfStarsGreater4M
				+ ", names=" + names + "]";
	}

	/**
	 * @return the starCardData as {@link byte[]}
	 */
	public byte[] getStarCardData() {
		return starCardData;
	}

	public void setStarCardData(byte[] starCardData) {
		this.starCardData = starCardData;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return All names in foreign languages for this constellation
	 */
	public List<ConstellationName> getNames() {
		return names;
	}

	public void setNames(List<ConstellationName> names) {
		this.names = names;
	}
}

package org.sampratistaana.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sampratistaana.Messages;

@Entity
@Table(name = "PROPERTIES")
public class Property implements Serializable{
	private static final long serialVersionUID = -7831270198763527090L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROPERTY_ID", nullable = false)
	private long propertyId;
	
	@Column(name = "PROPERTY_NAME", nullable = false)
	private String propertyName;
	@Column(name = "PROPERTY_KEY", nullable = false)
	private String propertyKey;
	@Column(name = "FLAG", nullable = false)
	private String flag;
	@Column(name = "PROPERTY_VALUE", nullable = false)
	private String propertyValue;
	
	
	public long getPropertyId() {
		return propertyId;
	}
	public Property setPropertyId(long propertyId) {
		this.propertyId = propertyId;
		return this;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public Property setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}
	public String getPropertyKey() {
		return propertyKey;
	}
	public Property setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
		return this;
	}
	public String getFlag() {
		return flag;
	}
	public Property setFlag(String flag) {
		this.flag = flag;
		return this;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public Property setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
		return this;
	}
	@Override
	public String toString() {
		return Messages.getMessage(propertyValue);
	}	
}

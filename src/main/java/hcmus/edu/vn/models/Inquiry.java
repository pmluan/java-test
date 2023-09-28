package hcmus.edu.vn.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Input")
@XmlAccessorType(XmlAccessType.FIELD)
public class Inquiry {

	@XmlElement(name = "InquiryType")
	private String inquiryType;
	
	@XmlElement(name = "InquiryValue")
	private String inquiryValue;
	
	@XmlElement(name = "InquiryName")
	private String inquiryName;

	public String getInquiryType() {
		return inquiryType;
	}

	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
	}

	public String getInquiryValue() {
		return inquiryValue;
	}

	public void setInquiryValue(String inquiryValue) {
		this.inquiryValue = inquiryValue;
	}

	public String getInquiryName() {
		return inquiryName;
	}

	public void setInquiryName(String inquiryName) {
		this.inquiryName = inquiryName;
	}

}

package hcmus.edu.vn.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Output")
@XmlAccessorType(XmlAccessType.FIELD)
public class Output {

	@XmlElement(name = "ErrorCode")
	private String errorCode;

	@XmlElement(name = "ErrorDesc")
	private String errorDesc;

	@XmlElement(name = "Detail")
	private Detail detail;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

}

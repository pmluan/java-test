package hcmus.edu.vn.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Function")
@XmlAccessorType(XmlAccessType.FIELD)
public class Function {

	@XmlAttribute(name = "Name")
	private String name;

	@XmlAttribute(name = "WSName")
	private String wSName;

	@XmlAttribute(name = "IPAddress")
	private String iPAddress;

	@XmlAttribute(name = "BANKCODE")
	private String bankCode;

	@XmlAttribute(name = "TXDATE")
	private String txDate;

	@XmlAttribute(name = "TXTIME")
	private String txTime;

	@XmlAttribute(name = "BATCHID")
	private String batChid;

	@XmlAttribute(name = "VIA")
	private String via;

	@XmlAttribute(name = "SIGNATURE")
	private String signature;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getwSName() {
		return wSName;
	}

	public void setwSName(String wSName) {
		this.wSName = wSName;
	}

	public String getiPAddress() {
		return iPAddress;
	}

	public void setiPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getTxTime() {
		return txTime;
	}

	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}

	public String getBatChid() {
		return batChid;
	}

	public void setBatChid(String batChid) {
		this.batChid = batChid;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}

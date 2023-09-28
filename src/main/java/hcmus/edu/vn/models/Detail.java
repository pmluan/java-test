package hcmus.edu.vn.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Detail")
@XmlAccessorType(XmlAccessType.FIELD)
public class Detail {

	@XmlElement(name = "AcctItem")
	private AcctItem acctItem;

	public AcctItem getAcctItem() {
		return acctItem;
	}

	public void setAcctItem(AcctItem acctItem) {
		this.acctItem = acctItem;
	}
	
	
}

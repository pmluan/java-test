
package hcmus.edu.vn.models.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pv_strMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pvStrMessage"
})
@XmlRootElement(name = "ExecuteMessage")
public class ExecuteMessage {

    @XmlElement(name = "pv_strMessage")
    protected String pvStrMessage;

    /**
     * Gets the value of the pvStrMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPvStrMessage() {
        return pvStrMessage;
    }

    /**
     * Sets the value of the pvStrMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPvStrMessage(String value) {
        this.pvStrMessage = value;
    }

}

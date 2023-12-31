
package hcmus.edu.vn.models.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "FLEXGWServiceSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface FLEXGWServiceSoap {


    /**
     * 
     * @param pvStrMessage
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "ExecuteMessage", action = "http://tempuri.org/ExecuteMessage")
    @WebResult(name = "ExecuteMessageResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "ExecuteMessage", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ExecuteMessage")
    @ResponseWrapper(localName = "ExecuteMessageResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ExecuteMessageResponse")
    public String executeMessage(
        @WebParam(name = "pv_strMessage", targetNamespace = "http://tempuri.org/")
        String pvStrMessage);

}

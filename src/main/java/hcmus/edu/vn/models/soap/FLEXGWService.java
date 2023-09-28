
package hcmus.edu.vn.models.soap;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "FLEXGWService", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://182.237.21.154/FLEXGWService/FLEXGWService.asmx?WSDL")
public class FLEXGWService
    extends Service
{

    private final static URL FLEXGWSERVICE_WSDL_LOCATION;
    private final static WebServiceException FLEXGWSERVICE_EXCEPTION;
    private final static QName FLEXGWSERVICE_QNAME = new QName("http://tempuri.org/", "FLEXGWService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://182.237.21.154/FLEXGWService/FLEXGWService.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        FLEXGWSERVICE_WSDL_LOCATION = url;
        FLEXGWSERVICE_EXCEPTION = e;
    }

    public FLEXGWService() {
        super(__getWsdlLocation(), FLEXGWSERVICE_QNAME);
    }

    public FLEXGWService(WebServiceFeature... features) {
        super(__getWsdlLocation(), FLEXGWSERVICE_QNAME, features);
    }

    public FLEXGWService(URL wsdlLocation) {
        super(wsdlLocation, FLEXGWSERVICE_QNAME);
    }

    public FLEXGWService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, FLEXGWSERVICE_QNAME, features);
    }

    public FLEXGWService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FLEXGWService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns FLEXGWServiceSoap
     */
    @WebEndpoint(name = "FLEXGWServiceSoap")
    public FLEXGWServiceSoap getFLEXGWServiceSoap() {
        return super.getPort(new QName("http://tempuri.org/", "FLEXGWServiceSoap"), FLEXGWServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FLEXGWServiceSoap
     */
    @WebEndpoint(name = "FLEXGWServiceSoap")
    public FLEXGWServiceSoap getFLEXGWServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "FLEXGWServiceSoap"), FLEXGWServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (FLEXGWSERVICE_EXCEPTION!= null) {
            throw FLEXGWSERVICE_EXCEPTION;
        }
        return FLEXGWSERVICE_WSDL_LOCATION;
    }

}

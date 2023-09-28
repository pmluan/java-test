package hcmus.edu.vn.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class SoapUtilV2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapUtilV2.class);

    private static final String DEFAULT_SOAP_PREFIX = "soapenv";

    private static final String NAMESPACE_PREFIX = "soap";

    private static final String NAMESPACE_URL = "http://soap.amaretto.globe.com";

    public static SOAPMessage getSoapMessage(Document document) {
        SOAPMessage soapMessage = null;
        try {
            MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
            soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
            SOAPHeader soapHeader = soapEnvelope.getHeader();
            SOAPBody soapBody = soapEnvelope.getBody();

            soapMessage.getSOAPBody().addDocument(document);

            soapEnvelope.setPrefix(DEFAULT_SOAP_PREFIX);
            soapHeader.setPrefix(DEFAULT_SOAP_PREFIX);
            soapBody.setPrefix(DEFAULT_SOAP_PREFIX);

            soapEnvelope.removeNamespaceDeclaration(SOAPConstants.SOAP_ENV_PREFIX);
            soapBody.getFirstChild().setPrefix(NAMESPACE_PREFIX);

        } catch (Exception e) {
            LOGGER.error("Error in getSoapMessage method of SoapUtil: ", e);
        }

        return soapMessage;
    }

    public static <T> T parseSoapMessageToObject(SOAPMessage soapMessage, Class<T> clazz) {
        T response = null;
        try {
            JAXBContext jaxbContext = null;
            SOAPBody soapBody = soapMessage.getSOAPBody();

            if (!soapBody.hasFault()) {
                jaxbContext = JAXBContext.newInstance(clazz);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                Node node = soapBody.extractContentAsDocument().getDocumentElement();

                JAXBElement<T> jaxbElement = jaxbUnmarshaller.unmarshal(node, clazz);
                response = jaxbElement.getValue();
            }

        } catch (Exception e) {
            LOGGER.error("Error in parseSoapMessageToObject method of SoapUtil: ", e);
        }

        return response;
    }

    public static boolean hasFault(SOAPMessage soapMessage) {
        boolean result = false;
        try {
            SOAPBody soapBody = soapMessage.getSOAPBody();
            result = soapBody.hasFault();
        } catch (Exception e) {
            LOGGER.error("Error in hasFault method of SoapUtil: ", e);
        }
        return result;
    }

    public static String getFaultReasonText(SOAPMessage soapMessage) {
        String reasonText = StringUtils.EMPTY;
        try {
            SOAPBody soapBody = soapMessage.getSOAPBody();
            SOAPFault soapFault = soapBody.getFault();
            reasonText = soapFault.getFaultReasonText(Locale.US);
        } catch (Exception e) {
            LOGGER.error("Error in getFaultReasonText method of SoapUtil: ", e);
        }
        return reasonText;
    }

    public static <T> SOAPMessage updateSoapMessage(T request, Class<T> clazz) {
        SOAPMessage soapMessage = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.newDocument();
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(request, document);

            soapMessage = SoapUtilV2.getSoapMessage(document);

            if (soapMessage == null) {
                return soapMessage;
            }

            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();

            envelope.addNamespaceDeclaration(NAMESPACE_PREFIX, NAMESPACE_URL);

            SOAPBody body = soapMessage.getSOAPBody();
            body.addDocument(document);

        } catch (Exception e) {
            LOGGER.error("Error in updateSoapMessage method of SoapUtil: ", e);
        }
        return soapMessage;
    }

    public static SOAPMessage callSoapWebService(String soapEndpointUrl, SOAPMessage soapMessage) {
        SOAPMessage response = null;

        if (soapMessage == null) {
            LOGGER.info("soapMessage null, error when init soap message");
            return response;
        }

        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", soapEndpointUrl);

            // Print soap request log
            LOGGER.info("Soap Request");
            printPretty(soapMessage);

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);

            // Print soap response log
            LOGGER.info("Soap Response");
            printPretty(soapResponse);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            soapResponse.writeTo(outputStream);
            String messageStr = outputStream.toString(StandardCharsets.UTF_8.displayName());

            ByteArrayInputStream is = new ByteArrayInputStream(
                    messageStr.getBytes(StandardCharsets.UTF_8.displayName()));
            response = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage(null, is);

        } catch (Exception e) {
            LOGGER.error("Error in callSoapWebService method of SoapUtil: ", e);
        }
        return response;
    }

    public static void printPretty(SOAPMessage soapMessage) {
        try {
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            final Transformer transformer = transformerFactory.newTransformer();

            // Format it
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            final Source soapContent = soapMessage.getSOAPPart().getContent();
            final ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
            final StreamResult result = new StreamResult(streamOut);
            transformer.transform(soapContent, result);

            String message = streamOut.toString(StandardCharsets.UTF_8.displayName());
            String msgFormatted = StringUtils.replace(message, "\n", StringUtils.EMPTY);
            LOGGER.info("Soap Message: {}", msgFormatted);

        } catch (Exception e) {
            LOGGER.error("Error in printPretty method of SoapUtil: ", e);
        }
    }

    private SoapUtilV2() {
        /* Prevent Instantiation */
    }
}

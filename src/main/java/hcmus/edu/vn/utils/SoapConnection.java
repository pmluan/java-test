//package hcmus.edu.vn.utils;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.List;
//import java.util.Map;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import javax.xml.XMLConstants;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Marshaller;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.soap.MessageFactory;
//import javax.xml.soap.MimeHeaders;
//import javax.xml.soap.SOAPBody;
//import javax.xml.soap.SOAPEnvelope;
//import javax.xml.soap.SOAPMessage;
//import javax.xml.soap.SOAPPart;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Source;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.stream.StreamResult;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.HttpStatus;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.w3c.dom.Document;
//
//public class SoapConnection {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(SoapConnection.class);
//
//	private static final String NAMESPACE_PREFIX = "soap";
//
//	private static final String NAMESPACE_URL = "http://schemas.xmlsoap.org/soap/envelope";
//
//	static {
//        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
//                LOGGER.info("Hostname: {}", hostname);
//                return true;
//            }
//        });
//    }
//
//	 private static SSLSocketFactory trustAllCerts() throws NoSuchAlgorithmException, KeyManagementException {
//	        TrustManager[] trustManagers = new TrustManager[] { new X509TrustManager() {
//
//	            @Override
//	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//	                return new X509Certificate[] {};
//	            }
//
//	            @Override
//	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
//	                if (certs == null || certs.length == 0) {
//	                    throw new IllegalArgumentException("Null or zero length chain");
//	                }
//	            }
//
//	            @Override
//	            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
//	                try {
//	                    certs[0].checkValidity();
//	                } catch (Exception e) {
//	                    throw new CertificateException("Certificate not valid or trusted.");
//	                }
//	            }
//	        } };
//
//	        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
//	        SSLContext.setDefault(sslContext);
//	        sslContext.init(null, trustManagers, new SecureRandom());
//	        return sslContext.getSocketFactory();
//	    }
//
//	public static SOAPMessage callMessage(SOAPMessage soapMessageRequest, String endpoint) {
//		String successResponse = StringUtils.EMPTY;
//		String failureResponse = StringUtils.EMPTY;
//		try {
//			  // Load CAs from an InputStream
//			trustAllCerts();
//			URL url = new URL(null,endpoint, new sun.net.www.protocol.https.Handler());
//			LOGGER.info("Endpoint: {}", url);
//
//			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//			connection.setRequestMethod("POST");
//			connection.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
//			connection.setDoOutput(true);
//			String xmlRequest = convertSoapMessageToString(soapMessageRequest);
//			String reqLog = StringUtils.replace(xmlRequest, "\n", StringUtils.EMPTY);
//			LOGGER.info("Soap message Request: {}", reqLog);
//			// Write request body
//			try (OutputStream os = connection.getOutputStream()) {
//				byte[] input = xmlRequest.getBytes(StandardCharsets.UTF_8);
//				os.write(input, 0, input.length);
//			}
//			int responseCode = connection.getResponseCode();
//			LOGGER.info("ESB response code: {}", connection.getResponseCode());
//			Map<String, List<String>> responseHeader = connection.getHeaderFields();
//			LOGGER.info("ESB response header: {}", responseHeader);
//			// Read the Response from Input Stream
//			if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_CREATED) {
//				try (BufferedReader br = new BufferedReader(
//						new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
//					StringBuilder response = new StringBuilder();
//					String responseLine = null;
//					while ((responseLine = br.readLine()) != null) {
//						response.append(responseLine.trim());
//					}
//					successResponse = response.toString();
//				}
//			} else {
//				try (BufferedReader br = new BufferedReader(
//						new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
//					StringBuilder response = new StringBuilder();
//					String responseLine = null;
//					while ((responseLine = br.readLine()) != null) {
//						response.append(responseLine.trim());
//					}
//					failureResponse = response.toString();
//				}
//			}
//
//			String esbResponse = StringUtils.defaultIfBlank(failureResponse, successResponse);
//			LOGGER.info("ESB RESPONSE: {}", esbResponse);
//
//			if (StringUtils.isNotBlank(successResponse)) {
//				return convertStringToSoapMsg(successResponse);
//			}
//			return null;
//		} catch (Exception e) {
//			LOGGER.error("Error: ", e);
//			return null;
//		}
//	}
//
//	private static SOAPMessage convertStringToSoapMsg(String esbResponse) {
//		try {
//			InputStream is = new ByteArrayInputStream(esbResponse.getBytes());
//			return MessageFactory.newInstance().createMessage(new MimeHeaders(), is);
//		} catch (Exception e) {
//			LOGGER.error("Error in convertStringToSoapMsg: ", e);
//			return null;
//		}
//
//	}
//	private static String convertSoapMessageToString(SOAPMessage soapMessage) {
//		try {
//			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
//			transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//			final Transformer transformer = transformerFactory.newTransformer();
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//			final Source soapContent = soapMessage.getSOAPPart().getContent();
//			final ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
//			final StreamResult result = new StreamResult(streamOut);
//			transformer.transform(soapContent, result);
//			return streamOut.toString(StandardCharsets.UTF_8.displayName());
//		} catch (Exception e) {
//			LOGGER.error("Error in convertSoapMessageToString: ", e);
//		}
//		return StringUtils.EMPTY;
//	}
//
//	public static <T> SOAPMessage updateSoapMessage(T request, Class<T> clazz) {
//		SOAPMessage soapMessage = null;
//		try {
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			Document document = db.newDocument();
//			JAXBContext context = JAXBContext.newInstance(clazz);
//			Marshaller marshaller = context.createMarshaller();
//
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			marshaller.marshal(request, document);
//
//			soapMessage = SoapUtil.getSoapMessage(document);
//
//			if (soapMessage == null) {
//				return soapMessage;
//			}
//
//			SOAPPart soapPart = soapMessage.getSOAPPart();
//			SOAPEnvelope envelope = soapPart.getEnvelope();
//
//			envelope.addNamespaceDeclaration(NAMESPACE_PREFIX, NAMESPACE_URL);
//
//			SOAPBody body = soapMessage.getSOAPBody();
//			body.addDocument(document);
//
//		} catch (Exception e) {
//			LOGGER.error("Error in updateSoapMessage method of SoapUtil: ", e);
//		}
//		return soapMessage;
//	}
//
//	private SoapConnection() {
//		/* Prevent instantiation */
//	}
//}

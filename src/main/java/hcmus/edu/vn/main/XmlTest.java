package hcmus.edu.vn.main;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import hcmus.edu.vn.models.Function;
import hcmus.edu.vn.models.Inquiry;
import hcmus.edu.vn.models.ObjectMessage;

public class XmlTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlTest.class);

	public static void main(String[] args) throws Exception {
		LOGGER.info("Start");
//		InputStream inputStream = new FileInputStream(new File("C:\\Users\\phamm\\Downloads\\aa.pdf"));
//		byte[] bytes = IOUtils.toByteArray(inputStream);
//		String encoded = Base64.encodeBase64String(bytes);
//		
//		byte[] data = Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8));
//		try (OutputStream stream = new FileOutputStream("C:\\Users\\phamm\\Desktop\\a.txt")) {
//			 byte[] strToBytes = encoded.getBytes();
//		    stream.write(strToBytes);
//		}

		Function function = new Function();
		function.setName("HDB2C_INQOPENACCOUNT");
		function.setwSName("HDBANKGW");
		function.setiPAddress("10.1.1.1");
		function.setBankCode("HDBANK");
		function.setTxDate("28022015");
		function.setTxTime("113000");
		function.setBatChid("HDBANK20180613095100567");
		function.setVia("M");
		function.setSignature("aaaaa");

		Inquiry inquiry = new Inquiry();
		inquiry.setInquiryName("");
		inquiry.setInquiryType("IDCODE");
		inquiry.setInquiryValue("");

		ObjectMessage message = new ObjectMessage();
		message.setFunction(function);
		message.setInquiry(inquiry);

		String inputData = objectXmlToString(inquiry, Inquiry.class);

		System.out.println(inputData);

		String extract = extractNote(inputData, "Input");
		extract = StringUtils.replace(extract, "$DEFAULT$", StringUtils.EMPTY);
		System.out.println(extract);

	}

	private static <T> String objectXmlToString(T t, Class<T> clazz) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", false);

			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(t, sw);

			return sw.toString();
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}

	private static String extractNote(String data, String noteName) {
		try {
			StringBuilder nodeData = new StringBuilder();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(data)));
			NodeList flowList = document.getElementsByTagName(noteName);

			for (int i = 0; i < flowList.getLength(); i++) {
				NodeList childList = flowList.item(i).getChildNodes();
				for (int j = 0; j < childList.getLength(); j++) {
					Node childNode = childList.item(j);
					
					if(StringUtils.equalsIgnoreCase("#text", childNode.getNodeName())) {
						continue;
					}
					
					if(StringUtils.isBlank(childNode.getTextContent())) {
						childNode.setTextContent("$DEFAULT$");
					}
					
					String dataStr = nodeToString(childNode).trim();
					nodeData.append(dataStr);
				}
			}

			return nodeData.toString();
		} catch (Exception e) {
			LOGGER.error("extractNote - error: ", e);
			return StringUtils.EMPTY;
		}
	}

	private static String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

			Transformer t = factory.newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			return StringUtils.EMPTY;
		}
		return sw.toString();
	}
	
	public static  <T> T parseRootObject(String message, Class<T> clazz) {
        try {
            byte[] decode = Base64.decodeBase64(message);
            String decodeMessage = new String(decode);

            StringReader sr = new StringReader(decodeMessage);
            JAXBContext jaxbContext1 = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext1.createUnmarshaller();
            return (T) unmarshaller.unmarshal(sr);
        } catch (Exception e) {
            return null;
        }
    }
	
	 public static String signWithRSA(String data, String privateKey) {
	        try{
	            byte[] privateKeyByte = Base64.decodeBase64(privateKey);
	            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByte);

	            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

	            PrivateKey kp = keyFactory.generatePrivate(keySpec);

	            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);

	            Signature signature = Signature.getInstance("SHA256withRSA");
	            signature.initSign(kp);
	            signature.update(dataBytes);

	            return Base64.encodeBase64String(signature.sign());
	        } catch(Exception exception){
	            return StringUtils.EMPTY;
	        }
	    }

}

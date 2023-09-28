package hcmus.edu.vn.google.handle;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

public class Main {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String clientId = "56gr52gmtegv0um1gljm7pt5f3";
		String clientSecret = "smvib0npcpjba1bk04p01qpekf5i8vam03ama7uv7g2rd9smc37";
		String encodedData = DatatypeConverter.printBase64Binary((clientId + ":" + clientSecret).getBytes("UTF-8"));
		System.out.println("Basic "+encodedData);
	}
}

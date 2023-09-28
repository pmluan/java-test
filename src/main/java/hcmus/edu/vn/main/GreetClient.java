package hcmus.edu.vn.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(GreetClient.class);

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void startConnection(String ip, int port) {
		try {
			clientSocket = new Socket(ip, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (Exception e) {
			LOGGER.error("startConnection - Error: ", e);
		}
	}

	public String sendMessage(String msg) {
		try {
			out.println(msg);
			String resp = in.readLine();
			return resp;
		} catch (Exception e) {
			LOGGER.error("sendMessage - Error: ", e);
			return StringUtils.EMPTY;
		}
	}

	public void stopConnection() {
		try {
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			LOGGER.error("stopConnection - Error: ", e);
		}

	}
}
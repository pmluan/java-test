package hcmus.edu.vn.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketUtil.class);

    public static String sendToSocket(String input, String ip, int port) {

        LOGGER.info("Socket ip: {} - port: {}", ip, port);
        String result = StringUtils.EMPTY;

        try (Socket socket = new Socket(ip, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream());
             InputStream in = socket.getInputStream()) {

            // Create a connection to
            socket.setSoTimeout(30000);

            // send the command string to the barcode reader
            out.write(input);
            out.flush();

            byte[] readBuffer;
            readBuffer = new byte[2048];
            int messageSize = in.read(readBuffer, 0, 2048);
            result = new String(readBuffer, 0, messageSize);

        } catch (SocketTimeoutException e) {
            return "Time out";
        } catch (Exception e) {
            LOGGER.error("Error: ", e);
        }

        return result;
    }

    private SocketUtil() {
    }
}

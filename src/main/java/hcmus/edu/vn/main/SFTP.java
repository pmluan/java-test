package hcmus.edu.vn.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTP {

	public static void main(String[] args) throws JSchException, SftpException, FileNotFoundException {
		for (int i = 0; i < 1000; i++) {

			JSch jsch = new JSch();
			Session session = jsch.getSession("test", "0.0.0.0", 22);

			session.setPassword("test");
			session.setConfig("StrictHostKeyChecking", "no");
			session.setTimeout(45000);
			session.connect();

			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();

			channelSftp.cd("/");

			// channelSftp.mkdir("test");

			try {
				File file = new File("C:\\Users\\phamm\\Downloads\\img_face.jpg");
				InputStream inputStream = new FileInputStream(file);
				String uuid = UUID.randomUUID().toString();
				channelSftp.put(inputStream, "test" + "/" + uuid + ".jpg");
			} catch (Exception e) {

			} finally {
				//session.disconnect();
				//channelSftp.disconnect();
			}
		}

		System.out.println("success");

	}
}

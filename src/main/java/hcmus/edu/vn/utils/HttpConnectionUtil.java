package hcmus.edu.vn.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hcmus.edu.vn.common.MapperCommon;

public class HttpConnectionUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionUtil.class);

	private static CloseableHttpClient client;

	public static CloseableHttpResponse postMethod(Map<String, String> headers, String endpoint, String request) {
		try {
			LOGGER.info("Call post method with endpoint: {}", endpoint);
			HttpPost httpPost = new HttpPost(endpoint);

			if (headers != null) {
				for (Entry<String, String> header : headers.entrySet()) {
					httpPost.addHeader(header.getKey(), header.getValue());
				}
			}

			if (StringUtils.isNotBlank(request)) {
				StringEntity entity = new StringEntity(request);
				httpPost.setEntity(entity);
			}

			client = HttpClients.createDefault();
			return client.execute(httpPost);

		} catch (Exception e) {
			LOGGER.info("postMethod - error: ", e);
		}

		return null;
	}

	public static CloseableHttpResponse getMethod(Map<String, String> headers, String endpoint) {
		try {
			LOGGER.info("Call get method with endpoint: {}", endpoint);
			HttpGet httpGet = new HttpGet(endpoint);

			if (headers != null) {
				for (Entry<String, String> header : headers.entrySet()) {
					httpGet.addHeader(header.getKey(), header.getValue());
				}
			}
			
			client = HttpClients.createDefault();
			return client.execute(httpGet);

		} catch (Exception e) {
			LOGGER.info("postMethod - error: ", e);
		}

		return null;
	}

	public static <T> T responseBody(CloseableHttpResponse response, Class<T> clazz) {
		try {
			if (response == null) {
				LOGGER.info("Can not connect to server");
				return null;
			}

			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}

			String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			LOGGER.info("body: {}", body);

			if (StringUtils.isBlank(body)) {
				return null;
			}

			return MapperCommon.fromJsonString(body, clazz);
		} catch (Exception e) {
			LOGGER.info("responseBody - error: ", e);
			return null;
		} finally {
			/* Close connection */
			HttpConnectionUtil.closeConnection();
		}
	}

	public static void closeConnection() {
		try {
			if (client != null) {
				client.close();
			}

		} catch (IOException e) {
			LOGGER.error("closeConnection - error: ", e);
		}
	}

	private HttpConnectionUtil() {

	}
}

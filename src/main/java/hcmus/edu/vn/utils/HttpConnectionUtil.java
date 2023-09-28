package hcmus.edu.vn.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpConnectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionUtil.class);

    public static CloseableHttpResponse postMethod(Map<String, String> headers, String endpoint, String request, CloseableHttpClient client) {
        try {
            LOGGER.info("Call post method with endpoint: {}", endpoint);
            HttpPost httpPost = new HttpPost(endpoint);

            if (MapUtils.isNotEmpty(headers)) {
                for (Entry<String, String> header : headers.entrySet()) {
                    httpPost.addHeader(header.getKey(), header.getValue());
                }
            }

            if (StringUtils.isNotBlank(request)) {
                StringEntity entity = new StringEntity(request);
                httpPost.setEntity(entity);
            }

            return client.execute(httpPost);

        } catch (Exception e) {
            LOGGER.info("postMethod - error: ", e);
        }

        return null;
    }

    public static CloseableHttpResponse getMethod(Map<String, String> headers, String endpoint, CloseableHttpClient client) {
        try {
            LOGGER.info("Call get method with endpoint: {}", endpoint);
            HttpGet httpGet = new HttpGet(endpoint);

            if (MapUtils.isNotEmpty(headers)) {
                for (Entry<String, String> header : headers.entrySet()) {
                    httpGet.addHeader(header.getKey(), header.getValue());
                }
            }

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

            return Jackson.fromJsonString(body, clazz);
        } catch (Exception e) {
            LOGGER.info("responseBody - error: ", e);
            return null;
        }
    }

    public static void closeConnection(CloseableHttpClient client) {
        LOGGER.info("Close connection...");
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

    public static void main(String[] args) {
        getAccessToken();
    }

    private static String getAccessToken() {

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String clientId = "3nq57e3uul8p11t3vnonq0ug2u";
            String clientSecret = "1vugcq23t49j86cv35eu8tjulgtk2001qfjirbsb45h1olchh2vs";
            String encoded = Base64.getEncoder()
                    .encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Basic " + encoded);

            String endpoint = "https://test-cxs.globe.com.ph/v1/channels/oauth/token";

            CloseableHttpResponse httpResponse = HttpConnectionUtil.postMethod(headers, endpoint, StringUtils.EMPTY, client);
            if (httpResponse == null) {
                return StringUtils.EMPTY;
            }

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            LOGGER.info("getEnrollAccountCXS - statusCode: {}", statusCode);

            Header[] header = httpResponse.getHeaders("CxsMessageId");
            LOGGER.info("getAccessToken - CxsMessageId: {}", header[0].getValue());

            if (statusCode == HttpStatus.SC_OK) {
                HttpConnectionUtil.responseBody(httpResponse, String.class);
            } else {
                HttpConnectionUtil.responseBody(httpResponse, String.class);
            }

            return StringUtils.EMPTY;
        } catch (Exception e) {
            LOGGER.error("Error: ", e);
            return StringUtils.EMPTY;
        }


    }

}

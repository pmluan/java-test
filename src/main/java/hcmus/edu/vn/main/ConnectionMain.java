package hcmus.edu.vn.main;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class ConnectionMain {
    /**
     * <dependency>
     * <groupId>org.postgresql</groupId>
     * <artifactId>postgresql</artifactId>
     * </dependency>
     */
    public static void main(String[] args) {
        String url = "jdbc:postgresql://192.168.1.44:5432/timon";
        String user = "postgres";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "select * from mbc.mb_configurations";
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    // Retrieve data from the result set
                    int id = rs.getInt("id");
                    String key = rs.getString("key");

                    LOGGER.info("id: {}, key: {}", id, key);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error: ", e);
        }
    }


}

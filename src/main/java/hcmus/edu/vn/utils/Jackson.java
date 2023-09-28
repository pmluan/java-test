package hcmus.edu.vn.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

@Slf4j
public class Jackson {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static final ObjectWriter writer = objectMapper.writer();
    private static final ObjectWriter prettyWriter = objectMapper.writerWithDefaultPrettyPrinter();

    private static final TypeReference<HashMap<String, String>> STRING_MAP_TYPE_REFERENCE = new TypeReference<HashMap<String, String>>() {
    };
    private static final TypeReference<HashMap<String, Object>> OBJECT_MAP_TYPE_REFERENCE = new TypeReference<HashMap<String, Object>>() {
    };

    /**
     * Returns string json value from Object or "null" when object is null
     */
    public static String toJsonString(Object value) {
        try {
            return writer.writeValueAsString(value);
        } catch (Exception e) {
            LOGGER.error("toJsonString - Error: ", e);
            return StringUtils.EMPTY;
        }
    }

    /**
     * Returns the deserialized object from the given json string and target class;
     * or null if the given json string is null.
     */
    public static <T> T fromJsonString(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            LOGGER.error("fromJsonString - Error: ", e);
            return null;
        }
    }

    /**
     * Returns the deserialized object from the given json string and target class generic object;
     * or null if the given json string is null.
     */
    public static <T, E> T fromJsonString(String json, Class<T> clazz, Class<E> subClazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(clazz, subClazz);
            return objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            LOGGER.error("fromJsonString - Error: ", e);
            return null;
        }
    }

    public static <T> T fromObjectNode(ObjectNode json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.convertValue(json, clazz);
        } catch (Exception e) {
            LOGGER.error("fromJsonNode - Error: ", e);
            return null;
        }
    }

    /**
     * Returns a map of strings from the given json string; or null if the given json string is null.
     */
    public static Map<String, String> stringToMap(String json) {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyMap();
        }
        try {
            return objectMapper.readValue(json, STRING_MAP_TYPE_REFERENCE);
        } catch (IOException e) {
            LOGGER.error("stringToMap - Error: ", e);
            return Collections.emptyMap();
        }
    }

    /**
     * Returns a map of strings from the given json string; or null if the given json string is null.
     */
    public static Map<String, Object> objectToMap(String json) {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyMap();
        }
        try {
            return objectMapper.readValue(json, OBJECT_MAP_TYPE_REFERENCE);
        } catch (IOException e) {
            LOGGER.error("objectToMap - Error: ", e);
            return Collections.emptyMap();
        }
    }

    /**
     * Convert json to List<T>
     */
    public static <T> List<T> stringToList(String json, Class<T> clazz) {

        if (StringUtils.isBlank(json)) {
            return Collections.emptyList();
        }

        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return objectMapper.readValue(json, listType);
        } catch (Exception e) {
            LOGGER.error("stringToList - Error: ", e);
            return Collections.emptyList();
        }
    }

    public static JsonNode jsonNodeOf(String json) {
        return fromJsonString(json, JsonNode.class);
    }

    public static JsonGenerator jsonGeneratorOf(Writer writer) throws IOException {
        return new JsonFactory().createGenerator(writer);
    }

    public static <T> T loadFrom(File file, Class<T> clazz) throws IOException {
        try {
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static ObjectWriter getWriter() {
        return writer;
    }

    public static ObjectWriter getPrettywriter() {
        return prettyWriter;
    }

    private Jackson() {
        // Default Constructor
    }
}

package hcmus.edu.vn.common;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;

public class MapperCommon {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapperCommon.class);
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    private static final ObjectWriter writer = objectMapper.writer();
	private static final ObjectWriter prettyWriter = objectMapper.writerWithDefaultPrettyPrinter();

	private static final TypeReference<HashMap<String, String>> STRING_MAP_TYPEREFERENCE = new TypeReference<HashMap<String, String>>() {};

	private static final TypeReference<HashMap<String, Object>> OBJECT_MAP_TYPEREFERENCE = new TypeReference<HashMap<String, Object>>() {};
	
	public static String toJsonString(Object value) {
		try {
			return writer.writeValueAsString(value);
		} catch (Exception e) {
			throw new IllegalStateException(e);
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
	 * Returns a map of strings from the given json string; or null if the given json string is null.
	 */
	public static Map<String, String> stringMapFromJsonString(String json) {
		if (StringUtils.isBlank(json)) {
			return Collections.emptyMap();
		}
		try {
			return objectMapper.readValue(json, STRING_MAP_TYPEREFERENCE);
		} catch (IOException e) {
			LOGGER.error("stringMapFromJsonString - Error: ", e);
			return Collections.emptyMap();
		}
	}
	
	public static Map<String, Object> objectMapFromJsonString(String json) {
		if (StringUtils.isBlank(json)) {
			return Collections.emptyMap();
		}
		try {
			return objectMapper.readValue(json, OBJECT_MAP_TYPEREFERENCE);
		} catch (IOException e) {
			LOGGER.error("objectMapFromJsonString - Error: ", e);
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
			LOGGER.error("fromJsonString - Error: ", e);
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

	private MapperCommon() {
		// Default Constructor
	}
}

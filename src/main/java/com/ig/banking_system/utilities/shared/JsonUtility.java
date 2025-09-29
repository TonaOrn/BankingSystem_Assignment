package com.ig.banking_system.utilities.shared;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for JSON serialization and deserialization using Jackson's ObjectMapper.
 * Corresponds to the Kotlin JsonUtility class.
 */
@Component
public class JsonUtility {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Serializes an object to a byte array.
     * @param value The object to serialize.
     * @param <T> The type of the object.
     * @return The object serialized as a byte array.
     * @throws RuntimeException if serialization fails (wrapping IOException).
     */
    public <T> byte[] toBytes(T value) {
        try {
            return mapper.writeValueAsBytes(value);
        } catch (IOException e) {
            throw new RuntimeException("Error serializing object to bytes", e);
        }
    }

    /**
     * Serializes an object to a JSON string.
     * @param value The object to serialize.
     * @param <T> The type of the object.
     * @return The object serialized as a JSON string.
     * @throws RuntimeException if serialization fails (wrapping IOException).
     */
    public <T> String toString(T value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (IOException e) {
            throw new RuntimeException("Error serializing object to string", e);
        }
    }

    /**
     * Converts a list of objects into a single string, joining them with "<br>".
     * Note: This method is NOT standard JSON serialization; it's a custom string formatting function.
     * @param value The list of objects.
     * @param <T> The type of the objects in the list.
     * @return A single string representation of the list.
     */
    public <T> String toString(List<T> value) {
        return value.stream()
                    .map(item -> "- " + item.toString())
                    .collect(Collectors.joining("<br>"));
    }

    /**
     * Serializes an object to a pretty-printed JSON string.
     * @param value The object to serialize.
     * @param <T> The type of the object.
     * @return The object serialized as a pretty-printed JSON string, or null on error.
     */
    public <T> String toPrettyString(T value) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Deserializes a JSON string to an object of the specified class.
     * @param content The JSON string content.
     * @param clazz The class to deserialize the content into.
     * @param <T> The target type.
     * @return The deserialized object.
     * @throws RuntimeException if deserialization fails (wrapping IOException).
     */
    public <T> T parse(String content, Class<T> clazz) {
        if (content == null || clazz == null) {
            throw new IllegalArgumentException("Content and class must not be null for parsing.");
        }
        try {
            return mapper.readValue(content, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON string to object of class " + clazz.getName(), e);
        }
    }

    /**
     * Deserializes a JSON string into a list of objects of the specified class.
     * @param content The JSON string content for the list.
     * @param clazz The class of the elements in the list.
     * @param <T> The type of the elements in the list.
     * @return The deserialized list, or null if deserialization fails or inputs are null.
     */
    public <T> List<T> parseAsList(String content, Class<T> clazz) {
        if (content == null || clazz == null) {
            return null;
        }
        try {

            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, clazz);

            return mapper.readValue(content, type);
        } catch (IOException e) {
            // Returning null on error, matching the nullable return type of the Kotlin function.
            return null;
        }
    }
}
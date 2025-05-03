package org.example.config;

import java.util.Map;

/**
 * custom exception to handle creation of product
 * returns error map to display in UI
 * it maps the field of the object to its error message
 */
public class ObjectCreationException extends RuntimeException {
    private final Map<String, String> errorMap;

    public ObjectCreationException(Map<String, String> errorMap) {
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}

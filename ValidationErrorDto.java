package com.fisglobal.taxreporting.controller.authentication.validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * In case of failing validations, this class could be used to transport detail
 * information to the calling client.
 *
 * @author Stefan Corban
 */
@Schema(
        description = "EN: validation errors,<br>DE: Informationen zu den Validierungsfehlern",
        title = "ValidationError")
public class ValidationErrorDto {
    /**
     * Holding field validation errors to be send to the client.
     */
    @Schema(
            description = "EN: field validation error description,<br>DE: Validierungsfehlerbeschreibung",
            title = "ValidationErrorField")
    public static class ValidationErrorFieldDto {
        private String field;
        private String message;

        /**
         * Constructor
         *
         * @param field
         *                    containing the invalid value
         * @param message
         *                    with error description
         */
        public ValidationErrorFieldDto(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "ValidationErrorFieldDto [field=" + field + ", message=" + message + "]";
        }
    }

    private String httpMethod;
    private String uri;
    private List<ValidationErrorFieldDto> validationErrors = new ArrayList<ValidationErrorFieldDto>();

    /**
     * Constructor
     *
     * @param method
     *                   aka POST, GET, ...
     * @param uri
     *                   the request URI
     */
    public ValidationErrorDto(String method, String uri) {
        this.httpMethod = method;
        this.uri = uri;
    }

    public void addValidationErrorField(String field, String message) {
        ValidationErrorFieldDto validationErrorFieldDto = new ValidationErrorFieldDto(field, message);
        validationErrors.add(validationErrorFieldDto);
    }

    public String getMethod() {
        return httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public List<ValidationErrorFieldDto> getValidationErrors() {
        return validationErrors;
    }

    @Override
    public String toString() {
        return "ValidationErrorDto [httpMethod=" + httpMethod + ", uri=" + uri + ", validationErrors="
                + Arrays.toString(validationErrors.toArray()) + "]";
    }
}

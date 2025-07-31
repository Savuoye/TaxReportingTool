package com.fisglobal.taxreporting.exception;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.HtmlUtils;

import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * The class that will be called whenever an exception is thrown in your
 * application.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private Environment environment;

    private static final KORLogger LOG = KORLogger.getLogger(ApplicationExceptionHandler.class);

    /**
     * @param recordNotFoundException
     *                                    Exception handler for Tax Reports Record Not Found
     *                                    Exception
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ValidationResults> handleRecordNotFoundException(
            final RecordNotFoundException recordNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                .body(recordNotFoundException.getRequestValidationResult());
    }

    /**
     * @param mandatoryFieldValidationException
     *                                              Exception handler for Mandatory Field
     *                                              Validations
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(MandatoryFieldValidationException.class)
    public ResponseEntity<ValidationResults> handleMandatoryFieldValidationException(
            final MandatoryFieldValidationException mandatoryFieldValidationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(mandatoryFieldValidationException.getRequestValidationResult());
    }

    /**
     * @param validationResultsException
     *                                       Exception handler for Validation Results Exception
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(ValidationResultsException.class)
    public ResponseEntity<ValidationResults> handleValidationResultsException(
            final ValidationResultsException validationResultsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(validationResultsException.getRequestValidationResult());
    }

    /**
     * @param runtimeException
     *                             Exception handler for all RuntimeException
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ValidationResults> handleRuntimeException(final RuntimeException runtimeException) {
        LOG.error("Unkown error detected!", HtmlUtils.htmlEscape(runtimeException.toString()));
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.ERROR, "backendInternalServerError",
                runtimeException.getMessage(), null);
        if (Arrays.asList(this.environment.getActiveProfiles()).contains("test")) {
            throw runtimeException;
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                .body(validationResults);
    }

    /**
     * @param authenticationException
     *                                    Exception handler for unauthorized exception
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ValidationResults> handleAuthenticationException(
            final AuthenticationException authenticationException) {
        LOG.error("[handleAuthenticationException] Unauthorized exception {}", authenticationException.getMessage());
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.ERROR, "401 Unauthorized", "Unauthorized exception",
                CommonConstants.LOGIN);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON)
                .body(validationResults);
    }

    /**
     * @param accessDeniedException
     *                                  Exception handler for Access denied, e.g. wrong password,
     *                                  role.
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ValidationResults> handleAccessDeniedException(
            final AccessDeniedException accessDeniedException) {
        LOG.error("[handleAccessDeniedException] Access denied exception {}", accessDeniedException.getMessage());
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.ERROR, "403 Access Denied",
                accessDeniedException.getMessage(), CommonConstants.LOGIN);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
                .body(validationResults);
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        // Get all fields errors
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }

    /**
     * @param constraintViolationException
     *                                         Exception handler for ConstraintViolationException
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationResults> handleConstraintViolationExceptionException(
            final ConstraintViolationException constraintViolationException) {
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.ERROR, "400 Bad Request",
                constraintViolationException.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(validationResults);
    }

    /**
     * @param methodArgumentTypeMismatchException
     *                                                Exception handler for
     *                                                MethodArgumentTypeMismatchException
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ValidationResults> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.ERROR, "400 Bad Request",
                methodArgumentTypeMismatchException.getMessage(), methodArgumentTypeMismatchException.getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(validationResults);
    }

    /**
     * Override the parent class method handleMissingServletRequestParameter and
     * return ResponseEntity of ValidationResults when required request parameter is
     * missing in the request
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.ERROR, "400 Bad Request", ex.getMessage(),
                ex.getParameterName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(validationResults);
    }

    /**
     * @param unauthorizedValidationException
     *                                            Exception handler for Unauthorized Field
     *                                            Validations
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(UnauthorizedValidationException.class)
    public ResponseEntity<ValidationResults> handleUnauthorizedValidationException(
            final UnauthorizedValidationException unauthorizedValidationException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON)
                .body(unauthorizedValidationException.getRequestValidationResult());
    }

    /**
     * @param npe
     *                Exception handler for unhandled null pointer exception
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ValidationResults> handleRuntimeException(final NullPointerException npe) {
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.ERROR, "backendInternalServerError",
                npe != null ? npe.getMessage() : null,
                npe != null && npe.getClass() != null ? npe.getClass().getSimpleName() : "NullPointerException");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                .body(validationResults);
    }

    /**
     * @param jpaSystemException
     *                               Exception handler for db inconsistency exception
     * 
     * @return ResponseEntity ValidationResults
     */
    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ValidationResults> handleJpaRuntimeException(final JpaSystemException jpaSystemException) {
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.ERROR, "dataInconsistency",
                "Data inconsistency in database", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                .body(validationResults);
    }
}

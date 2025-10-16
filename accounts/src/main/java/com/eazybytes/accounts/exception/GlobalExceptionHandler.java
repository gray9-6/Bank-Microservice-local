package com.eazybytes.accounts.exception;

import com.eazybytes.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This annotation indicates that the class will handle exceptions globally across all controllers.
// If any controller throws an exception, this class can catch and process it using @ExceptionHandler methods.
// It allows for centralized exception handling, making it easier to manage and maintain error responses in a consistent manner.
// By using @ControllerAdvice, you can define how different types of exceptions should be handled and what responses should be sent back to the client.
// It helps in separating error handling logic from the main business logic in controllers.
// This class extends ResponseEntityExceptionHandler to leverage its built-in exception handling capabilities, for example, handling validation errors, besides custom exceptions.
// Because framework knows what to do when there is a validation error, so we are just overriding that method to provide our custom response.
// This class provides a centralized way to handle exceptions thrown by controllers
// while also allowing for custom exception handling methods to be defined.
@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler { // Extending ResponseEntityExceptionHandler to override its methods for custom exception handling, like validation errors.


    // This method handles validation errors that occur when method arguments annotated with @Valid fail validation.
    // It overrides the default behavior to provide a custom response containing validation error details.
    // For example, if a request body fails validation, this method will be triggered.
    // The MethodArgumentNotValidException contains details about the validation errors.
    // The method constructs a map of field names to error messages and returns it with a 400 Bad Request status.
    // The parameters include the exception, HTTP headers, status code, and the web request context.
    // The response entity contains the validation errors and the HTTP status code.
    // This method is automatically invoked by Spring when a validation error occurs.
    // The @Override annotation indicates that this method overrides a method in the superclass.
    // The method is protected, meaning it can be accessed within its package and by subclasses.
    // The method returns a ResponseEntity containing the validation errors and a BAD_REQUEST status.
    // The validation errors are extracted from the exception and formatted into a map.
    // Each entry in the map corresponds to a field that failed validation and its associated error message.
    // This provides a clear and structured way to inform clients about validation issues in their requests.
    // This method enhances the user experience by providing detailed feedback on what went wrong during validation.
    // It helps clients understand and correct their input to meet the required criteria.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( // Arguments include the exception, HTTP headers, status code, and web request context. exception means the exception that was thrown when method argument validation failed.
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) { // Arguments include the exception, HTTP headers, status code, and web request context.

        // Creating a map to hold field names and their corresponding validation error messages.
        Map<String, String> validationErrors = new HashMap<>();

        // Extracting all validation errors from the exception. The exception contains details about which fields failed validation and why.
        // We are getting all the errors from the binding result of the exception. Binding result is an object that holds the results of a validation and binding and contains errors that may have occurred.
        // We are getting all the errors from that binding result.
        // This Exception is thrown when validation on an argument annotated with @Valid fails.
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        // Iterating through each validation error to populate the map with field names and error messages.
        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField(); // Getting the name of the field that caused the validation error. FieldError is a subclass of ObjectError that represents a validation error on a specific field of an object. We are casting the ObjectError to FieldError to access the getField() method. This method returns the name of the field that caused the error.
            String validationMsg = error.getDefaultMessage(); // Getting the default error message associated with the validation error. This message is typically defined in the validation annotation. For example, if a field is annotated with @NotNull(message = "Field cannot be null"), this message will be retrieved here. The getDefaultMessage() method returns the default message for this error.
            validationErrors.put(fieldName, validationMsg); // Adding the field name and error message to the map.
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST); // Returning a ResponseEntity containing the map of validation errors and a 400 Bad Request status.
    }


    // This Will Handle the Run time Exception if the Exception is not handled, then this will trigger
    // and return the 500 - Internal Server Error
    // This method is a global exception handler that catches all unhandled exceptions in the application.
    // It constructs a custom error response with details about the exception and returns it with a 500 Internal Server Error status.
    // The parameters include the exception and the web request context.
    // The response entity contains the error details and the HTTP status code.
    // The @ExceptionHandler annotation indicates that this method will handle exceptions of type Exception.
    // The method is public, meaning it can be accessed from anywhere in the application.
    // The method returns a ResponseEntity containing the error details and an INTERNAL_SERVER_ERROR status.
    // The error details include the request description, HTTP status, exception message, and timestamp.
    // This method provides a fallback mechanism to ensure that all exceptions are handled gracefully.
    // It helps maintain application stability and provides useful feedback to clients when unexpected errors occur.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
                                                                  WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false), // false means i just want api path from the web request, and if i pass true , then it will send all the other information like ip address, etc
                HttpStatus.INTERNAL_SERVER_ERROR, // because its a server side error, so its 500
                exception.getMessage(), // this will send the message that we have defined in the exception class when we throw the exception in the service class like "Some unexpected error occurred"
                LocalDateTime.now() // current timestamp
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR); // returning the response entity with the error response dto and the http status
    }

    // This method handles the ResourceNotFoundException thrown by any controller.
    // When this exception is thrown, the method annotated with this will be invoked to return a custom response.
    // The parameters include the exception and the web request context.
    // The response entity contains the error details and the HTTP status code.
    // The @ExceptionHandler annotation indicates that this method will handle exceptions of type ResourceNotFoundException.
    // The method is public, meaning it can be accessed from anywhere in the application.
    // The method returns a ResponseEntity containing the error details and a NOT_FOUND status.
    // The error details include the request description, HTTP status, exception message, and timestamp.
    // This method provides a specific mechanism to handle cases where a requested resource is not found.
    // It helps inform clients about the absence of the requested resource in a clear and structured manner.
    // This enhances the user experience by providing meaningful feedback when resources cannot be located.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                            WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    // This method handles the CustomerAlreadyExistsException thrown by any controller.
    // When this exception is thrown, the method annotated with this will be invoked to return a custom response.
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException exception,
                                                                                 WebRequest webRequest){
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false), // false means i just want api path from the web request, and if i pass true , then it will send all the other information like ip address, etc
                HttpStatus.BAD_REQUEST, // because client is trying to create the same customer again, so its a bad request from client side to create the same customer again
                exception.getMessage(), // this will send the message that we have defined in the exception class when we throw the exception in the service class like "Customer with mobile number xxx already exists"
                LocalDateTime.now() // current timestamp
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST); // returning the response entity with the error response dto and the http status
    }

}
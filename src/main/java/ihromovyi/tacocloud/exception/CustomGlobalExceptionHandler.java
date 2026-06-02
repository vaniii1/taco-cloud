package ihromovyi.tacocloud.exception;

import com.stripe.exception.StripeException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::getErrorMessage)
                .toList();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler({
            IngredientNotFoundException.class,
            TacoNotFoundException.class,
            TacoOrderNotFoundException.class,
            EntityNotFoundException.class,
            UserNotFoundException.class,
            PaymentNotFoundException.class,
            ItemNotFoundException.class,
            CartNotFoundException.class,
            OrderNotFoundException.class,
    })
    public ResponseEntity<Object> handleNotFoundException(RuntimeException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, List.of(e.getMessage()));
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            UserAlreadyRegisteredException.class,
            ConstraintViolationException.class,
            StripeException.class
    })
    public ResponseEntity<Object> handleBadRequestException(Exception e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(e.getMessage()));
    }

    @ExceptionHandler({
            AuthorizationDeniedException.class,
            ForbiddenItemException.class
    })
    public ResponseEntity<Object> handleAuthorizationDeniedException(RuntimeException e) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, List.of(e.getMessage()));
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, List<String> errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("errors", errors);
        return ResponseEntity.status(status).body(body);
    }

    private String getErrorMessage(ObjectError objectError) {
        if (objectError instanceof FieldError fieldError) {
            return fieldError.getField() + " " + fieldError.getDefaultMessage();
        }
        return objectError.getDefaultMessage();
    }
}


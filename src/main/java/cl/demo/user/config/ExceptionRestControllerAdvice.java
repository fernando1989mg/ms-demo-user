package cl.demo.user.config;

import cl.demo.user.domain.exception.ExceptionResponseMessage;
import cl.demo.user.domain.exception.ResourceNotFoundException;
import cl.demo.user.domain.exception.UsernameExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponseMessage handleInvalidParameterException(RuntimeException ex) {

        return sendResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponseMessage handleUsernameNotFoundException(RuntimeException ex) {

        return sendResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler({UsernameExistsException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ExceptionResponseMessage handleUsernameExistsException(RuntimeException ex) {

        return sendResponse(HttpStatus.FORBIDDEN, ex);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    public ExceptionResponseMessage handleAuthenticationExceptions(AuthenticationException ex, HttpServletResponse response) {
        return sendResponse(HttpStatus.UNAUTHORIZED, ex);
    }

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponseMessage handleRuntimeException(Throwable ex) {

        return sendResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionResponseMessage>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        // Get the error messages for invalid fields
        List<FieldError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldError(fieldError.getField(),fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        List<ExceptionResponseMessage> messages = errors.stream()
                .map(error ->
                        new ExceptionResponseMessage(
                                Instant.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                error.getDefaultMessage())
                )
                .collect(Collectors.toList());


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
    }

    private ExceptionResponseMessage sendResponse(HttpStatus status, Throwable ex) {

        return new ExceptionResponseMessage(Instant.now(), status.value(), status.getReasonPhrase(), ex.getMessage());
    }
}

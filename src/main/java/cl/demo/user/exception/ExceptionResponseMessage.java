package cl.demo.user.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class ExceptionResponseMessage {
    private Instant time;
    private int status;
    private String error;
    private String message;
}

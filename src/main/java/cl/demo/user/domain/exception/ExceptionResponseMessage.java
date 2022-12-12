package cl.demo.user.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionResponseMessage {
    private Instant time;
    private int status;
    private String error;
    private String message;
}

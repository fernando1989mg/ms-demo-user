package cl.demo.user.domain.exception;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponseMessage {
    private Instant time;
    private int status;
    private String error;
    private String message;
}

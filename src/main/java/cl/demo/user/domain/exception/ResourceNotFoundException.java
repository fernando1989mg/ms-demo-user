package cl.demo.user.domain.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
package cl.demo.user.domain.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String msg) {
        super(msg);
    }
}

package cl.demo.user.domain.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsernameNotFoundException extends ResourceNotFoundException {
    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}

package cl.demo.user.exception;

public class UsernameNotFoundException extends ResourceNotFoundException {
    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}

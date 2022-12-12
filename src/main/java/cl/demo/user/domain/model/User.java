package cl.demo.user.domain.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    private Instant lastLogin;

    private Boolean isActive;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
    private List<UserPhone> phones;

    private String token;

    private Instant tokenExpiration;
}

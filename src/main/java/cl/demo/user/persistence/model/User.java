package cl.demo.user.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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

    @OneToMany(mappedBy = "userId", cascade = { CascadeType.ALL })
    private List<UserPhone> userPhones;

    private String token;

    private Instant tokenExpiration;
}

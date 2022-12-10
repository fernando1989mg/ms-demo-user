package cl.demo.user.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="user_phones")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserPhone {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private Integer number;
    private Integer cityCode;
    private Integer countryCode;
}

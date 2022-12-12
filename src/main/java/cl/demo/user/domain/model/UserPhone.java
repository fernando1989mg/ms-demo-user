package cl.demo.user.domain.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name="user_phones")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private User user;


    private Integer number;

    private Integer cityCode;

    private Integer countryCode;
}

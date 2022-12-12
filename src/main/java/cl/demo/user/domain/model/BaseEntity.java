package cl.demo.user.domain.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
public class BaseEntity {
    @Column(updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;
}

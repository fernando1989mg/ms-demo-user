package cl.demo.user.persistence.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
public class BaseEntity {
    @Column(name="created_at", updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name="modified_at")
    @UpdateTimestamp
    private Instant updatedAt;
}

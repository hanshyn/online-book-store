package hanshyn.onlinebookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "statuses")
@Entity
public class OrderStatus {
    @Column(name = "status_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        COMPLETED,
        PENDING,
        DELIVERED,
        CANCELLED
    }
}

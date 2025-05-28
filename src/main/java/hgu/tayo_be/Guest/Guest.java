package hgu.tayo_be.Guest;

import hgu.tayo_be.Ride.Ride;
import hgu.tayo_be.User.User;
import io.swagger.v3.oas.models.media.UUIDSchema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "guests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID guestId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ride_id", nullable = false)
    private Ride ride;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    public enum PaymentStatus {
        PENDING,      // Initial state
        PAYMENT_SENT, // Guest marked as sent
        CONFIRMED     // Host confirmed payment
    }
}
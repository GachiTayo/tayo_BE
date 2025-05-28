package hgu.tayo_be.Guest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {
    private UUID guestId;
    private UUID userId;
    private Long rideId;
    private String name;
    private Guest.PaymentStatus paymentStatus;
}
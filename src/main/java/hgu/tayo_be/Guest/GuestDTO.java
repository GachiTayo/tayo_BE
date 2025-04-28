package hgu.tayo_be.Guest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {
    private Long guestId;
    private Long userId;
    private Long rideId;
    private String name;
    private Guest.PaymentStatus paymentStatus;
}

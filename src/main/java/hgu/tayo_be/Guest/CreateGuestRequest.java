package hgu.tayo_be.Guest;
import lombok.Data;

@Data
public class CreateGuestRequest {

    private Long userId;

    private Long rideId;
}

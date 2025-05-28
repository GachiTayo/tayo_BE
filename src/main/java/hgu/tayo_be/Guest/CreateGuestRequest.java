package hgu.tayo_be.Guest;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateGuestRequest {
    private UUID userId;
    private Long rideId;
}
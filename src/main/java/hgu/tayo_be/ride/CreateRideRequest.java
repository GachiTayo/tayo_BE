package hgu.tayo_be.Ride;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class CreateRideRequest {
    private Long hostId;
    private Ride.RideType type;
    private LocalDate startDay;
    private LocalDate endDay;
    private LocalTime time;
    private List<String> stops;
    private String announcement;
    private Integer price;
    private Integer guestNumber;
}
package hgu.tayo_be.Ride;

import hgu.tayo_be.Guest.GuestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideDTO {
    private Long rideId;
    private HostDTO host;
    private Ride.RideType type;
    private LocalDate startDay;
    private LocalDate endDay;
    private String departureTime;
    private List<String> stops;
    private String announcement;
    private Integer price;
    private Integer guestNumber;
    private List<GuestDTO> guests;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HostDTO {
        private UUID userId;
        private String name;
        private String bankAccount;
        private String carNum;
    }
}
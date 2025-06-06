package hgu.tayo_be.Ride;

import hgu.tayo_be.Guest.GuestDTO;
import hgu.tayo_be.User.User;
import hgu.tayo_be.User.UserRepository;
import hgu.tayo_be.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Transactional
    public RideDTO createRide(CreateRideRequest request) {
        User host = userRepository.findById(request.getHostId())
                .orElseThrow(() -> new ResourceNotFoundException("Host not found with id: " + request.getHostId()));

        // Initialize stops with 6 empty slots
        List<String> stops = new ArrayList<>(6);
        if (request.getStops() != null && !request.getStops().isEmpty()) {
            stops.addAll(request.getStops());
        }
        while (stops.size() < 6) {
            stops.add("");
        }

        Ride ride = Ride.builder()
                .host(host)
                .type(request.getType())
                .startDay(request.getStartDay())
                .stops(stops)
                .announcement(request.getAnnouncement())
                .price(request.getPrice())
                .guestNumber(request.getGuestNumber())
                .build();

        // Parse the HH:MM string to LocalTime
        if (request.getDepartureTime() != null && !request.getDepartureTime().isEmpty()) {
            try {
                LocalTime departureTime = LocalTime.parse(request.getDepartureTime(), TIME_FORMATTER);
                ride.setDepartureTime(departureTime);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid time format. Please use HH:mm format.");
            }
        }

        // Set endDay only for FIXED type
        if (request.getType() == Ride.RideType.FIXED) {
            ride.setEndDay(request.getEndDay());
        }

        Ride savedRide = rideRepository.save(ride);
        return mapToDTO(savedRide);
    }

    public RideDTO getRideById(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + rideId));
        return mapToDTO(ride);
    }

    public List<RideDTO> getRidesByHostId(UUID hostId) {
        User host = userRepository.findById(hostId)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found with id: " + hostId));

        List<Ride> rides = rideRepository.findByHost(host);
        return rides.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<RideDTO> getUpcomingRides() {
        List<Ride> rides = rideRepository.findByStartDayGreaterThanEqual(LocalDate.now());
        return rides.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<RideDTO> getUpcomingRidesByType(Ride.RideType type) {
        List<Ride> rides = rideRepository.findByTypeAndStartDayGreaterThanEqual(type, LocalDate.now());
        return rides.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RideDTO updateRide(Long rideId, UpdateRideRequest request) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + rideId));

        if (request.getType() != null) {
            ride.setType(request.getType());
        }

        if (request.getStartDay() != null) {
            ride.setStartDay(request.getStartDay());
        }

        // Handle endDay based on type
        if (request.getType() == Ride.RideType.FIXED) {
            ride.setEndDay(request.getEndDay());
        } else {
            ride.setEndDay(null);
        }

        if (request.getDepartureTime() != null && !request.getDepartureTime().isEmpty()) {
            try {
                LocalTime departureTime = LocalTime.parse(request.getDepartureTime(), TIME_FORMATTER);
                ride.setDepartureTime(departureTime);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid time format. Please use HH:mm format.");
            }
        }

        if (request.getStops() != null) {
            List<String> stops = new ArrayList<>(request.getStops());
            while (stops.size() < 6) {
                stops.add("");
            }
            ride.setStops(stops);
        }

        if (request.getAnnouncement() != null) {
            ride.setAnnouncement(request.getAnnouncement());
        }

        if (request.getPrice() != null) {
            ride.setPrice(request.getPrice());
        }

        if (request.getGuestNumber() != null) {
            ride.setGuestNumber(request.getGuestNumber());
        }

        Ride updatedRide = rideRepository.save(ride);
        return mapToDTO(updatedRide);
    }

    @Transactional
    public void deleteRide(Long rideId) {
        if (!rideRepository.existsById(rideId)) {
            throw new ResourceNotFoundException("Ride not found with id: " + rideId);
        }
        rideRepository.deleteById(rideId);
    }

    private RideDTO mapToDTO(Ride ride) {
        User host = ride.getHost();

        RideDTO.HostDTO hostDTO = RideDTO.HostDTO.builder()
                .userId(host.getUserId())
                .name(host.getName())
                .bankAccount(host.getBankAccount())
                .carNum(host.getCarNum())
                .build();

        // Format LocalTime to HH:mm string for the DTO
        String formattedTime = null;
        if (ride.getDepartureTime() != null) {
            formattedTime = ride.getDepartureTime().format(TIME_FORMATTER);
        }

        return RideDTO.builder()
                .rideId(ride.getRideId())
                .host(hostDTO)
                .type(ride.getType())
                .startDay(ride.getStartDay())
                .endDay(ride.getEndDay())
                .departureTime(formattedTime)
                .stops(ride.getStops())
                .announcement(ride.getAnnouncement())
                .price(ride.getPrice())
                .guestNumber(ride.getGuestNumber())

                .guests(
                        ride.getGuests() == null ? new ArrayList<>() :
                                ride.getGuests().stream()
                                        .map(guest -> GuestDTO.builder()
                                                .guestId(guest.getGuestId())
                                                .userId(guest.getUser().getUserId())
                                                .name(guest.getUser().getName())
                                                .paymentStatus(guest.getPaymentStatus())
                                                .build())
                                        .collect(Collectors.toList())
                ).build();
    }
}
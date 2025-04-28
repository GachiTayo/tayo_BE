package hgu.tayo_be.Guest;
import hgu.tayo_be.Ride.Ride;
import hgu.tayo_be.Ride.RideRepository;
import hgu.tayo_be.User.User;
import hgu.tayo_be.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hgu.tayo_be.exception.ResourceNotFoundException;
import hgu.tayo_be.exception.AlreadyExistsException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    @Transactional
    public GuestDTO addGuestToRide(CreateGuestRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + request.getRideId()));

        // Check if user is already a guest in this ride
        if (guestRepository.existsByUserAndRide(user, ride)) {
            throw new AlreadyExistsException("User is already a guest in this ride");
        }

        // Check if there's room for more guests
        List<Guest> currentGuests = guestRepository.findByRide(ride);
        if (currentGuests.size() >= ride.getGuestNumber()) {
            throw new AlreadyExistsException("Ride is already full");
        }

        Guest guest = Guest.builder()
                .user(user)
                .ride(ride)
                .paymentStatus(Guest.PaymentStatus.PENDING)
                .build();

        Guest savedGuest = guestRepository.save(guest);
        return mapToDTO(savedGuest);
    }

    public GuestDTO getGuestById(Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + guestId));
        return mapToDTO(guest);
    }

    public List<GuestDTO> getGuestsByRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + rideId));

        List<Guest> guests = guestRepository.findByRide(ride);
        return guests.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<GuestDTO> getGuestsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Guest> guests = guestRepository.findByUser(user);
        return guests.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GuestDTO updatePaymentStatus(Long guestId, UpdatePaymentStatusRequest request) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + guestId));

        guest.setPaymentStatus(request.getPaymentStatus());
        Guest updatedGuest = guestRepository.save(guest);
        return mapToDTO(updatedGuest);
    }

    @Transactional
    public void removeGuestFromRide(Long guestId) {
        if (!guestRepository.existsById(guestId)) {
            throw new ResourceNotFoundException("Guest not found with id: " + guestId);
        }
        guestRepository.deleteById(guestId);
    }

    private GuestDTO mapToDTO(Guest guest) {
        return GuestDTO.builder()
                .guestId(guest.getGuestId())
                .userId(guest.getUser().getUserId())
                .rideId(guest.getRide().getRideId())
                .name(guest.getUser().getName())
                .paymentStatus(guest.getPaymentStatus())
                .build();
    }
}
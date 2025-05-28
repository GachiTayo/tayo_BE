package hgu.tayo_be.Guest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @Operation(summary = "Add a guest to a ride", description = "Adds a new guest to a specified ride with the provided information.")
    @PostMapping
    public ResponseEntity<GuestDTO> addGuestToRide(@RequestBody CreateGuestRequest request) {
        return new ResponseEntity<>(guestService.addGuestToRide(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Get guest by ID", description = "Retrieves the details of a guest by their ID.")
    @GetMapping("/{guestId}")
    public ResponseEntity<GuestDTO> getGuestById(@PathVariable UUID guestId) {
        return ResponseEntity.ok(guestService.getGuestById(guestId));
    }

    @Operation(summary = "Get guests by ride ID", description = "Retrieves a list of all guests participating in a specific ride.")
    @GetMapping("/ride/{rideId}")
    public ResponseEntity<List<GuestDTO>> getGuestsByRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(guestService.getGuestsByRide(rideId));
    }

    @Operation(summary = "Get guests by user ID", description = "Retrieves a list of all rides a user is participating in as a guest.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GuestDTO>> getGuestsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(guestService.getGuestsByUser(userId));
    }

    @Operation(summary = "Update guest payment status", description = "Updates the payment status of a guest for a ride.")
    @PatchMapping("/{guestId}/payment-status")
    public ResponseEntity<GuestDTO> updatePaymentStatus(
            @PathVariable UUID guestId,
            @RequestBody UpdatePaymentStatusRequest request) {
        return ResponseEntity.ok(guestService.updatePaymentStatus(guestId, request));
    }

    @Operation(summary = "Remove guest from ride", description = "Removes a guest from a ride by their guest ID.")
    @DeleteMapping("/{guestId}")
    public ResponseEntity<Void> removeGuestFromRide(@PathVariable UUID guestId) {
        guestService.removeGuestFromRide(guestId);
        return ResponseEntity.noContent().build();
    }
}
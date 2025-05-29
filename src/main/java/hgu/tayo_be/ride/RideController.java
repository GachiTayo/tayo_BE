package hgu.tayo_be.Ride;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @Operation(summary = "Create a new ride", description = "Creates a new ride with the provided details.")
    @PostMapping
    public ResponseEntity<RideDTO> createRide(@RequestBody CreateRideRequest request) {
        RideDTO createdRide = rideService.createRide(request);
        return new ResponseEntity<>(createdRide, HttpStatus.CREATED);
    }

    @Operation(summary = "Get ride by ID", description = "Retrieves a ride's details by its ID.")
    @GetMapping("/{rideId}")
    public ResponseEntity<RideDTO> getRideById(@PathVariable Long rideId) {
        RideDTO ride = rideService.getRideById(rideId);
        return ResponseEntity.ok(ride);
    }

    @Operation(summary = "Get rides by host ID", description = "Retrieves all rides hosted by a specific user.")
    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<RideDTO>> getRidesByHostId(@PathVariable UUID hostId) {
        List<RideDTO> rides = rideService.getRidesByHostId(hostId);
        return ResponseEntity.ok(rides);
    }

    @Operation(summary = "Get upcoming rides", description = "Retrieves a list of all upcoming rides.")
    @GetMapping("/upcoming")
    public ResponseEntity<List<RideDTO>> getUpcomingRides() {
        List<RideDTO> rides = rideService.getUpcomingRides();
        return ResponseEntity.ok(rides);
    }

    @Operation(summary = "Get upcoming rides by type", description = "Retrieves upcoming rides filtered by ride type.")
    @GetMapping("/upcoming/type/{type}")
    public List<RideDTO> getUpcomingRidesByType(@PathVariable Ride.RideType type) {
        return rideService.getUpcomingRidesByType(type);
    }

    @Operation(summary = "Update a ride", description = "Updates the details of an existing ride by its ID.")
    @PatchMapping("/{rideId}")
    public ResponseEntity<RideDTO> updateRide(
            @PathVariable Long rideId,
            @RequestBody UpdateRideRequest request) {
        RideDTO updatedRide = rideService.updateRide(rideId, request);
        return ResponseEntity.ok(updatedRide);
    }

    @Operation(summary = "Delete a ride", description = "Deletes a ride by its ID.")
    @DeleteMapping("/{rideId}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long rideId) {
        rideService.deleteRide(rideId);
        return ResponseEntity.noContent().build();
    }
}
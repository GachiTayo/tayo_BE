package hgu.tayo_be.Ride;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping
    public ResponseEntity<RideDTO> createRide( @RequestBody CreateRideRequest request) {
        RideDTO createdRide = rideService.createRide(request);
        return new ResponseEntity<>(createdRide, HttpStatus.CREATED);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideDTO> getRideById(@PathVariable Long rideId) {
        RideDTO ride = rideService.getRideById(rideId);
        return ResponseEntity.ok(ride);
    }

    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<RideDTO>> getRidesByHostId(@PathVariable Long hostId) {
        List<RideDTO> rides = rideService.getRidesByHostId(hostId);
        return ResponseEntity.ok(rides);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<RideDTO>> getUpcomingRides() {
        List<RideDTO> rides = rideService.getUpcomingRides();
        return ResponseEntity.ok(rides);
    }

    @GetMapping("/upcoming/type/{type}")
    public ResponseEntity<List<RideDTO>> getUpcomingRidesByType(
            @PathVariable Ride.RideType type) {
        List<RideDTO> rides = rideService.getUpcomingRidesByType(type);
        return ResponseEntity.ok(rides);
    }

    @PatchMapping("/{rideId}")
    public ResponseEntity<RideDTO> updateRide(
            @PathVariable Long rideId,
             @RequestBody UpdateRideRequest request) {
        RideDTO updatedRide = rideService.updateRide(rideId, request);
        return ResponseEntity.ok(updatedRide);
    }

    @DeleteMapping("/{rideId}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long rideId) {
        rideService.deleteRide(rideId);
        return ResponseEntity.noContent().build();
    }
}
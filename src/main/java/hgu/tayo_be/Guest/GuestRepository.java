package hgu.tayo_be.Guest;

import hgu.tayo_be.Ride.Ride;
import hgu.tayo_be.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByUser(User user);
    List<Guest> findByRide(Ride ride);
    Optional<Guest> findByUserAndRide(User user, Ride ride);
    boolean existsByUserAndRide(User user, Ride ride);
}
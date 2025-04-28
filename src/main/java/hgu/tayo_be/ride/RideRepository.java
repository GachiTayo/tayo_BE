package hgu.tayo_be.Ride;
import hgu.tayo_be.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByHost(User host);
    List<Ride> findByStartDayGreaterThanEqual(LocalDate date);
    List<Ride> findByTypeAndStartDayGreaterThanEqual(Ride.RideType type, LocalDate date);
}
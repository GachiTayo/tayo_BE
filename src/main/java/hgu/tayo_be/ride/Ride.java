package hgu.tayo_be.Ride;

import hgu.tayo_be.Guest.Guest;
import hgu.tayo_be.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rides")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RideType type;

    @Column(name = "start_day", nullable = false)
    private LocalDate startDay;

    @Column(name = "end_day")
    private LocalDate endDay;

    @Column(nullable = false)
    private LocalTime time;

    @ElementCollection
    @CollectionTable(name = "ride_stops", joinColumns = @JoinColumn(name = "ride_id"))
    @Column(name = "stop")
    @OrderColumn(name = "stop_order")
    private List<String> stops = new ArrayList<>(6);

    @Column(columnDefinition = "TEXT")
    private String announcement;

    private Integer price;

    @Column(name = "guest_number")
    private Integer guestNumber;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guest> guests = new ArrayList<>();

    public enum RideType {
        ONEWAY, FIXED, TAXI
    }
}
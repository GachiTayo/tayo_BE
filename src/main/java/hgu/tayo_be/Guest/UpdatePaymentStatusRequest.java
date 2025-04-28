package hgu.tayo_be.Guest;
import lombok.Data;

@Data
public class UpdatePaymentStatusRequest {
    private Guest.PaymentStatus paymentStatus;
}
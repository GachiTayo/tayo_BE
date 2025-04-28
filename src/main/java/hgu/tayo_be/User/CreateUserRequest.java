package hgu.tayo_be.User;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String email;
    private String bankAccount;
    private String carNum;
}
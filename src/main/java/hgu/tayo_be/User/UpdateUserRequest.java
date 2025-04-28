package hgu.tayo_be.User;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String bankAccount;
    private String carNum;
}
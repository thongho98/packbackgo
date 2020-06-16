package own.thongho.web.packbackgo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
}

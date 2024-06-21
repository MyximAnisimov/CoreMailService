package mailcoremicroservice.requests;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class UserDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;
}

package mailcoremicroservice.requests;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class UserDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;
//    @NotNull
//    private String role;
}

package mailcoremicroservice.model;

import jakarta.persistence.*;
import lombok.*;
import mailcoremicroservice.roles.Roles;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor

public class User implements Serializable {
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
    @Id
    @Column(nullable = false, unique = true, name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column @NotNull
    private String email;

    @Column @NotNull
    private String password;

//    @Column @NotNull
//    private String role;



}

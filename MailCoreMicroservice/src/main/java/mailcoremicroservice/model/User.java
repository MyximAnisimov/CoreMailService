package mailcoremicroservice.model;

import jakarta.persistence.*;
import lombok.*;
import mailcoremicroservice.roles.Role;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor

public class User implements Serializable {
    public User(String email, String password, Role role_id){
        this.email = email;
        this.password = password;
        this.role = role_id;
    }
    @Id
    @Column(nullable = false, unique = true, name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column @NotNull
    private String email;

    @Column @NotNull
    private String password;

    @Getter
    @ManyToOne
    @JoinColumn(name = "role_id") // Теперь правильно связываем с "role_id"
    private Role role;



}

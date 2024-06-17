package mailcoremicroservice.roles;

import jakarta.persistence.*;
import lombok.Getter;
import mailcoremicroservice.model.User;

import java.util.List;

@Entity
public class Role {
    //    @OneToOne(mappedBy = "role_id")
    //    private User users;
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Getter
    private String role;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}

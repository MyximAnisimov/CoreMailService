package mailcoremicroservice.repositories;

import mailcoremicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User getByEmail(String email);
    User getByEmailAndPassword(String email, String password);


}

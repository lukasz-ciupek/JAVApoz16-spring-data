package pl.sda.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.sda.spring.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByFirstName(String firstName);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    List<User> findByLastName(String lastName);

    List<User> findByAddress(String address);

    @Query("select u from User u where u.username = :username and u.password = :password")
    Optional<User> searchByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

/*    @Query("select u from User u where u.lastName = :lastName")
    List<User> searchByLastName(@Param("lastName") String lastName);

    @Query(value = "SELECT * FROM USER WHERE ADDRESS = :address", nativeQuery = true)
    List<User> searchByAddress(@Param("address") String address);*/
}

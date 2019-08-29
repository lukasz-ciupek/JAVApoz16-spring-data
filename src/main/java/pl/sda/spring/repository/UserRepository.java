package pl.sda.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.sda.spring.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByFirstName(String firstName);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("select u from User u where u.lastName = :lastName")
    List<User> searchByLastName(@Param("lastName") String lastName);

    @Query(value = "SELECT * FROM USER WHERE ADDRESS = :address", nativeQuery = true)
    List<User> searchByAddress(@Param("address") String address);
}

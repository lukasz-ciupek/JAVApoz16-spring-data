package pl.sda.spring.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sda.spring.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {

    private final User user = new User("user", "user", "John", "Example", "adres");
    private final User user2 = new User("user2", "user2", "Johny", "Beer", "adres");
    private final User admin = new User("admin", "admin", "John", "Admin", "adres");

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindAllUsers() {
        //when
        List<User> actual = userRepository.findAll();

        //then
        assertThat(actual).containsExactlyInAnyOrder(user, user2, admin);
    }

    @Test
    public void shouldFindUserById() {
        //when
        Optional<User> actual = userRepository.findById("user");

        //then
        assertThat(actual.get()).isEqualTo(user);
    }

    @Test
    public void shouldFindUserByUsernameAndPassword() {
        //when
        Optional<User> actual = userRepository.searchByUsernameAndPassword("user","user");

        //then
        assertThat(actual.get()).isEqualTo(user);
    }

    @Test
    public void shouldFindUsersWithFirstNameEqualToJohn() {
        //when
        List<User> actual = userRepository.findByFirstName("John");

        //then
        assertThat(actual).containsExactlyInAnyOrder(user, admin);
    }

    @Test
    public void shouldFindJohnExampleOnly() {
        //when
        List<User> actual = userRepository.findByFirstNameAndLastName("John", "Example");

        //then
        assertThat(actual).containsExactlyInAnyOrder(user);
    }

    @Test
    public void shouldFindOnlyJohnyBeer() {
        //when
        List<User> actual = userRepository.findByLastName("Beer");

        //then
        assertThat(actual).containsExactlyInAnyOrder(user2);
    }

    @Test
    public void shouldFindAllUsersBySearchByAddress() {
        //when
        List<User> actual = userRepository.findByAddress("adres");

        //then
        assertThat(actual).containsExactlyInAnyOrder(user, user2, admin);
    }

    @Test
    @Transactional
    public void shouldAddNewUserToDatabase() {
        //given
        User newUser = new User("newUser", "newUser", "name", "lastName", "adres");

        //when
        userRepository.save(newUser);

        //then
        assertThat(userRepository.findAll()).contains(newUser);
    }

    @Test
    @Transactional
    public void shouldUpdateUser() {
        //given
        User updatedUser = new User("user", "password", "John", "Example", "adres");

        //when
        userRepository.save(updatedUser);

        //then
        assertThat(userRepository.count()).isEqualTo(3);
        assertThat(userRepository.findById("user").get()).isEqualTo(updatedUser);
    }
}

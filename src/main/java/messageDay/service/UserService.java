package messageDay.service;

import messageDay.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity findUserByLogin(String login);

    List<UserEntity> findAllUserByFIOEquals(String name, String surname);

    void saveUser(UserEntity user);

    UserEntity getAuthenticationUser();

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findAllByActive(Pageable pageable, int active);

    Page<UserEntity> findAllByFioIgnoreCase(Pageable pageable, String name);

    void deleteById(int id);

    UserEntity findById(int id);

    void saveSingUpUser(UserEntity user);

    Optional<UserEntity> findByActiveAndRoleId(int i, int i1);
}

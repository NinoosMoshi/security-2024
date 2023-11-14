package com.ninos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ninos.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);


    @Query("select u.active from User u where u.username=?1 OR u.email=?2")
    int getActiveByUsernameOrEmail(String username, String email);


    @Query("select u.password from User u where u.username=:username OR u.email=:email")
    String getPasswordByUsernameOrEmail(@Param("username") String username, @Param("email") String email);


}

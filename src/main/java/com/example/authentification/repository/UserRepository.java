package com.example.authentification.repository;

import com.example.authentification.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    @Query("select username from UserEntity")
    List<String> findAllUsername();

    @Modifying
    @Query("update UserEntity u set u.password = ?1 where u.id = ?2")
    void changePassword(String newPassword, Long userId);

    @Modifying
    @Query("delete from UserEntity u WHERE u.id = ?1")
    void deleteUser(Long id);
}

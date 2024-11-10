package com.userservice.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.userservice.user.entity.User;

import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM user_table u " +
            "WHERE (:first_name IS NULL OR b.first_name = :firstname) " +
            "AND (:last_name IS NULL OR b.last_name = :lastname)")
     List<User> searchUsers(@Param("first_name") String firstname,
                             @Param("last_name") String lastname);

	User findByUserId(Long userId);
}

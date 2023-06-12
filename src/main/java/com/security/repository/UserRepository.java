package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);

	@Query("SELECT e FROM User AS e WHERE e.username = :id and e.password= :pass")
	public User signInUser(@Param("id") String id, @Param("pass") String pass);
	
//	@Query("Update User u set u.email = :email where u.lastname = ?2")
//	public User modifyByUsername(@Param("username") String username, @Param("email") String email, @Param("status") String status, @Param("userIdentity") String userIdentity);
}

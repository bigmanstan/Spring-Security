package com.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.security.models.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

	@Query("SELECT e FROM Activity AS e WHERE e.roleName = :roleName")
	public List<Activity> findByRoleName(String roleName);
}

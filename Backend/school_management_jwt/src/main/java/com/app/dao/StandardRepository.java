package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.pojos.Role;
import com.app.pojos.Standard;
import com.app.pojos.Standards;
import com.app.pojos.UserRoles;

@Repository
public interface StandardRepository extends JpaRepository<Standard, Integer> {
	Optional<Standard> findByStandard(Standards standard);
}

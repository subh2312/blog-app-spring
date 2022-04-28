package com.subhankar.blogapp.config.security.repository;

import com.subhankar.blogapp.config.security.models.ERole;
import com.subhankar.blogapp.config.security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

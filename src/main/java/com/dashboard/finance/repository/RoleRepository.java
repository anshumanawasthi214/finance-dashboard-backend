package com.dashboard.finance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dashboard.finance.model.Role;
import com.dashboard.finance.model.enums.RoleName;

public interface RoleRepository
extends JpaRepository<Role, Long> {

Optional<Role> findByName(RoleName name);

}
package com.checkbuy.project.domain.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checkbuy.project.domain.model.user.Role;

@Repository
public interface  RoleRepository extends JpaRepository<Role, Long>{
    
}

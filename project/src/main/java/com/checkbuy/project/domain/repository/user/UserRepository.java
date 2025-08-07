package com.checkbuy.project.domain.repository.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checkbuy.project.domain.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
 
}

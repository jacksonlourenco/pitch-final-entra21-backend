package com.checkbuy.project.domain.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.checkbuy.project.domain.model.user.User;

public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByLogin(String login);
}

package com.checkbuy.project.domain.dto;

import com.checkbuy.project.domain.model.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {

}

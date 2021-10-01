package com.tracom.cohort5project.service;


import com.tracom.cohort5project.model.User;
import com.tracom.cohort5project.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}

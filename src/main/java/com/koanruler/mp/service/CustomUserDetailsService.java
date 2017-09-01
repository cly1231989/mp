package com.koanruler.mp.service;

import com.koanruler.mp.entity.CustomUserDetails;
import com.koanruler.mp.entity.User;
import com.koanruler.mp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by hose on 2017/9/1.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByName(userName);
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("UserName not found"));
        return optionalUser.map(CustomUserDetails::new).get();
    }
}

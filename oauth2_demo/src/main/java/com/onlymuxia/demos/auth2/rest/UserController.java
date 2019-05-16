package com.onlymuxia.demos.auth2.rest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onlymuxia.demos.auth2.entrys.User;
import com.onlymuxia.demos.auth2.repository.UserRepository;

/**
 * Resource Owner
 * @author huangzhigao
 * @date      2019-05-16
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository applicationUserRepository;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }
    
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
      return "hello auth2";
    }
}
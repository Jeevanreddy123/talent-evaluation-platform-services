
package com.talentEvaluation.service;

import com.talentEvaluation.dto.UserDto;
import com.talentEvaluation.dto.UserResponse;
import com.talentEvaluation.exception.UserAlreadyExistsException;
import com.talentEvaluation.entity.User;
import com.talentEvaluation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username '" + user.getUsername() + "' already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username);
        UserResponse userResponse = new UserResponse();
        userResponse.setAssociateId(user.getAssociateId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setRole(user.getRole());
        userResponse.setTechStack(user.getTechStack());
        userResponse.setProjectRole(user.getProjectRole());
        userResponse.setUpdatedBy(user.getUpdatedBy());
        return userResponse;
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getAssociateId()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        user.setTechStack(userDto.getTechStack());
        user.setProjectRole(userDto.getProjectRole());
        user.setUpdatedBy(userDto.getUpdatedBy());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long associateId) {
        userRepository.deleteById(associateId);
    }

    @Override
    public List<User> getAllEvaluatorsGroupByStatus() {
        return userRepository.findAll().stream().filter(user -> "EVALUATOR".equals(user.getRole())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // The User entity already implements UserDetails, so we can return it directly.
        // The getAuthorities() method in the User entity will provide the roles.
        return user;
    }
}

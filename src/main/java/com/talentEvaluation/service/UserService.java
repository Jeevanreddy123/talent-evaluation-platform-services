
package com.talentEvaluation.service;

import com.talentEvaluation.dto.UserDto;
import com.talentEvaluation.dto.UserResponse;
import com.talentEvaluation.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    UserResponse getUser(String username);
    User updateUser(UserDto userDto);
    void deleteUser(Long associateId);
    List<User> getAllEvaluatorsGroupByStatus();
}

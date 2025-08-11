
package com.talentEvaluation.service;

import com.talentEvaluation.dto.UserUpdateDto;
import com.talentEvaluation.dto.UserResponse;
import com.talentEvaluation.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    UserResponse getUser(String username);
    User updateUser(UserUpdateDto userUpdateDto);
    void deleteUser(Long associateId);
    List<User> getAllEvaluatorsGroupByStatus();
}

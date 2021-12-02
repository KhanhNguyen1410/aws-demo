package com.example.aws.service.impl;

import com.example.aws.entity.Role;
import com.example.aws.entity.User;
import com.example.aws.enums.SystemEnum;
import com.example.aws.exception.BusinessException;
import com.example.aws.model.request.LoginRequest;
import com.example.aws.repository.RoleRepository;
import com.example.aws.repository.UserRepository;
import com.example.aws.service.UserService;
import com.example.aws.constants.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository repository;

  @Override
  public User findById(long id) {
    return userRepository.findById(id).get();
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public User login(LoginRequest loginRequest) throws Exception {
    User user = userRepository.findUserByUserName(loginRequest.getUsername());
    if (user == null) {
      throw new BusinessException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
    }
    Role role = repository.getById(user.getRoleId());
    if (!isHasRole(new HashSet<>(Collections.singletonList(role)), SystemEnum.RoleType.USER.value())) {
      throw new BusinessException(ResponseMessageConstants.LOGIN_ACCESS_DENIED);
    }
    return null;
  }

  public static boolean isHasRole(Set<Role> roles, String roleName) {
    for (Role role : roles)
      if (role.getName().equals(roleName))
        return true;
    return false;
  }
}

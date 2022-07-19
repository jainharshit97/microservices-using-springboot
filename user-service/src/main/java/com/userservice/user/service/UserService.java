package com.userservice.user.service;

import com.userservice.user.VO.Department;
import com.userservice.user.VO.ResponseTemplateVO;
import com.userservice.user.entity.User;
import com.userservice.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RestTemplate restTemplate;

  public User saveUser(User user) {
    log.info("Inside saveUser method of UserService.");
    return userRepository.save(user);
  }

  public ResponseTemplateVO getUserWithDepartment(Long userId) {
    log.info("Inside saveUser method of UserService.");
    ResponseTemplateVO vo = new ResponseTemplateVO();
    User user = userRepository.findByUserId(userId);

    Department department = restTemplate.getForObject(
        "http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId(),
        Department.class);
    vo.setUser(user);
    vo.setDepartment(department);

    return vo;
  }
}

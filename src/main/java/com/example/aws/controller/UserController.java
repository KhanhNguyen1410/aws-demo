package com.example.aws.controller;

import com.example.aws.constants.PagingConstants;
import com.example.aws.entity.User;
import com.example.aws.enums.Enums;
import com.example.aws.model.Pagination;
import com.example.aws.model.ResponseData;
import com.example.aws.model.ResponseDataPagination;
import com.example.aws.service.UserService;
import com.example.aws.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {

  @Autowired
  private UserService userService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/test")
  public String test() {
    User user = new User("khanh", "khanh");
    User userSaved = userService.save(user);
    return userSaved.toString();
  }

  @GetMapping("/get-all-accounts")
  public ResponseData getAllAccount(
    @RequestParam(value = "page", required = false, defaultValue = PagingConstants.PAGING_STRING_PAGE_DEFAULT) int page,
    @RequestParam(value = "size", required = false, defaultValue = PagingConstants.PAGING_STRING_SIZE_DEFAULT) int size) {
    ResponseDataPagination responseData = new ResponseDataPagination();
    Pagination pagination = new Pagination();
    try {
      int pageReq = page >= 1 ? page - 1 : page;
      Pageable pageable = PageRequest.of(pageReq, size);
      Page<User> users = userService.findAll(pageable);
      responseData.setData(users.getContent());
      pagination.setCurrentPage(page);
      pagination.setPageSize(size);
      pagination.setTotalPage(users.getTotalPages());
      pagination.setTotalRecords(users.getTotalElements());
      responseData.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
      responseData.setPagination(pagination);
    } catch (Exception e) {
      String msg = LogUtil.printLogStackTrace(e);
      logger.error(msg);
      return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
    }
    return  responseData;
  }
}

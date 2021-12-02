package com.example.aws.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GeneralService<T> {
  T findById(long id);

  List<T> findAll();

  Page<T> findAll(Pageable pageable);

  T save(T t);

}

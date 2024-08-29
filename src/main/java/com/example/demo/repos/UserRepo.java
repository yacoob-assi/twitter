package com.example.demo.repos;

import java.util.Optional;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;


@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    public User findByEmail(String email);
    List<User> findByUserIdNotIn(List<Integer> followers);
    List<User> findByFullNameContains(String s);
}

package com.example.demo.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Post;
import com.example.demo.models.User;

import java.util.List;
@Repository
public interface PostRepo extends CrudRepository<Post, Integer> {
    public List<Post> findByUserAndParentIsNull(User user);
    public List<Post>findByParentIsNull();
    public List<Post>findByTextContains(String s);
} 

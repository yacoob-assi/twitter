package com.example.demo.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Post;

@Repository
public interface PostRepo extends CrudRepository<Post, Integer> {

}

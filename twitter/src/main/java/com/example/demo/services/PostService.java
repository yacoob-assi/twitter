package com.example.demo.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repos.PostRepo;
import com.example.demo.repos.UserRepo;

@Service
public class PostService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;

    public Post save(Post post) {
        postRepo.save(post);
        return post;
    }

    public Post getPost(int postId) {
        return postRepo.findById(postId).get();
    }

    public List<User> getPostLikes(int id) {
        return postRepo.findById(id).get().getUsers();
    }

    public List<Post> getAllPosts(int userId) {
        return userRepo.findById(userId).get().getPosts();

    }

}
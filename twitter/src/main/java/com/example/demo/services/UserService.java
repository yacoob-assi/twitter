package com.example.demo.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

// import com.example.demo.Mapper.UserFollowersMapper;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repos.UserRepo;

@Service
public class UserService {

    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUser(int id) {

        return userRepo.findById(id).get();
    }

    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<Post> getUserLikes(int id) {

        return userRepo.findById(id).get().getLikedPosts();
    }

    public List<User> getFollowers(Integer id) {
        User user = userRepo.findById(id).get();
        return user.getFollowers();
    }

    public List<User> getFollowings(Integer id) {
        User user = userRepo.findById(id).get();
        return user.getFollowings();
    }

    public List<Post> getUserposts(Integer id) {
        User user = userRepo.findById(id).get();
        return user.getPosts();
    }

}
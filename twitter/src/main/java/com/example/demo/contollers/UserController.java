package com.example.demo.contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repos.PostRepo;

import com.example.demo.response.UserRespone.UserResponse;
import com.example.demo.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    UserMapper userMapper;

    @Autowired
    PostRepo postRepo;

    public UserController() {
        userMapper = new UserMapper();
    }

    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable int id) {
        User user = userService.getUser(id);
        return userMapper.toUserResponse(user);

    }

    @GetMapping("/users/{id}/followers")
    public List<User> getFollowers(@PathVariable Integer id) {
        return userService.getFollowers(id);
    }

    @GetMapping("/users/{id}/followings")
    public List<User> getFollowings(@PathVariable Integer id) {
        return userService.getFollowings(id);
    }

    @GetMapping("/users/{id}/likes")
    public List<Post> getUserLikes(@PathVariable int id) {
        return userService.getUserLikes(id);
    }

    @GetMapping("/users/{id}/allposts")
    public Iterable<Post> getAllUserposts(@PathVariable int id) {

        return userService.getUserposts(id);
    }

}
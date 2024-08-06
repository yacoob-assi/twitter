package com.example.demo.contollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Mapper.PostMapper;
// import com.example.demo.Mapper.PostMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.response.PostResponse;
import com.example.demo.services.PostService;
import com.example.demo.services.UserService;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    PostMapper postMapper;

    public PostController() {
        postMapper = new PostMapper();
    }

    @PostMapping("/newpost")
    public PostResponse addNewPost(@ModelAttribute Post post) {
        postService.save(post);
        return postMapper.toPostResponse(post);

    }

    @PutMapping("/post/addLike")
    public PostResponse addLike(@ModelAttribute Post post, @ModelAttribute User user) {
        post.getUsers().add(user);
        return postMapper.toPostResponse(post);
    }

    @PutMapping("/likeplus")
    public int likeplus(@ModelAttribute User user, @ModelAttribute Post post) {

        post.getUsers().add(user);
        return post.getUsers().size();
    }

    @GetMapping("post/{postId}")
    public PostResponse getPosts(@PathVariable int postId) {
        Post post = postService.getPost(postId);
        return postMapper.toPostResponse(post);
    }

    @GetMapping("post/{postId}/likes")
    public List<User> getPostsLikes(@PathVariable int postId) {
        return postService.getPostLikes(postId);
    }

}
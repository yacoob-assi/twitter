// package com.example.demo.contollers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.demo.Mapper.PostMapper;
// import com.example.demo.Mapper.UserMapper;
// import com.example.demo.models.Post;
// import com.example.demo.models.User;
// import com.example.demo.services.PostService;
// import com.example.demo.services.UserService;

// @RestController
// public class LikesController {

// @Autowired
// UserService userService;

// @Autowired
// PostService postService;

// @Autowired
// LikesController likesController;

// @Autowired
// UserMapper userMapper;

// @Autowired
// PostMapper postMapper;

// @PostMapping("/addlike")
// public void addPost(@RequestParam int userId, @RequestParam int postId) {

// User user = userMapper.toUser(userService.getUser(userId));
// Post post = postMapper.toPost(postService.getPost(postId));

// }

// }

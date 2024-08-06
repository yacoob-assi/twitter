// package com.example.demo.contollers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;

// import com.example.demo.services.PostService;
// // import com.example.demo.services.PostService;
// import com.example.demo.services.UserService;

// @Controller
// public class PagesController {

// @Autowired
// private PostService postService;

// @Autowired
// private UserService userService;

// @GetMapping("/")
// public String getPosts(Model model) {
// model.addAttribute("posts", postService.getAllPosts(1));
// return "index";
// }
// }

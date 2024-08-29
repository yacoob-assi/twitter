 package com.example.demo.contollers;

 import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Mapper.PostMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.response.PostResponse;
import com.example.demo.response.UserResponse;
import com.example.demo.services.NotificationService;
import com.example.demo.services.PostService;
  import com.example.demo.services.PostService;
 import com.example.demo.services.UserService;

import jakarta.websocket.server.PathParam;
import java.util.List;
import java.util.ArrayList;
 @Controller
 public class PagesController {

 @Autowired
 private PostService postService;

 @Autowired
 private UserService userService;
 
 
 @Autowired
 private NotificationService noteService;
 PostMapper postMapper;
 UserMapper userMapper;

 public PagesController() {
     postMapper = new PostMapper();
     userMapper=new UserMapper();
 }

 @GetMapping("/")
 public String getPosts( Model model, Principal principal) {
     String email = principal.getName();
     User user = userService.getUser(email);
 
     model.addAttribute("posts", postMapper.toPostResponse(postService.allPostsWithoutComments()));
         
     model.addAttribute("user", user);
 
     List<UserResponse> whoFollow = userMapper.toUserResponse(userService.findUsersToFollow(user.getUserId()));
     model.addAttribute("toFollow", whoFollow);
     model.addAttribute("trends", postService.getTrends());
 
     return "index";
 }
 
 @GetMapping("/login")
 public String signin() {

  return "login";
  }


  @GetMapping("/profile")
  public String profile(Model model,Principal principal) {
    String email=principal.getName();
    com.example.demo.models.User user= userService.getUser(email);
    model.addAttribute("user",user);
    model.addAttribute("posts",postMapper.toPostResponse( user.getPosts()));
    List<UserResponse>whofollow= userMapper.toUserResponse(userService.findUsersToFollow(user.getUserId()));
    model.addAttribute("toFollow", whofollow);
    model.addAttribute("signedUser", user);

    List<PostResponse>likedPosts=postMapper.toPostResponse(user.getLikedPosts());
    model.addAttribute("likedPosts", likedPosts);
   return "profile";
   }
  
   @GetMapping("/userprofile/{id}")
   public String userprofile(@PathVariable int id,Model model,Principal principal) {

    com.example.demo.models.User user = userService.getUser(id);
     model.addAttribute("user",user);
    model.addAttribute("signedUser", userService.getUser(principal.getName()));
     model.addAttribute("posts",postMapper.toPostResponse( user.getPosts()));
 
    return "profile";
    }
   @GetMapping("/notification")
   public String note(Model model,Principal principal) {
    String email=principal.getName();
    User user=userService.getUser(email);
    model.addAttribute("notes", noteService.getNotesToShow(user.getUserId()));
    model.addAttribute("user", user);
    return "notification";
    }

    @GetMapping("/comments/{postId}")
    public String comments(Model model,@PathVariable int postId,Principal principal){
      
      Post post= postService.getPost(postId);
      model.addAttribute("parentPost",postMapper.toPostResponse(post));
      // Post comment=new Post();
      // comment.setText("text");
      // comment.setParent(post);
      // comment.setUser(post.getUser());
      // comment.setDate(post.getDate());
      // comment.setRepostNum(post.getRepostNum());
      
      
      // comments.add(comment);
      // comment.setParent(post);
      // postService.save(comment);


      List<PostResponse>postComments=postMapper.toPostResponse(post.getComments());
      model.addAttribute("comments",postComments);
           
      User user=userService.getUser(principal.getName());
      model.addAttribute("user", user);
      return "comments";
    }
    
 }

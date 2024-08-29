package com.example.demo.contollers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Mapper.PostMapper;
 import com.example.demo.Mapper.PostMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.models.Note;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.response.PostResponse;
import com.example.demo.services.NotificationService;
import com.example.demo.services.PostService;
import com.example.demo.services.UserService;

import io.jsonwebtoken.io.IOException;

import java.sql.Date;
import java.security.Principal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService noteService;
    PostMapper postMapper;


    public static String uploadDirectory=System.getProperty("user.dir")+"/src/main/java/com/example/demo/images";
    public PostController() {
        postMapper = new PostMapper();
    }

    @PostMapping("/newpost")
    public PostResponse addNewPost(@RequestParam("text")String text,Principal principal,@RequestParam(value="image",required = false) MultipartFile file) throws java.io.IOException{

        String fileName =null;
        if(file!=null && !file.isEmpty()){
    	 fileName = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
        Files.write(fileNameAndPath, file.getBytes());
        }

        Post post=new Post();
        post.setText(text);
        post.setUser(userService.getUser(principal.getName()));
        Date sqlDate = new Date(new java.util.Date().getTime());
        post.setDate(sqlDate); // Set the current date
        post.setImage(fileName);
        postService.save(post);
        return postMapper.toPostResponse(post);

    }
    @PutMapping("/post/addLike")
    public PostResponse addLike(@RequestParam("postId") int postId, Principal principal) {
        Post post = postService.getPost(postId);
        User user = userService.getUser(principal.getName());
        
        if (!user.getLikedPosts().contains(post)) {
            user.getLikedPosts().add(post);
            userService.save(user);
        }
        post.incStatisics();
        postService.save(post);
        return postMapper.toPostResponse(post);
    } 

    @PutMapping("/post/removelike")
    public PostResponse removeLike(@RequestParam("postId") int postId, Principal principal) {
        Post post = postService.getPost(postId);
        User user = userService.getUser(principal.getName());

        
        if (user.getLikedPosts().contains(post)) {
            user.getLikedPosts().remove(post);
            userService.save(user);
        }
        
        post.decStatisics();
        return postMapper.toPostResponse(post);
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
    
    @PostMapping("/addPostNote")
    public void addPostNote(@RequestParam("postId")int id,Principal principal){
     
        Post post=postService.getPost(id);
        User user=userService.getUser(principal.getName());
        Date sqlDate = new Date(new java.util.Date().getTime());
        List<User> followers=user.getFollowers();
       
        for(User follower:followers){
            Note note = new Note();
            note.setDate(sqlDate);
            note.setNotesTo(follower);
            note.setPublisher(user);
            note.setText(user.getFullName()+" add new post");
            note.setPost(post);
            noteService.SaveNote(note);
        }

    }
    
     @PostMapping("/repost")
     public void repost(@RequestParam("postId")int postId,Principal principal){
      User user=userService.getUser(principal.getName());
      Post post=postService.getPost(postId);
      Post postToAdd=new Post();
      postToAdd.setDate(new Date(new java.util.Date().getTime()));
      postToAdd.setRepostNum(0);
      postToAdd.setUser(user);
      postToAdd.setText(post.getText());
      postToAdd.setImage(post.getImage());
      user.getPosts().add(postToAdd);
     
      post.setRepostNum(post.getRepostNum()+1);
      post.getUsersRepost().add(user);
      user.getRepost().add(post);

      post.incStatisics();
      postService.save(post); 
      userService.save(user);
     }

     @PostMapping("/addNewComment")
     public void addComment(@RequestParam("postId") int postId,
     @RequestParam(value = "image", required = false) MultipartFile file,
     @RequestParam("text") String text,
     Principal principal) throws IOException, java.io.IOException {
        
     
    User user = userService.getUser(principal.getName());
    Post parentPost = postService.getPost(postId);
    Date sqlDate = new Date(new java.util.Date().getTime());
    
    String fileName = null;
    
    if (file != null && !file.isEmpty()) {
        fileName = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
        Files.write(fileNameAndPath, file.getBytes());
    }
    
    Post comment = new Post();
    comment.setText(text);
    comment.setDate(sqlDate);
    comment.setParent(parentPost);
    comment.setRepostNum(0);
    comment.setUser(user);
    comment.setImage(fileName); // Set image to null if no file was uploaded
    parentPost.getComments().add(comment);
    parentPost.incStatisics();
    postService.save(comment);
    postService.save(parentPost);
}
    @GetMapping("getFollowersPosts")
    public List<PostResponse> getFollowersPosts(Principal principal){
       
        User user=userService.getUser(principal.getName());
        List<Post> totalPost = new ArrayList<>();
         List<User> followers = user.getFollowers();
         for (User follower : followers) {
             List<Post> posts = postService.allUserPostsWithoutComment(follower.getUserId());
             if (posts != null) {
                 totalPost.addAll(posts);
             }
         }

         return postMapper.toPostResponse(totalPost); 
    }



@GetMapping("/getAllPosts")
public List<PostResponse> getAllPosts(){
    return postMapper.toPostResponse(postService.allPostsWithoutComments());
}

@GetMapping("/getHashPosts")
public List<PostResponse> getHashPosts(@RequestParam("hashText")String hashText){
   return postMapper.toPostResponse( postService.getHashPosts(hashText));
}

@GetMapping("/post/image/{id}")
public ResponseEntity<Resource>  getPostImage(@PathVariable("id") int id) throws java.io.IOException{
    Post post=postService.getPost(id);
    Path imagePath=Paths.get(uploadDirectory,post.getImage());
    Resource resource=new FileSystemResource(imagePath.toFile());
    String contentType=Files.probeContentType(imagePath);
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
}
}


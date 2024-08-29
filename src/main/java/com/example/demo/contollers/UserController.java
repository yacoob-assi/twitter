package com.example.demo.contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repos.PostRepo;
import com.example.demo.response.UserResponse;
import com.example.demo.services.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
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
    

    @GetMapping("/getUsersToFollow/{id}")
    public List<UserResponse> getToFollow(@PathVariable int id) {
        return userMapper.toUserResponse(userService.findUsersToFollow(id));
    }
    

    @PostMapping("/removeFollower")
    public void removeFollower(@RequestParam("user") int userId,@RequestParam("follower") int followeId){
        User user=userService.getUser(userId);
        User follower=userService.getUser(followeId);
        user.getFollowers().remove(follower);

        follower.getFollowings().remove(user);
        userService.save(follower);
        userService.save(user);

    }

    @PostMapping("/addFollower")
    public void addFollower(@RequestParam("user") int userId,@RequestParam("follower") int followeId){
        User user=userService.getUser(userId);
        User follower=userService.getUser(followeId);
        user.getFollowers().add(follower);

        follower.getFollowings().add(user);
        userService.save(follower);
        userService.save(user);

    }

    @GetMapping("getNotesNumber")
    public int getUserNotesNumber(Principal Principal){

        User user=userService.getUser(Principal.getName());
        int notesNum=user.getNotesToGet().size()-user.getNotesSaw();
        if(notesNum<0)notesNum=0;
        return notesNum;
    }

    @PostMapping("/notesUserSaw")
    public void changeNotesSaw(Principal principal){
      User user=userService.getUser(principal.getName());
      user.setNotesSaw( user.getNotesToGet().size());
      userService.save(user);
     }



@GetMapping("/search")
public List<UserResponse> searchUsers(@RequestParam("query")String query){
    return userMapper.toUserResponse(userService.getUsersSearch(query));
}

public String uploadDirectory=System.getProperty("user.dir")+"/src/main/java/com/example/demo/images";

@PostMapping("/changeUserImage")
public void saveImage( @RequestParam("image") MultipartFile file,Principal principal) throws IOException{
    User user=userService.getUser(principal.getName());
    String fileName = file.getOriginalFilename();
    Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
    Files.write(fileNameAndPath, file.getBytes());

    user.setImage(fileName);
    userService.save(user);
}

@GetMapping("/user/image/{id}")
public ResponseEntity<Resource>  getPostImage(@PathVariable int id ) throws java.io.IOException{
    User user=userService.getUser(id);
    Path imagePath=Paths.get(uploadDirectory,user.getImage());
    Resource resource=new FileSystemResource(imagePath.toFile());
    String contentType=Files.probeContentType(imagePath);
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
}

}
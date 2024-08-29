package com.example.demo.contollers;

import java.util.Optional;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Date;
import com.example.demo.models.Note;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repos.NoteRepo;
import com.example.demo.services.NotificationService;
import com.example.demo.services.PostService;
import com.example.demo.services.UserService;

@Controller
public class NotificationController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService noteService;

    @Autowired
    private PostService postService;


@PostMapping("/notes/removeNote")
public void removeNote(@RequestParam("noteId") int noteId) {
   
   noteService.deleteNoteById(noteId);
}
@PostMapping("/notes/addNote")
public void addNoteWhenLike(@RequestParam("postId")int postId,Principal principal){
    
    User noteMaker=userService.getUser(principal.getName());
    Post post=postService.getPost(postId);
    User noteTo=post.getUser();
    if(noteMaker.equals(noteTo))return;
    
    Date sqlDate = new Date(new java.util.Date().getTime());
    Note note= new Note();
    note.setDate(sqlDate);
    note.setNotesTo(noteTo);
    note.setPublisher(noteMaker);
    note.setPost(post);
    note.setText(noteMaker.getFullName()+" Like your post");
    noteService.SaveNote(note);

}
   
@PostMapping("/notes/removeNoteByLike")
public void removeNoteByLike(@RequestParam("postId")int postId,Principal principal){

Post post=postService.getPost(postId);
User user=userService.getUser(principal.getName());
post.getNotes().remove(noteService.getLikeNote(post, user));
postService.save(post);
}

@PostMapping("/addFollowNote")
public void addFollowNote(@RequestParam("followerId")int followerId,Principal principal){
User noteMaker=userService.getUser(principal.getName());
User NoteTo=userService.getUser(followerId);
Note note=new Note();
Date sqlDate = new Date(new java.util.Date().getTime());

note.setDate(sqlDate);
note.setPublisher(noteMaker);
note.setNotesTo(NoteTo);
note.setText(noteMaker.getFullName()+" now follow you");
noteService.SaveNote(note);
}

@PostMapping("/removeFollowNote")
public void removeFollowNote(int toId,Principal principal){
    User noteMaker=userService.getUser(principal.getName());
    User noteTo=userService.getUser(toId);
    noteTo.getNotesToGet().remove(noteService.findFollowNote(noteTo, noteMaker,"follow"));
    userService.save(noteTo);
} 

}

package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Note;
import com.example.demo.models.Post;
import com.example.demo.repos.NoteRepo;
import com.example.demo.repos.UserRepo;
import com.example.demo.models.User;

import java.util.List;
@Service
public class NotificationService {

    @Autowired
    private NoteRepo noteRepo;

    @Autowired 
    private UserRepo userRepo;



    public List<Note> getNotesToShow(int id){
       User user= userRepo.findById(id).get();
       return user.getNotesToGet();
    }

    public Note getNote(int id){
        return noteRepo.findById(id).get();
    }

    public Note SaveNote(Note note){
       return noteRepo.save(note);
    }

    public void deleteNoteById(int id) {
        noteRepo.deleteById(id);
    }

    public Note getLikeNote(Post post,User publisher){
      return noteRepo.findByPostAndPublisher(post, publisher);
    }
    public Note findFollowNote(User to,User publisher,String s){
        return noteRepo.findByNotesToAndPublisherAndTextContaining(to, publisher, s);
    }
    
}

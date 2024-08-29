package com.example.demo.repos;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Note;
import com.example.demo.models.Post;
import com.example.demo.models.User;

public interface NoteRepo extends CrudRepository<Note,Integer> {
    
    public Note findByPostAndPublisher(Post post,User publisher);
    public Note findByNotesToAndPublisherAndTextContaining(User to,User publisher,String string);

}

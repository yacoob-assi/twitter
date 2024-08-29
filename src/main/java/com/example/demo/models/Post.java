package com.example.demo.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "postId")

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private int postId;

    @Column(name = "date")
    private Date date;

    @Column(name = "repostNum")
    private int repostNum;

    @Column(name = "text")
    private String text;
    
    @Column(name = "tag")
    private String tag;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @JsonIgnore
    @ManyToMany(mappedBy = "likedPosts")
    private List<User> users=new ArrayList<>();
 
    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "repost")
    private List<User> usersRepost=new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "parent_post")
    @JsonIgnore
    private Post parent;

    @OneToMany(mappedBy = "parent")
    private List<Post> comments = new ArrayList<>();

    private int statisics;

  
    private String image;

    public void incStatisics(){
     this.statisics+=1;
    }

    public void decStatisics(){
        this.statisics-=1;
       }


   }




package com.example.demo.services;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

// import com.example.demo.Mapper.UserFollowersMapper;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repos.UserRepo;
import com.example.demo.response.UserResponse;

@Service
public class UserService {

    private UserRepo userRepo;	

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUser(int id) {

        return userRepo.findById(id).get();
    }

    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<Post> getUserLikes(int id) {

        return userRepo.findById(id).get().getLikedPosts();
    }

    public List<User> getFollowers(Integer id) {
        User user = userRepo.findById(id).get();
        return user.getFollowers();
    }

    public List<User> getFollowings(Integer id) {
        User user = userRepo.findById(id).get();
        return user.getFollowings();
    }

    public List<Post> getUserposts(Integer id) {
        User user = userRepo.findById(id).get();
        return user.getPosts();
    }
    
    public User save(User user) {
    return userRepo.save(user);
    }

    public User getUser(String userName){
        return userRepo.findByEmail(userName);
    }

    public List<User> findUsersToFollow(int id){
        User user=userRepo.findById(id).get();
        List<Integer>followersIds=new ArrayList<Integer>();

        for(User follower:user.getFollowers()){
            followersIds.add(follower.getUserId());

        }
        List<User>answer=userRepo.findByUserIdNotIn(followersIds);
        if(answer.isEmpty()){
            for(User u:userRepo.findAll())
            if(u.getUserId()!=user.getUserId())answer.add(u);
        }
        else answer.remove(user);
        return answer;
    }

    public List<User> getUsersSearch(String s){
        return userRepo.findByFullNameContains(s);
    }
    }    


			
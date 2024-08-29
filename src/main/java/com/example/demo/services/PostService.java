package com.example.demo.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Comparator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Mapper.PostMapper;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repos.PostRepo;
import com.example.demo.repos.UserRepo;
import com.example.demo.response.TrendResponse;
import com.example.demo.response.trendsResponse;

import java.util.Map.Entry;
@Service
public class PostService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;

    
 
   
    public Post save(Post post) {
        postRepo.save(post);
        return post;
    }

    public Post getPost(int postId) {
        return postRepo.findById(postId).get();
    }

    public List<User> getPostLikes(int id) {
        return postRepo.findById(id).get().getUsers();
    }

    public List<Post> getAllPosts(int userId) {
        return userRepo.findById(userId).get().getPosts();

    }

public Iterable<Post> getAllPosts() {
    List<Post> posts = (List<Post>) postRepo.findAll();

    Collections.shuffle(posts);

    return posts;
}

public List<Post> postsOfUserWithoutComments(User user){
    return postRepo.findByUserAndParentIsNull(user);
}

public List<Post> allPostsWithoutComments(){
    return postRepo.findByParentIsNull();
}

public List<Post> allUserPostsWithoutComment(int id){

    User user=userRepo.findById(id).get();
    return postRepo.findByUserAndParentIsNull(user);
}
public List<TrendResponse> getTrends() {
    // Fetch posts that contain hashtags
    List<Post> postHasHash = postRepo.findByTextContains("#");

    // Initialize a map to count occurrences of each hashtag
    HashMap<String, Integer> hashtagCount = new HashMap<>();

    // Iterate over posts to populate hashtag counts
    for (Post post : postHasHash) {
        String[] arrayText = post.getText().split(" ");
        for (String word : arrayText) {
            if (word.startsWith("#")) {  // Use startsWith("#") to identify hashtags
                hashtagCount.put(word, hashtagCount.getOrDefault(word, 0) + 1);
            }
        }
    }

    // Sort the hashtags by count in descending order
    Map<String, Integer> sortedHashtagCount = sortByValue(hashtagCount);

    
    List<TrendResponse>response =new ArrayList();
    
    int count=0;
    for (String key : sortedHashtagCount.keySet()) {
        Integer value = sortedHashtagCount.get(key);
        
        response.add(new TrendResponse(key, value));
        if(count==10)break;
    }
    return response;
}

   public static Map<String, Integer> sortByValue(Map<String, Integer> map) {
    List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

    list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    Map<String, Integer> sortedMap = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> entry : list) {
        sortedMap.put(entry.getKey(), entry.getValue());
    }

    return sortedMap;
}

public List<Post>getHashPosts(String hash){
    return postRepo.findByTextContains(hash);
}
}
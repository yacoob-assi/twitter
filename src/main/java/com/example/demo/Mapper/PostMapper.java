package com.example.demo.Mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.Post;
import com.example.demo.response.PostResponse;
import com.example.demo.response.UserResponse;

public class PostMapper {

    UserMapper userMapper;

    public PostMapper() {
        userMapper = new UserMapper();
    }

    public PostResponse toPostResponse(Post post) {

        UserResponse userResponse = userMapper.toUserResponse(post.getUser());
        
        return new PostResponse(post.getPostId(), post.getDate(), post.getRepostNum(), post.getText(), userResponse,
        		post.getTag(),post.getUsers().size(),post.getUsers(),post.getUsersRepost(),post.getParent(),post.getComments(),post.getStatisics(),post.getImage()  );
    }
    
    public List<PostResponse>  toPostResponse(Iterable<Post> posts) {

    	List<PostResponse> responses=new ArrayList();
    	for(Post post:posts)responses.add(toPostResponse(post));
        
        
       return responses;
    }

}

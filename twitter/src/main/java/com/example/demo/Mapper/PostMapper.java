package com.example.demo.Mapper;

import com.example.demo.models.Post;
import com.example.demo.response.PostResponse;
import com.example.demo.response.UserRespone.UserResponse;

public class PostMapper {

    UserMapper userMapper;

    public PostMapper() {
        userMapper = new UserMapper();
    }

    public PostResponse toPostResponse(Post post) {

        UserResponse userResponse = userMapper.toUserResponse(post.getUser());
        return new PostResponse(post.getPostId(), post.getDate(), post.getRepostNum(), post.getText(), userResponse,
                post.getUsers().size());
    }

}

package com.example.demo.Mapper;

import com.example.demo.models.User;
import com.example.demo.response.UserRespone.UserResponse;

public class UserMapper {

    public UserResponse toUserResponse(User user) {
        if (user == null)
            return null;
        return new UserResponse(user.getUserId(), user.getAge(), user.getName(), user.getTag(), user.getBirthDate(),
                user.getPassword(), user.getEmail(), user.getFollowers().size(), user.getFollowings().size());
    }

}

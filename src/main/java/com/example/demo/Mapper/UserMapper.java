package com.example.demo.Mapper;

import com.example.demo.models.User;
import com.example.demo.response.UserResponse;
import java.util.List;
import java.util.ArrayList;
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        if (user == null)
            return null;
        return new UserResponse(user.getUserId(), user.getAge(), user.getFullName(), user.getTag(), user.getBirthDate(),
                user.getPassword(), user.getEmail(), user.getFollowers().size(), user.getFollowings().size(),user.getImage());
    }

    public List<UserResponse> toUserResponse(List<User> users) {
        List<UserResponse> response=new ArrayList();

        for(User user:users)
            response.add(toUserResponse(user));

        
            return response;
    }
}

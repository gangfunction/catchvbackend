package com.catchvbackend.usermanage.UserRepository.UserMember;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    //concurrenthash_map으로 동시성 문제 해결
    public static final Map<Long, User> USER_MAP = new ConcurrentHashMap<>();

    public static long sequence =0L;

    public User save(User user){
        user.setId(++sequence);
        USER_MAP.put(user.getId(), user);
        return user;
    }

    public User findById(Long id){
        return USER_MAP.get(id);
    }

    public List<User> findAll(){
        return new ArrayList<User>(USER_MAP.values());
    }
    public void update(Long id, User updateUser){
        User findUser = findById(id);
        updateUser.setUserEmail(findUser.getUserEmail());
        updateUser.setUserPassword(findUser.getUserPassword());
    }
    public void clearUser(){
        USER_MAP.clear();
    }
}

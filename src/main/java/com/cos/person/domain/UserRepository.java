package com.cos.person.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private List<User> users;
    private int id;

    public UserRepository() {
        this.users = new ArrayList<>();
        users.add(new User(1, "AAA", "1234", "01011111111"));
        users.add(new User(2, "BBB", "1234", "01011111111"));
        users.add(new User(3, "CCC", "1234", "01011111111"));
        users.add(new User(4, "DDD", "1234", "01011111111"));

        id = users.size();
    }

    public List<User> findAll() {
        return users;
    }


    public User findById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }


    public void save(JoinReqDTO dto) {
        id += 1;
        User user = new User();
        user.setId(id);
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        users.add(user);
    }


    public void delete(int id) {
        users = users.stream()
                .filter(user -> user.getId() != id)
                .collect(Collectors.toList());
    }



    public void update(int id, UpdateReqDTO dto) {
        for(User user : users) {
            if (user.getId() == id) {
                user.setPassword(dto.getPassword());
                user.setPhone(dto.getPhone());
                break;
            }
        }
    }


}

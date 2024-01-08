package com.adrianlucena.service;

import com.adrianlucena.exception.RecordNotFoundException;
import com.adrianlucena.model.User;
import com.adrianlucena.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repo;

    public List<User> getAllUsers() {
        List<User> users = repo.findAll();
        return users;
    }

    public User getUserById(int id) {
        Optional<User> user = repo.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RecordNotFoundException("No user with id = " + id);
        }

    }

    public User createOrUpdateUser(User user) {
        User end;
        if (user.getId() != -1) { //update
            Optional<User> result = repo.findById(user.getId());
            if (result.isPresent()) {
                User fromDB = result.get();
                fromDB.setName(user.getName());
                fromDB.setAge(user.getAge());
                end = repo.save(fromDB);
            } else { //insert
                throw new RecordNotFoundException("No user found with id = " + user.getId());
            }
        } else {
            end = repo.save(user);
        }
        return end;
    }


    public void deleteUser(int id) {
        Optional<User> result = repo.findById(id);
        if(result.isPresent()){
            repo.deleteById(id);
        }else{
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }
}

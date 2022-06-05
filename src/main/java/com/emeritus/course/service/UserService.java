package com.emeritus.course.service;

import com.emeritus.course.exception.DataAlreadyExistsException;
import com.emeritus.course.exception.DataNotFoundException;
import com.emeritus.course.model.EmeritusUser;
import com.emeritus.course.util.Roles;
import com.emeritus.course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Iterable<EmeritusUser> getAllUsers() {
        return userRepository.findAll();
    }

    public Iterable<EmeritusUser> getAllUsers(String role) {
        String finalRole = role;
        Optional<Roles> optionalRoles = Arrays.stream(Roles.values()).filter(roles -> roles.getRole().equalsIgnoreCase(finalRole)).findFirst();
        if(optionalRoles.isPresent()){
            return userRepository.findAllByRole(optionalRoles.get().getRole());
        }
        throw new DataNotFoundException(EmeritusUser.class.getSimpleName(), role);
    }

    public EmeritusUser saveUser(EmeritusUser emeritusUser) {
        boolean isUserExists = userRepository.existsById(emeritusUser.getUserName());
        if (isUserExists){
            throw new DataAlreadyExistsException(EmeritusUser.class.getSimpleName(), emeritusUser.getUserName());
        }
        return userRepository.save(emeritusUser);
    }

    public EmeritusUser getUser(String userName) {
        Optional<EmeritusUser> existingUser = userRepository.findById(userName);
        if (existingUser.isPresent()){
            return existingUser.get();
        }
        throw new DataNotFoundException(EmeritusUser.class.getSimpleName(), userName);
    }

    public void deleteUser(String userName) {
        userRepository.deleteById(userName);
    }

    public EmeritusUser updateUser(String userName, EmeritusUser emeritusUser) {
        Optional<EmeritusUser> optionalUser = userRepository.findById(userName);
        if (!optionalUser.isPresent()){
            throw new DataNotFoundException(EmeritusUser.class.getSimpleName(), userName);
        }
        EmeritusUser existingEmeritusUser = optionalUser.get();
        existingEmeritusUser.setFirstName(emeritusUser.getFirstName());
        existingEmeritusUser.setLastName(emeritusUser.getLastName());
        existingEmeritusUser.setEmail(emeritusUser.getEmail());
        existingEmeritusUser.setPhone(emeritusUser.getPhone());
        existingEmeritusUser.setRole(emeritusUser.getRole());

        return userRepository.save(existingEmeritusUser);
    }
}

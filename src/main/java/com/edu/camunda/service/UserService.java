package com.edu.camunda.service;

import com.edu.camunda.exception.user.UserAlreadyExistException;
import com.edu.camunda.exception.user.UserNotFoundException;
import com.edu.camunda.dao.UserDao;
import com.edu.camunda.dto.UserDto;
import com.edu.camunda.dto.UserDtoResponse;
import com.edu.camunda.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class UserService {

    private UserDao repository;


    @Transactional
    public UserDtoResponse createUser(UserDto userDto){

        if (repository.find(userDto.getUsername()).isPresent()){
            throw new UserAlreadyExistException(userDto.getUsername());
        }
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword());
        newUser.setBalance(BigDecimal.ZERO);
        newUser.setAccountNumber(generateUserAccountNumber());
        repository.createUser(newUser);

        return new UserDtoResponse(newUser.getUsername(), newUser.getBalance());

    }

    @Transactional(readOnly = true)
    public User findUserByUsername(String username){
        return repository.find(username).orElseThrow(()-> new UserNotFoundException(username));
    }

    @Transactional(readOnly = true)
    public User findUserByAccountNumber(String accountNumber){
        return repository.findUserByAccountNumber(accountNumber).orElseThrow(()-> new UserNotFoundException(accountNumber));
    }

    @Transactional
    public void updateUser(User user){
        repository.updateUser(user);
    }

    public String generateUserAccountNumber(){

        Random random = new Random();
        StringBuffer accountNumber = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            accountNumber.append(random.nextInt(8) + 1);
        }
        return accountNumber.toString();
    }
}

package com.project.service.validator;

import com.project.entity.user.User;
import com.project.exception.ConflictException;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.abstracts.AbstractUserRequest;
import com.project.payload.request.abstracts.BaseUserRequest;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkUniqueProperty(User user, AbstractUserRequest baseUserRequest){
        String updatedUsername = "";
        String updatedSsn = "";
        String updatedPhone = "";
        String updatedEmail = "";
        boolean isChange = false;

        if(!user.getUsername().equalsIgnoreCase(baseUserRequest.getUsername())){
            updatedUsername = baseUserRequest.getUsername();
            isChange = true;
        }
        if(!user.getSsn().equalsIgnoreCase(baseUserRequest.getSsn())){
            updatedSsn = baseUserRequest.getSsn();
            isChange = true;
        }
        if(!user.getPhoneNumber().equalsIgnoreCase(baseUserRequest.getPhoneNumber())){
            updatedPhone = baseUserRequest.getPhoneNumber();
            isChange = true;
        }
        if(!user.getEmail().equalsIgnoreCase(baseUserRequest.getEmail())){
            updatedEmail = baseUserRequest.getEmail();
            isChange = true;
        }
        if(isChange){
            checkDuplicate(updatedUsername,updatedSsn,updatedPhone,updatedEmail);
        }
    }

    public void checkDuplicate(String username,String ssn,String phone,String email){

        if(userRepository.existsByUsername(username)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME,username));
        }
        if(userRepository.existsBySsn(ssn)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN,ssn));
        }
        if(userRepository.existsByPhoneNumber(phone)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE,phone));
        }
        if(userRepository.existsByEmail(email)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL,email));
        }
    }
}

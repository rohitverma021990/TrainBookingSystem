package com.trainbooking.api;

import com.trainbooking.exception.BadRequestException;
import com.trainbooking.model.User;
import com.trainbooking.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class UserController {

    private UserService userService;

    public String createUser(@NonNull String firstName, @NonNull String lastName, @NonNull String emailId) throws BadRequestException {
        boolean isValidRequest = validateRequest(emailId);
        if(!isValidRequest)
            throw new BadRequestException();
        return userService.createUser(firstName, lastName, emailId);
    }

    private boolean validateRequest(@NonNull final String emailId) {

        return !emailId.equals("") && emailId.contains("@");
    }

    public User getUserObj(@NonNull final String emailId){
        return userService.getUser(emailId);
    }
}

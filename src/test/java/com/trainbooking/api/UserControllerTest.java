package com.trainbooking.api;

import com.trainbooking.exception.BadRequestException;
import com.trainbooking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest extends BaseTest{

    @BeforeEach
    public void setUp(){
        setUpController();
    }

    @Test
    public void testUserCreation() throws BadRequestException {
        String emailId = userController.createUser("first4", "last4", "firstLast4@gmail.com");
        User user = userController.getUserObj(emailId);
        assertTrue(user!=null);
    }

    @Test
    public void testBadRequestException(){
        Exception exception = assertThrows(BadRequestException.class, ()->{
            userController.createUser("first4", "last4", "firstLast4gmail.com");
        });
        assertTrue(exception.getStackTrace()!=null);
    }
}

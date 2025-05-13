package org.example.controller;

import com.google.inject.Inject;
import org.example.config.ObjectValidationException;
import org.example.model.DAO.accessControl.UserDAO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.example.model.Service.UserService;

import java.util.HashMap;
import java.util.Map;

public class SignupController {

    private final UserDAO userDAO;
    private final UserService userService ;

    @Inject
    public SignupController(UserDAO userDAO, UserService userService) {
        this.userDAO = userDAO;
        this.userService = userService;
    }


    /**
     *
     * @param formData taken from SignUp form
     * @return the errorMap whether empty or not, received from service through throw
     */
    public Map<String, String> handleSignup(Map<String, String> formData) {
        Map<String, String> errorMap = new HashMap<>();


        try {
            UserDTO userDTO = userService.signupService(formData);
            userDAO.create(userDTO);
            return errorMap;
        }catch (ObjectValidationException e){
            errorMap = e.getErrorMap();
        } catch (RuntimeException e){
            throw new RuntimeException("Error signing up");
        }

        return errorMap;
    }
}

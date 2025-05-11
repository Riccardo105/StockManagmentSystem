package org.example.controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.example.config.GuiceConfig;
import org.example.config.ObjectValidationException;
import org.example.model.Service.ProductSearchService;
import org.example.model.Service.UserService;

import java.util.HashMap;
import java.util.Map;

public class SignupController {
    private final Injector injector = Guice.createInjector(new GuiceConfig());

    @Inject
    public SignupController() {}


    /**
     *
     * @param formData taken from SignUp form
     * @return the errorMap whether empty or not, received from service through throw
     */
    public Map<String, String> signupHandler(Map<String, String> formData) {
        Map<String, String> errorMap = new HashMap<>();
        UserService userService = injector.getInstance(UserService.class);

        try {
            userService.signupService(formData);
            return errorMap;
        }catch (ObjectValidationException e){
            errorMap = e.getErrorMap();
        } catch (RuntimeException e){
            throw new RuntimeException("Error signing up");
        }

        return errorMap;
    }
}

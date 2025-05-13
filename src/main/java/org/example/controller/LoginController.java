package org.example.controller;

import com.google.inject.Inject;
import org.example.model.DAO.accessControl.UserDAO;
import org.example.model.DAO.accessControl.UserRoleDAO;
import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.example.model.Service.UserService;
import org.example.view.ViewManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginController {

    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Inject
    public LoginController(UserDAO userDAO, UserRoleDAO userRoleDAO, UserService userService, BCryptPasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.userRoleDAO = userRoleDAO;
        this.userService = userService;
        this.encoder = encoder;
    }

    public void handleLogin(String email, String password) {
        UserDTO user;
        try{
            user = userService.loginService(email);
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }

        if (user == null || !encoder.matches(password, user.getPassword()) ) {
            throw new IllegalArgumentException("incorrect credentials");
        }

        if (!user.isActivated()) {
            throw new IllegalArgumentException("account not activated");
        }

        userService.setupUserSession(user);
        ViewManager.showDashboardView();


    }

    public void handleLogout(){
        userService.clearUserSession();
        ViewManager.showLoginView();
    }


    // the following section is responsible for the initialization of the ActivateAccount window
    // login Controller was chosen as responsible for this due to it being couple with UserDAO and UserService already

    public List<UserDTO> getInactiveAccounts(){
        List<UserDTO> users = new ArrayList<>();

        try{
            users = userDAO.getNoneActivatedAccounts();
        } catch (Exception e) {
            System.err.println("getInactiveAccounts failed" + e.getMessage());
        }
        return users;
    }

    public List<RoleDTO> getAvailableRoles(){
        try{
            return userRoleDAO.getAvailableRoles();
        }catch (RuntimeException e){
            e.printStackTrace();
            throw e;
        }
    }

    public void handleAccountActivation(Map<UserDTO, RoleDTO> rolesToAssign){
        try {
            for (Map.Entry<UserDTO, RoleDTO> entry : rolesToAssign.entrySet()) {
                UserDTO user = entry.getKey();
                RoleDTO role = entry.getValue();

                userDAO.activateAccount(user);
                userRoleDAO.assignRoleToUser(user, role);

            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}

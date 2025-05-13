package org.example.model.Service;


import com.google.inject.Inject;
import org.example.config.DbConnection;
import org.example.config.ObjectValidationException;
import org.example.model.DAO.accessControl.RolePermissionDAO;
import org.example.model.DAO.accessControl.UserDAO;
import org.example.model.DAO.accessControl.UserRoleDAO;
import org.example.model.DTO.AccessControl.PermissionDTO;
import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.Session;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;


public class UserService {

    private static final HashSet<PermissionDTO> currentUserPermissions = new HashSet<>();
    private UserDTO currentUser = null;
    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;
    private final RolePermissionDAO rolePermissionDAO;
    private final BCryptPasswordEncoder encoder;

    // necessary dependencies injected through guice
    @Inject
    public UserService(UserDAO userDAO, UserRoleDAO userRoleDAO, RolePermissionDAO rolePermissionDAO, BCryptPasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.userRoleDAO = userRoleDAO;
        this.rolePermissionDAO = rolePermissionDAO;
        this.encoder = encoder;
    }

    public UserDTO loginService (String email) {
        try{
            return userDAO.getByEmail(email);

        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    public UserDTO signupService (Map<String, String> formData) {
        HashMap<String, String> errorMap = new HashMap<>();
        String password = formData.get("password");
        String email = formData.get("email");

        /* all form fields must be inputted (preliminary check also made in front end
           but service is the last line before entering the backend so extra check for security is done here too
         */
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (entry.getValue() == null) {
                errorMap.put(entry.getKey(), "field must not be null");
            }
        }

        // checks password respects constraints
       List<String> errors = passwordValidationHelper(password);
        if (!errors.isEmpty()) {
            for (String error : errors) {
                errorMap.put("password", error);
            }

        }
        // checks email is unique
        boolean isNotValid = emailValidationHelper(email);
        // if true is returned it means the email is already in use
        if (isNotValid) {
            errorMap.put("email", "email already in use" );

        }
        // performing signup if all checks pass
        // hashing password (salt round set to 12 by Guice configuration)
        String hashedPassword = encoder.encode(password);

        if (!errorMap.isEmpty()) {
            throw new ObjectValidationException(errorMap);
        }
        // user object created if validation passes
       return  new UserDTO.Builder()
                .setFirstName(formData.get("firstName"))
                .setLastName(formData.get("lastName"))
                .setEmail(email)
                .setPassword(hashedPassword)
                .build();

    }

    // SignupService helpers (only run if signUp successful)
    protected List<String> passwordValidationHelper(String password) {
        List<String> errors = new ArrayList<>();

        boolean hasMinLength = password.length() >= 8;
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSymbol = password.chars().anyMatch(c -> "!@#$%^&*()".indexOf(c) >= 0);


        if (!hasMinLength) errors.add("Minimum length 8 characters");
        if (!hasUppercase) errors.add("at least one uppercase character");
        if (!hasLowercase) errors.add("at least one lowercase character");
        if (!hasDigit) errors.add("at least one digit");
        if (!hasSymbol) errors.add("at least one special character symbol");


        return errors;
    }
    // returns true if email is found
    protected boolean emailValidationHelper(String email) {
        Session session = DbConnection.getSession();
        String query = "SELECT COUNT(*) FROM UserDTO d WHERE d.email = :email";

        Long count = (Long) session.createQuery(query)
                .setParameter("email", email)
                .uniqueResult();

        return count > 0;
    }

    public void activateHandler (UserDTO user) {
        try {
            userDAO.activateAccount(user);
        } catch (RuntimeException e) {
            throw new RuntimeException("error occurred while trying to activate account:", e);
        }

    }

    // loginHandler helpers  (only run if login successful)
    public void setupUserSession(UserDTO user) {
        currentUser = user;
        List<RoleDTO> userRoles = userRoleDAO.getRolesForUser(user);
        setUserPermissions(rolePermissionDAO.getPermissionsForRoles(userRoles));
    }

    private void setUserPermissions(List<PermissionDTO> Permissions) {
        currentUserPermissions.addAll(Permissions);
    }

    public HashSet<PermissionDTO> getUserPermissions() {
        return currentUserPermissions;
    }

    public boolean checkUserHasPermission(PermissionDTO permission) {
        return currentUserPermissions.contains(permission);
    }

    public void clearUserSession() {
        if(currentUser != null ) {
            currentUser = null;
        }
        currentUserPermissions.clear();
    }

    public static HashSet<PermissionDTO> getCurrentUserPermissions() {
        return currentUserPermissions;
    }

}

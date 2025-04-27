package org.example.model.Service;


import org.example.config.DbConnection;
import org.example.model.DAO.accessControl.RolePermissionDAO;
import org.example.model.DAO.accessControl.UserDAO;
import org.example.model.DAO.accessControl.UserRoleDAO;
import org.example.model.DTO.AccessControl.PermissionsDTO;
import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.Session;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class UserSessionService {

    private final HashSet<PermissionsDTO> userPermissions = new HashSet<>();
    private UserDTO currentUser = null;
    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;
    private final RolePermissionDAO rolePermissionDAO;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // necessary dependencies injected through guice
    public UserSessionService(UserDAO userDAO, UserRoleDAO userRoleDAO, RolePermissionDAO rolePermissionDAO) {
        this.userDAO = userDAO;
        this.userRoleDAO = userRoleDAO;
        this.rolePermissionDAO = rolePermissionDAO;
    }

    public void setUserPermissions(List<PermissionsDTO> Permissions) {
        userPermissions.addAll(Permissions);
    }

    public HashSet<PermissionsDTO> getUserPermissions() {
        return userPermissions;
    }

    public boolean checkUserHasPermission(PermissionsDTO permission) {
        return userPermissions.contains(permission);
    }

    public void clearUserSession() {
        if(currentUser != null ) {
            currentUser = null;
        }
        userPermissions.clear();
    }

    public void loginHandler (String email, String password) {
        UserDTO user;
        try{
            user = userDAO.getByEmail(email);
        }catch(Exception e){
            throw new RuntimeException("failed to retrieve credentials" + email, e);
        }

        if (user == null) {
            throw new IllegalArgumentException("incorrect credentials");
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("incorrect credentials");
        }

        // if login is successful fetch and populate UserPermissions
        setupUserSession(user);

    }

    public void signUpHandler (Map<String, String> formData) {
        String password = formData.get("password");
        String email = formData.get("email");

        /* all form fields must be inputted (preliminary check also made in front end
           but service is the last line before entering the backend so extra check for security is done here too
         */
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (entry.getValue() == null) {
                throw new IllegalArgumentException(entry.getKey() + " cannot be null.");
            }
        }

        // checks password respects constraints
       List<String> errors = passwordValidationHelper(password);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Password validation failed: " + String.join(", ", errors));

        }
        // checks email is unique
        boolean isNotValid = emailValidationHelper(email);
        // if true is returned it means the email is already in use
        if (isNotValid) {
            throw new IllegalArgumentException("invalid email: " + email);

        }
        // performing signup if all checks pass
        // hashing password (default salt rounds 10)
        String hashedPassword = encoder.encode(password);

        // user object created if validation passes
        UserDTO newUser = new UserDTO.Builder()
                .setFirstName(formData.get("firstName"))
                .setLastName(formData.get("lastName"))
                .setEmail(email)
                .setPassword(hashedPassword)
                .build();

        userDAO.create(newUser);

        // default role assigned at registration
        UserRoleDAO userRoleDAO = new UserRoleDAO(DbConnection.getSessionFactory());
        userRoleDAO.assignDefaultRole(newUser);

    }

    public void activateHandler (UserDTO user) {
        try {
            userDAO.activateAccount(user);
        } catch (RuntimeException e) {
            throw new RuntimeException("error occurred while trying to activate account:", e);
        }

    }

    // loginHandler helpers  (only run if login successful)
    protected void setupUserSession(UserDTO user) {
        currentUser = user;
        List<RoleDTO> userRoles = userRoleDAO.getRolesForUser(user);
        setUserPermissions(rolePermissionDAO.getPermissionsForRoles(userRoles));
    }

    // SignupHandler helpers (only run if signUp successful)
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
}

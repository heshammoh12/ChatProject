package iti.chat.common;

public interface SignUpInterface extends LoginInterface
{
    boolean emailValid(String email);
    boolean userNameValid(String userName);
    boolean fullNameValid(String fullName);
    boolean passValid(String password);
    
    boolean emailExists(String email);
    boolean insertUser(User user);  
}

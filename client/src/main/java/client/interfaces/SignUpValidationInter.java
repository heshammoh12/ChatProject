package client.interfaces;

public interface SignUpValidationInter {

    boolean emailValid(String email);

    boolean userNameValid(String userName);

    boolean fullNameValid(String fullName);

    boolean passValid(String password);

}

package client.models;

import client.interfaces.SignUpValidationInter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpValidInterImp implements SignUpValidationInter {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
   //Minimum eight characters, at least one letter, one number and one special character:
    private static final Pattern username_pattern = Pattern.compile("^[a-zA-Z]+[_a-zA-Z0-9]+[a-zA-Z]+$");
    private static final Pattern fullname_pattern = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern password_pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");


    public SignUpValidInterImp() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public boolean emailValid(String email) {

        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean userNameValid(String userName) {
        return username_pattern.matcher(userName).matches();

    }

    @Override
    public boolean fullNameValid(String fullName) {
        return fullname_pattern.matcher(fullName).matches();
    }

    @Override
    public boolean passValid(String password) {

        if (password.length()<8) {
            return false;
        }
        else return true;
    }

}

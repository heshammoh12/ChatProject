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
    //private static final String username_pattern="/^[0-9a-zA-Z]+$/";
    private static final Pattern username_pattern = Pattern.compile("/^[0-9a-zA-Z]+$/");
    private static final Pattern fullname_pattern = Pattern.compile("\\p{Alpha}+");
    private static final Pattern password_pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}");
    //(?=.*[0-9]) a digit must occur at least once
//(?=.*[a-z]) a lower case letter must occur at least once
//(?=.*[A-Z]) an upper case letter must occur at least once
//(?=.*[@#$%^&+=]) a special character must occur at least once
//(?=\\S+$) no whitespace allowed in the entire string
//.{8,} at least 8 characters

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

        return password_pattern.matcher(password).matches();
    }

}

package com.example.util;


public class ValidationUtils {
	 
	public String isValidUsername = "Username has to be character and digits only with length of 4 to 15¾";
    public boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9]{4,15}$");
    }
 
    public String isValidPassword = "Password has to be 8 to 15 in length with mix of at least 1 upper, 1 lower, 1 digit and 1 special character  ";
    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$");
    }
    
    // Numeric Validation
	public String isNumericMsg = "must contains only numeric values";
    public boolean isNumeric(String str) {
        return str.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
    }

    // Alphabetic Validation
    public String isOnlyAlphabetMsg = "must contains only alphabetic values";
    public boolean isOnlyAlphabet(String str) {
        return str.matches("^[a-zA-Z\\s]*$");
    }

    // Alphanumeric Validation
    public String isOnlyAlphaNumericMsg = "must contains only alpha-numeric values";
    public boolean isOnlyAlphaNumeric(String str) {
        return str.matches("^[a-zA-Z0-9\\s]*$");
    }
}

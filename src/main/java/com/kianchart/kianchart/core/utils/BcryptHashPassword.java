package com.kianchart.kianchart.core.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptHashPassword {
    public static String hash(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verify(String hashedPassword, String rawPassword){
        return BCrypt.checkpw(rawPassword,hashedPassword);
    }
}

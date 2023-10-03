package Sql;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
        public static String hashPassword(String password){
         try {
            // instance of sha
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // convert the password to byte and pass that
             // byte to our  sha function
            byte[] passwordBytes = password.getBytes();
            byte[] hashedBytes = digest.digest(passwordBytes);

            //convert the hashed byte to string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
             System.out.println(e);
            return null;
        }

    }
        public static boolean checkPasswordHash(String password, String hashedPassword){
        return hashPassword(password).equals(hashedPassword);
    }

}

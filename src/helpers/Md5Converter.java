package helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Converter {

    public static String hash(String text){

        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder();

        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] resBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));

            for(byte b : resBytes){
                stringBuilder.append(String.format("%02x", b));
            }

        }catch (NoSuchAlgorithmException e){
            System.err.println(e.getMessage());
        }

        return stringBuilder.toString();

    }

}

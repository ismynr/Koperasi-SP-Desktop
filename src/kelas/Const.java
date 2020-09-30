package kelas;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class Const {
    public static String username;
    public static String password;
    
    public static String masterDataMenu;
    public static String masterDataCRUD;
    
    public static String transaksiMenu;
    public static String transaksiCRUD;
    public static String AnggotaCRUD;
    
    public static String encrypt(String str) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString((str.getBytes()));
    }
    
    public static String decrypt(String str) {
        if(str.length() > 0){
            String cipher = str.substring(0);
            return new String (Base64.getDecoder().decode(cipher));
        }
        return null;
    }
}

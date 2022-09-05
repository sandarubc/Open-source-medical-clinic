package lk.ijse.dep9.clinic.misc;

import org.apache.commons.codec.digest.DigestUtils;

public class CryptoUtil {

    public static void main(String[] args) {
        String[] passwords={"admin","doc123","doc456","rec123","rec456"};
        for (String plainPassword:passwords) {
            String ciper = sha256String(plainPassword);
            System.out.println(ciper);

        }

    }


    public static String sha256String(String plainPassword){

        return DigestUtils.sha256Hex(plainPassword);
    }
}

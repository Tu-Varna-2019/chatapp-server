package controller.helpers;

import java.util.Base64;

public class Utils {

    public static String base64Decode(String encodedValue) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
        return new String(decodedBytes);
    }
}

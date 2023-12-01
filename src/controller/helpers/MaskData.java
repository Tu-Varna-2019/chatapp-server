package controller.helpers;

import java.util.Base64;

import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

public class MaskData {

    public static String[] base64Decode(JSONObject jsonPayload) {
        JSONObject dataObject = jsonPayload.getJSONObject("data");

        String[] decodedValues = new String[dataObject.length()];
        int index = 0;

        for (String key : dataObject.keySet()) {
            String encodedValue = dataObject.getString(key);
            byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
            decodedValues[index++] = new String(decodedBytes);
        }
        return decodedValues;
    }

    public static String base64DecodeSelectedValue(String encodedValue) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
        return new String(decodedBytes);
    }

    public static String hashPassword(String password) {

        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkHashedPassword(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }
}

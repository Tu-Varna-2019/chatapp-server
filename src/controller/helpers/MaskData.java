package controller.helpers;

import java.util.Base64;
import java.util.TreeMap;

import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

public class MaskData {

    // Return string of decoded payload and return them alphabetically
    // Example: [username,email,password]
    // Result: [email,password,username]
    public static String[] base64Decode(JSONObject jsonPayload) {
        JSONObject dataObject = jsonPayload.getJSONObject("data");
        TreeMap<String, String> sortedMap = new TreeMap<>();

        for (String key : dataObject.keySet()) {
            String encodedValue = dataObject.getString(key);
            byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
            sortedMap.put(key, new String(decodedBytes));
        }
        return sortedMap.values().toArray(new String[0]);
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

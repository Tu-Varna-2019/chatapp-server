package controller.helpers;

import java.util.Base64;
import java.util.TreeMap;
import com.amazonaws.services.s3.model.S3Object;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import com.amazonaws.util.IOUtils;
import model.storage.S3Manager;

public class MaskData {
    public static TreeMap<String, Object> base64Decode(JSONObject jsonPayload) {
        JSONObject dataObject = jsonPayload.getJSONObject("data");

        TreeMap<String, Object> sortedMap = new TreeMap<>();
        sortedMap.put("data", decode(dataObject));

        if (jsonPayload.has("filter")) {
            JSONObject filterObject = jsonPayload.getJSONObject("filter");
            sortedMap.put("filter", decode(filterObject));
        }

        return sortedMap;
    }

    private static Object decode(Object obj) {
        if (obj instanceof JSONObject) {
            JSONObject dataObject = (JSONObject) obj;
            TreeMap<String, Object> sortedMap = new TreeMap<>();
            for (String key : dataObject.keySet()) {
                Object value = dataObject.get(key);
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    sortedMap.put(key, decode(value));
                } else if (value instanceof String) {
                    // Skip decoding if the key is 'attachmentURL'
                    if ("attachmentURL".equals(key)) {
                        sortedMap.put(key, value);
                        continue;
                    }

                    String encodedValue = (String) value;
                    byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
                    sortedMap.put(key, new String(decodedBytes));
                }
            }
            return sortedMap;
        } else if (obj instanceof JSONArray) {
            JSONArray array = (JSONArray) obj;
            JSONArray decodedArray = new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                Object value = array.get(i);
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    decodedArray.put(decode(value));
                }
            }
            return decodedArray;
        }
        return obj;
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

    public static String base64EncodeS3File(String key) {
        try {
            S3Object object = S3Manager.getDownloadedFile(key);
            byte[] fileContent = IOUtils.toByteArray(object.getObjectContent());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
        }
        return "";
    }
}

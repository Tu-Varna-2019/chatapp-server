package controller.helpers;

import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import model.storage.S3Manager;

public class MaskData {

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkHashedPassword(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }

    public static String base64EncodeS3File(String key) {
        try {
            S3Object object = new S3Manager().getDownloadedFile(key);
            byte[] fileContent = IOUtils.toByteArray(object.getObjectContent());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
        }
        return "";
    }
}

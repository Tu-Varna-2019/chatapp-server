package model.storage;

import java.io.ByteArrayInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import view.SocketConnection;

public class S3Manager {

    public S3Manager() {
        getAWSCredentials();
    }

    private static final String BUCKET_NAME = "tu-varna-chatapp-client-images-aws";
    private static final Logger logger = LogManager.getLogger(SocketConnection.class.getName());

    private static AWSCredentials credentials = null;
    private static AmazonS3 s3 = null;

    private void getAWSCredentials() {
        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (~/.aws/credentials).
         */
        if (credentials == null) {
            try {
                credentials = new ProfileCredentialsProvider().getCredentials();

                /*
                 * Amazon S3 client object initialization
                 */
                if (s3 == null)
                    s3 = AmazonS3ClientBuilder.standard()
                            .withCredentials(new AWSStaticCredentialsProvider(credentials))
                            .withRegion("eu-west-1")
                            .build();

                logger.info("Credentials loaded from the credential profiles file.");

            } catch (Exception e) {
                throw new AmazonClientException(
                        "Cannot load the credentials from the credential profiles file. " +
                                "Please make sure that your credentials file is at the correct " +
                                "location (~/.aws/credentials), and is in valid format.",
                        e);
            }
        }
    }

    public boolean uploadFile(String key, byte[] fileBytes) {
        logger.info("Uploading a new object to S3 from a file\n");
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileBytes.length);
            s3.putObject(
                    new PutObjectRequest(BUCKET_NAME, key, new ByteArrayInputStream(fileBytes), metadata));
            logger.info("File uploaded successfully!");

            return true;
        } catch (AmazonServiceException e) {
            logger.error(
                    "Error uploading file from S3 Bucket!\n");
            e.printStackTrace();
        }
        return false;
    }

    public S3Object getDownloadedFile(String key) {
        logger.info("Downloading an object");
        try {
            S3Object object = s3.getObject(new GetObjectRequest(BUCKET_NAME, key));
            logger.info("Content-Type: " + object.getObjectMetadata().getContentType());

            return object;

        } catch (AmazonServiceException e) {
            logger.error(
                    "Error downloading file from S3 Bucket!\n" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void deleteFile(String key) {
        logger.info("Deleting a file {} from S3\n", key);
        try {
            s3.deleteObject(BUCKET_NAME, key);
            logger.info("File deleted!");

        } catch (AmazonServiceException e) {
            logger.error(
                    "Error uploading file from S3 Bucket!\n");
            e.printStackTrace();
        }
    }
}

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

    private static final String BUCKET_NAME = "tu-varna-chatapp-client-images-aws";
    private static final Logger logger = LogManager.getLogger(SocketConnection.class.getName());

    private static AWSCredentials credentials = null;

    private static void getAWSCredentials() {

        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (~/.aws/credentials).
         */
        if (credentials == null) {
            try {
                credentials = new ProfileCredentialsProvider().getCredentials();
            } catch (Exception e) {
                throw new AmazonClientException(
                        "Cannot load the credentials from the credential profiles file. " +
                                "Please make sure that your credentials file is at the correct " +
                                "location (~/.aws/credentials), and is in valid format.",
                        e);
            }
        }
    }

    public static void uploadFile(String key, byte[] fileBytes) {

        getAWSCredentials();
        try {
            AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion("eu-west-1")
                    .build();

            logger.info("Uploading a new object to S3 from a file\n");

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileBytes.length);
            s3.putObject(
                    new PutObjectRequest(BUCKET_NAME, key, new ByteArrayInputStream(fileBytes), metadata));
        } catch (AmazonServiceException e) {
            logger.error("AmazonServiceException: ", e);
        }

    }

    public static S3Object getDownloadedFile(String key) {

        getAWSCredentials();
        try {
            AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion("eu-west-1")
                    .build();

            logger.info("Downloading an object");

            S3Object object = s3.getObject(new GetObjectRequest(BUCKET_NAME, key));

            logger.info("Content-Type: " + object.getObjectMetadata().getContentType());

            return object;

        } catch (AmazonServiceException e) {
            logger.error("AmazonServiceException: ", e);
            return null;
        }

    }
}

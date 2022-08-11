package com.example.studit.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.studit.domain.enumType.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class S3Service{
    private final AmazonS3Client amazonS3;
    private final String bucket;
    private final String challengeFolder;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public S3Service(AmazonS3Client amazonS3, @Value("${cloud.aws.s3.bucket}") String bucket,  @Value("${cloud.aws.s3.folder.challenge}") String challengeFolder) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
        this.challengeFolder = challengeFolder;
    }

    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName){
        logger.error("S3service");
        amazonS3.putObject(
                new PutObjectRequest(bucket, fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicReadWrite)
        );
    }

    public void deleteFile(String fileName){
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    public String getFileUrl(String fileName){
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public String getFolderName(Category category){
        if(category.equals(Category.CHALLENGE)){
            return this.challengeFolder;
        }
        return null;
    }

}

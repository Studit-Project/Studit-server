package com.example.studit.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.studit.domain.enumType.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileProcessService {
    private final S3Service s3Service;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public FileProcessService(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    public String uploadFile(MultipartFile file, Category category){
        String fileName = s3Service.getFolderName(category) + createFileName(file.getOriginalFilename());

       // logger.error("uploadFile: "+ fileName);

        ObjectMetadata data = new ObjectMetadata();
        data.setContentLength(file.getSize());
        data.setContentType(file.getContentType());

        //logger.error("uploadFile: "+ data.toString());


        try (InputStream inputStream = file.getInputStream()) {
            //logger.error("uploadFile: "+ inputStream.toString());
            s3Service.uploadFile(inputStream, data, fileName);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("FileProcessing -- "+ file.getOriginalFilename());
        }

        return s3Service.getFileUrl(fileName);
    }

    private String createFileName(String fileName){
        return "studit_"+UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public void deleteFile(String url, Category category){
        logger.error(url);
        String fileName = s3Service.getFolderName(category) + getFileName(url);
        s3Service.deleteFile(fileName);
        logger.error(fileName);
    }

    private String getFileName(String url){
        logger.error(url);
        return url.substring(url.lastIndexOf("/")-1);
    }


}

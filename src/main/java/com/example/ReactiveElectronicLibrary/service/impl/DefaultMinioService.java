package com.example.ReactiveElectronicLibrary.service.impl;

import com.example.ReactiveElectronicLibrary.service.ConverterService;
import com.example.ReactiveElectronicLibrary.service.MinioService;
import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class DefaultMinioService implements MinioService {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private ConverterService converterService;

    @Value("${minio.bucket.name}")
    private String defaultBucketName;


    @Override
    public void uploadFile(FilePart file) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(defaultBucketName)
                    .object(file.filename())
                    .stream(converterService.getInputStreamFromFluxDataBuffer(file.content()), -1, 10485760)
                    .build());

        } catch (ServerException | InsufficientDataException | ErrorResponseException | NoSuchAlgorithmException |
                 InvalidKeyException | IOException | InvalidResponseException | XmlParserException | InternalException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void getFile(String filename) {
        try {
            DownloadObjectArgs downloadObjectArgs = DownloadObjectArgs.builder()
                    .bucket(defaultBucketName)
                    .object(filename)
                    .filename(filename)
                    .build();
            minioClient.downloadObject(downloadObjectArgs);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String filename) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .object(filename)
                    .bucket(defaultBucketName)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidResponseException |
                 IOException | NoSuchAlgorithmException | InvalidKeyException | ServerException | XmlParserException e) {
            throw new RuntimeException(e);
        }
    }
}

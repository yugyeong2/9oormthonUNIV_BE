package org.be._9oormthonuniv.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.be._9oormthonuniv.global.util.S3KeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AWSS3Service {

    private static final Logger logger = LoggerFactory.getLogger(AWSS3Service.class);

    private final S3Client s3Client; // 동기 클라이언트
    private final S3TransferManager transferManager; // 고수준 전송 매니저

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // S3 파일 업로드
    public String uploadFile(MultipartFile multipartFile) {
        // 원본 파일 이름 추출 (없으면 "file")
        String originalName = Optional.ofNullable(multipartFile.getOriginalFilename())
                .orElse("file");

        // key 생성
        String key = S3KeyGenerator.generate("images", originalName);

        try {
            // MultipartFile을 임시 파일로 변환
            File tempFile = File.createTempFile("upload-", multipartFile.getOriginalFilename());
            tempFile.deleteOnExit(); // 임시 파일이 JVM 종료될 때 자동으로 삭제되도록 예약

            multipartFile.transferTo(tempFile); // MultipartFile → File 저장

            // 업로드 요청 객체 생성
            UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
                    .putObjectRequest(b -> b.bucket(bucket).key(key)) // S3 버킷과 key 지정
                    .source(tempFile.toPath()) // 업로드 소스 지정
                    .build();

            // 업로드
            FileUpload fileUpload = transferManager.uploadFile(uploadFileRequest);
            CompletedFileUpload uploadResult = fileUpload.completionFuture().join(); // 완료 대기

//            String imageUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;

            return key;

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    // S3 파일 다운로드
    public Long downloadFile(String key) {
        // 파일명 추출 (경로 마지막 부분)
        String fileName = key.substring(key.lastIndexOf("/") + 1);

        // 로컬 저장 경로: 사용자 Downloads 디렉토리
        Path localPath = Paths.get(System.getProperty("user.home"), "Downloads", fileName);

        // 다운로드 요청 객체 생성
        DownloadFileRequest downloadFileRequest = DownloadFileRequest.builder()
                .getObjectRequest(b -> b.bucket(bucket).key(key))
                .destination(localPath)
                .build();

        // 다운로드 실행
        FileDownload downloadFile = transferManager.downloadFile(downloadFileRequest);
        CompletedFileDownload downloadResult = downloadFile.completionFuture().join();

        logger.info("파일 저장 완료: {}", localPath);
        return downloadResult.response().contentLength(); // 바이트 크기 반환
    }
}

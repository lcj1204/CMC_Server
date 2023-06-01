package com.sctk.cmc.util.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static com.sctk.cmc.common.exception.ResponseStatus.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class AmazonS3Service {
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket-domain}")
    private String bucketDomain;
    private final String PROFILE_IMG_UPLOAD_PATH_FORMAT = "%s/info/%d/profile-img/%s";
    private final String DESIGNER_PORTFOLIO_IMG_UPLOAD_PATH_FORMAT = "ROLE_DESIGNER/info/%d/portfolio-img/%s";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;  // S3 버킷 이름

    // 프로필 사진 업로드
    public String uploadProfileImg(MultipartFile multipartFile, Long userId, Role userRole) {
        File uploadFile = convert(multipartFile);
        return uploadProfileImg(uploadFile, userId, userRole);
    }

    private String uploadProfileImg(File uploadFile, Long userId, Role userRole) {
        String fileName = String.format(PROFILE_IMG_UPLOAD_PATH_FORMAT,
                userRole.getRoleName(),
                userId,
                UUID.randomUUID()
        );

        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeLocalFile(uploadFile);
        return uploadImageUrl;
    }

    // 포트폴리오 사진 업로드
    public String uploadPortfolioImg(MultipartFile multipartFile, Long designerId) {
        File uploadFile = convert(multipartFile);
        return uploadPortfolioImg(uploadFile, designerId);
    }

    private String uploadPortfolioImg(File uploadFile, Long designerId) {
        String fileName = String.format(DESIGNER_PORTFOLIO_IMG_UPLOAD_PATH_FORMAT,
                designerId,
                UUID.randomUUID()
        );

        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeLocalFile(uploadFile);
        return uploadImageUrl;
    }

    // 로컬에 파일 업로드 하기
    private File convert(MultipartFile file) {
        File convertFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());

        try {
            if (convertFile.createNewFile()) {
                // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                FileOutputStream fos = new FileOutputStream(convertFile);
                fos.write(file.getBytes());
                return convertFile;
            }

            throw new CMCException(S3_TEMP_FILE_CONVERT_FAIL);
        } catch (IOException exception) {
            throw new CMCException(S3_TEMP_FILE_CONVERT_FAIL);
        }
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(
                new PutObjectRequest(
                        bucket,
                        fileName,
                        uploadFile
                ).withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public boolean delete(String fileUrl) {
        String fileName = getFileName(fileUrl);
        if (!amazonS3.doesObjectExist(bucket, fileName)) {
            throw new CMCException(AWS_FILE_NOT_FOUND);
        }

        amazonS3.deleteObject(this.bucket, fileName);
        return true;
    }

    private String getFileName(String fileUrl) {
        String fileName = fileUrl.substring(bucketDomain.length());
        log.info("fileName : {}", fileName);
        return fileName;
    }

    // 로컬에 저장된 이미지 지우기
    private void removeLocalFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("Image File delete success");
            return;
        }
        log.info("Image File delete fail");
    }
}

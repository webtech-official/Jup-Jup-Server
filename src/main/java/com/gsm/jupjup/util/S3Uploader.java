package com.gsm.jupjup.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gsm.jupjup.advice.exception.FileExtensionNotMatchImageException;
import com.gsm.jupjup.advice.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        imgChk(multipartFile); //이미지 체크
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void deleteS3(String fileName){
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        String fileExtension = file.getContentType().split("/")[1]; //파일확장자
        File convertFile = new File(imgNameMake(file, fileExtension)); // 파일이름을 원래 파일이름 + 날짜 형식으로 저장
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    /** img 예외처리 method
     * img 를 예외체크를 해서 아무 예외도 나오지 않는다면 ture 를 반환한다.
     * @param img
     * @throws ImageNotFoundException, FileExtensionNotMatchImageException
     */
    private void imgChk(MultipartFile img){
        if(img.isEmpty())
            throw new ImageNotFoundException();
        else if(!img.getContentType().split("/")[0].equals("image"))  //type 이 image 가 아니면
            throw new FileExtensionNotMatchImageException();
    }

    /**
     * 기자재 img 이름을 만들어주는 method
     * 현재 시간 + 사진이름 으로 만들어 img 이름을 반환하는 매서드
     *         이미지 이름, 이미지 확장자
     * @param img, imageExtension
     * @param imageExtension
     * @return nameOfImg
     */
    public String imgNameMake(MultipartFile img, String imageExtension){
        StringBuilder nameOfImg = new StringBuilder();

        String originalName = img.getOriginalFilename().replace("." + img.getContentType().split("/")[1], "");
        nameOfImg.append(originalName);
        nameOfImg.append(new Date().getTime());
        nameOfImg.append("." + imageExtension);

        return nameOfImg.toString();
    }

    /** ImgLocation 에서 파일 이름 가져오는 method
     * @param fileLocation
     * @return
     */
    public String getLocationFileName(String fileLocation){
        String[] splitFileLocationSplit = fileLocation.split("/");
        int splitFileLocationSplitLen = splitFileLocationSplit.length;

        return splitFileLocationSplit[splitFileLocationSplitLen - 2] + "/" + splitFileLocationSplit[splitFileLocationSplitLen - 1];
    }
}


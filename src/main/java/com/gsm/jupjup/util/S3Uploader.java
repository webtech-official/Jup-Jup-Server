package com.gsm.jupjup.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gsm.jupjup.advice.exception.FileExtensionNotMatchImageException;
import com.gsm.jupjup.advice.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final String S3_EQUIPMENT_IMG_SAVE_LOCATION = "static/";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 파일 업로드 (총괄)
     * @param multipartFile MultipartFile 타입의 파일
     * @param dirName 파일 위치
     * @return uploadImageUrl => upload(uploadFile, dirName);
     * @throws IOException
     */
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        imgChk(multipartFile); //이미지 체크
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
        return upload(uploadFile, dirName);
    }

    /**
     *
     * removeNewFile함수로 로컬에 있는 파일 삭제
     * @param uploadFile 해당 파일
     * @param dirName 해당 파일 위치
     * @return uploadImageUrl
     * @throws UnsupportedEncodingException 에러 처리
     */
    private String upload(File uploadFile, String dirName) throws UnsupportedEncodingException {
        String fileName = dirName + "/" + uploadFile.getName();
        //file 저장후 UTF-8 로 변환후 저장
        String uploadImageUrl = putS3(uploadFile, fileName);  //파일을 S3에 저장후 s3 리소스에 접근하는 URL 받아오기
        uploadImageUrl = URLDecoder.decode(uploadImageUrl, "UTF-8");  //url 을 UTF-8 로 디코딩
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /**
     * S3에 있는 파일 삭제
     * @param fileName 파일 이름
     */
    public void deleteS3(String fileName){
        try {
            //Delete 객체 생성
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, fileName);
            //Delete
            this.amazonS3Client.deleteObject(deleteObjectRequest);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 로컬 파일 삭제
     * @param targetFile 타겟 파일
     */
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    /**
     * 파일 변환 메소드
     * S3는 MultipartFile 타입을 저장하지 못해 File 타입으로 바꾸어 저장
     * @param file 해당 파일
     * @return Optional<File>
     * @throws IOException 에러 처리
     */
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(imgNameMake(file)); // 파일이름을 원래 파일이름 + 날짜 형식으로 저장
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    /**
     * img 예외처리 method
     * img 를 예외체크를 해서 아무 예외도 나오지 않는다면 ture 를 반환한다.
     * @param img 이미지
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
     * (현재 시간 + 원래 이름 + 확장자) 형식으로 파일이름을 반환하는 매서드
     * @param img, imageExtension
     * @return nameOfImg
     */
    public String imgNameMake(MultipartFile img){
        StringBuilder nameOfImg = new StringBuilder();
        String imgContentType = img.getContentType().split("/")[1];
        // 원래파일 이름 에서 확장자를 제거
        String originalName = img.getOriginalFilename().replace("." + imgContentType, "");
        nameOfImg.append(originalName);
        nameOfImg.append(new Date().getTime());
        // 파일에서 확장자를 다시 이어붙이기
        nameOfImg.append("." + imgContentType);
        return nameOfImg.toString();
    }

    /** DB 에 저장되어있는 ImgLocation 에서 파일 이름 가져오는 method
     * @param fileLocation 파일 저장 위치
     * @return 저장할 위치와, 파일이름을 리턴
     */
    public String getLocationFileName(String fileLocation){
        //원본파일이름을 추출하기위해 슬래시("/") 기준으로 나눔, 원본 파일이름은 배열의 끝방에 있다.
        String[] splitFileLocationSplit = fileLocation.split("/");
        //배열의 끝방에 있는 원본파일이름을 추출하기위해 배열의 끝방 구하기
        int splitFileLocationSplitLastIdx = splitFileLocationSplit.length - 1;
        // 원본파일 이름
        String s3ImgOriginalImgName = splitFileLocationSplit[splitFileLocationSplitLastIdx];
        //저장할 위치와, 파일이름을 추가해서 리턴
        return S3_EQUIPMENT_IMG_SAVE_LOCATION + s3ImgOriginalImgName;
    }
}


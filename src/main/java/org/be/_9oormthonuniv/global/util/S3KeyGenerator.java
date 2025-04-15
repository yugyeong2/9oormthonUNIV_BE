package org.be._9oormthonuniv.global.util;

import java.util.UUID;

public class S3KeyGenerator {

    /**
     * S3에 저장할 고유한 key 생성
     * ex: uploads/posts/UUID.jpg
     *
     * @param prefix        S3 디렉토리 경로 (ex: "images")
     * @param originalName  원본 파일명 (ex: "original.png")
     * @return S3 객체 key (ex: images/UUID.png)
     */
    public static String generate(String prefix, String originalName) {
        String extension = "";

        int dotIndex = originalName.lastIndexOf(".");
        if (dotIndex != -1) {
            extension = originalName.substring(dotIndex); // 확장자 추출 (.jpg, .png)
        }

        String uuid = UUID.randomUUID().toString();
        return prefix + "/" + uuid + extension; // 디렉토리/UUID.확장자 형태
    }
}
package com.leyou.service;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.config.LyUploadFastDFSProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@Slf4j
@EnableConfigurationProperties(LyUploadFastDFSProperties.class)
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private LyUploadFastDFSProperties lyUploadFastDFSProperties;

    public String uploadImage(MultipartFile file) {

        //校验文件格式
        if(!lyUploadFastDFSProperties.getAllowTypes().contains(file.getContentType())){
            throw  new RuntimeException("此文件不是图片");
        }
        //校验文件内容
        StorePath storePath;
        try {
//            判断是否为图片
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            //文件上传到fastDFS
            String exlension= StringUtils.substringAfter(file.getOriginalFilename(),".");
            storePath=storageClient.uploadFile(file.getInputStream(),file.getSize(),exlension,null);

        } catch (IOException e) {
            log.info("此文件不是图片");
            throw  new RuntimeException("此文件不是图片");
        }
        return lyUploadFastDFSProperties.getBaseUrl()+storePath.getFullPath();
    }
}

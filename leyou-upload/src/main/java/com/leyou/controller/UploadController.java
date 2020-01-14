package com.leyou.controller;

import com.leyou.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("item")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {

        String url = uploadService.uploadImage( file );

        if (StringUtils.isEmpty( url )) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
        return ResponseEntity.status( HttpStatus.CREATED ).body( url );
    }
}

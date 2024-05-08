package com.tychicus.opentalk.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ICloudinaryService {
    Map uploadFile(MultipartFile file) throws IOException;

    // get url of image from cloudinary by folderName
    String getUrlImage(String folderName);

}

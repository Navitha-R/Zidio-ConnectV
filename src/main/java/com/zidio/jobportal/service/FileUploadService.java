package com.zidio.jobportal.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadService{
	private final Cloudinary cloudinary;  // Injected from Config

    public String uploadFile(MultipartFile file, String folder) throws Exception {

        if (file.isEmpty()) {
            throw new RuntimeException("⚠ File cannot be empty");
        }

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("folder", folder)
        );

        return uploadResult.get("secure_url").toString();
    }
}
  

	


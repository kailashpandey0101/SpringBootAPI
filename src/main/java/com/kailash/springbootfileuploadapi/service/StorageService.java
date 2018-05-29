package com.kailash.springbootfileuploadapi.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    void store(final MultipartFile file);

    void deleteAll();

    Resource loadAsResource(String filename);

}
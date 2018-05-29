package com.kailash.springbootfileuploadapi.service;

import com.kailash.springbootfileuploadapi.exception.StorageException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class FileSystemStorageServiceTest {

    private FileUploadStorageProperties properties = new FileUploadStorageProperties();

    private FileSystemStorageService service;

    @Before
    public void init() {
        properties.setLocation("out/test.txt");
        service = new FileSystemStorageService(properties);
        service.init();
    }

    @Test(expected = StorageException.class)
    public void saveNotPermitted() {
        service.store(new MockMultipartFile("test", "/test.txt",
                MediaType.TEXT_PLAIN_VALUE, "Test".getBytes()));
    }

    @Test
    public void savePermitted() {
        service.store(new MockMultipartFile("test", "test.txt",
                MediaType.TEXT_PLAIN_VALUE, "test".getBytes()));
    }
}

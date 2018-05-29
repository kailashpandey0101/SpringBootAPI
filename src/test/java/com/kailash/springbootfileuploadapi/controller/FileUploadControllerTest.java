package com.kailash.springbootfileuploadapi.controller;

import com.kailash.springbootfileuploadapi.entity.FileMetaData;
import com.kailash.springbootfileuploadapi.service.FileUploadStorageProperties;
import com.kailash.springbootfileuploadapi.service.FileMetaDataService;
import com.kailash.springbootfileuploadapi.service.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FileUploadController.class)
public class FileUploadControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StorageService storageService;

    @MockBean
    private FileMetaDataService metaDataService;

    @MockBean
    private FileUploadStorageProperties fileUploadStorageProperties;

    @Test
    public void shouldSaveUploadedFile() throws Exception {
        final MockMultipartFile multipartFile = new MockMultipartFile("file", "abc.txt",
                "text/plain", "Test".getBytes());
        given(this.fileUploadStorageProperties.getLocation()).willReturn("target");
        this.mvc.perform(fileUpload("/api/upload").file(multipartFile))
                .andExpect(status().isOk());

        then(this.storageService).should().store(multipartFile);
        then(this.metaDataService).should().saveMetaData(any(FileMetaData.class));
    }

    /*@Test
    public void shouldGetFileMetaData() throws Exception {
        FileMetaData fileMetaData = new FileMetaData();
        fileMetaData.setId(1);
        fileMetaData.setName("test.txt");
        given(this.metaDataService.findMetaDataByFile("test.txt")).willReturn(fileMetaData);
        this.mvc.perform(get("getFileMetaData/test.txt"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDownloadContentStream() throws Exception {
        FileMetaData fileMetaData = new FileMetaData();
        fileMetaData.setId(1);
        fileMetaData.setName("test.txt");
        given(this.metaDataService.findMetaDataByFile("test.txt")).willReturn(fileMetaData);
        this.mvc.perform(get("getFileMetaData/test.txt"))
                .andExpect(status().isOk());
    }*/
}

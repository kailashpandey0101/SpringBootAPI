package com.kailash.springbootfileuploadapi.controller;

import com.kailash.springbootfileuploadapi.entity.FileMetaData;
import com.kailash.springbootfileuploadapi.exception.DuplicateFileNameException;
import com.kailash.springbootfileuploadapi.service.FileUploadStorageProperties;
import com.kailash.springbootfileuploadapi.service.FileMetaDataService;
import com.kailash.springbootfileuploadapi.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/")
public class FileUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    private final StorageService storageService;

    private final FileMetaDataService fileMetaDataService;

    private final FileUploadStorageProperties fileUploadStorageProperties;

    @Autowired
    public FileUploadController(final StorageService storageService, final FileMetaDataService fileMetaDataService, final FileUploadStorageProperties fileUploadStorageProperties) {
        this.storageService = storageService;
        this.fileMetaDataService = fileMetaDataService;
        this.fileUploadStorageProperties = fileUploadStorageProperties;
    }

    @PostMapping("upload-file")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {

        LOGGER.info(file.getOriginalFilename());
        if (fileMetaDataService.findMetaDataByFileFromRepo(file.getOriginalFilename()) != null) {
            throw new DuplicateFileNameException("File with the name already exist. Please upload with new name");
        }
        storageService.store(file);
        final String filePath = fileUploadStorageProperties.getLocation().concat("/").concat(file.getOriginalFilename());
        fileMetaDataService.saveMetaData(new FileMetaData(file.getOriginalFilename(), filePath, file.getContentType()));
        return new ResponseEntity<String>(file.getName(), HttpStatus.OK);
    }

    @GetMapping("getFileMetaData/{fileName:.+}")
    public ResponseEntity<FileMetaData> getFileMetaData(@PathVariable("fileName") final String fileName) {
        return new ResponseEntity<FileMetaData>(fileMetaDataService.findMetaDataByFile(fileName), HttpStatus.OK);
    }

    @GetMapping("download-content-stream/{fileName:.+}")
    public ResponseEntity<Resource> downloadContentStream(@PathVariable("fileName") final String fileName) {
        final Resource file = storageService.loadAsResource(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("getFileId/{fileName:.+}")
    public ResponseEntity<Long> getFileId(@PathVariable("fileName") final String fileName) {
        return new ResponseEntity<Long>(fileMetaDataService.getFileId(fileName), HttpStatus.OK);
    }

}

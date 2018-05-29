package com.kailash.springbootfileuploadapi.service;

import com.kailash.springbootfileuploadapi.entity.FileMetaData;

import java.util.Date;
import java.util.List;

public interface FileMetaDataService {

    public void saveMetaData(FileMetaData fileMetaData);

    public FileMetaData findMetaDataByFile(String fileName);

    public List<FileMetaData> findLastOneHourNewItems(Date time);

    Long getFileId(String fileName);

    FileMetaData findMetaDataByFileFromRepo(String fileName);
}

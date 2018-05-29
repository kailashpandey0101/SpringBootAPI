package com.kailash.springbootfileuploadapi.service;

import com.kailash.springbootfileuploadapi.entity.FileMetaData;
import com.kailash.springbootfileuploadapi.exception.StorageFileNotFoundException;
import com.kailash.springbootfileuploadapi.repository.FileMetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FileMetaDataServiceImpl implements FileMetaDataService {

    private final FileMetaDataRepository fileMetaDataRepository;

    @Autowired
    public FileMetaDataServiceImpl(final FileMetaDataRepository fileMetaDataRepository) {
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    @Override
    public void saveMetaData(final FileMetaData metaData) {
        System.out.println("Date-> " + metaData.getTimeCreated());
        fileMetaDataRepository.save(metaData);
    }

    @Override
    public FileMetaData findMetaDataByFile(final String fileName) {
        final FileMetaData fileMetaData = findMetaDataByFileFromRepo(fileName);
        if (fileMetaData==null) {
            throw new StorageFileNotFoundException("File not found with name: " + fileName);
        }
        return fileMetaData;
    }

    @Override
    public List<FileMetaData> findLastOneHourNewItems(final Date lastOneHourDate) {
        return fileMetaDataRepository.findByTimeCreatedAfter(lastOneHourDate);
    }

    @Override
    public Long getFileId(final String fileName) {
        return findMetaDataByFile(fileName).getId();
    }

    @Override
    public FileMetaData findMetaDataByFileFromRepo(final String fileName) {
        return fileMetaDataRepository.findByName(fileName);
    }
}

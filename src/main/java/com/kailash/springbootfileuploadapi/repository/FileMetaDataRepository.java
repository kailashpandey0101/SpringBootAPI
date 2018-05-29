package com.kailash.springbootfileuploadapi.repository;

import com.kailash.springbootfileuploadapi.entity.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData, Integer> {

    public FileMetaData findByName(String name);

    public List<FileMetaData> findByTimeCreatedAfter(Date oneHourBeforeDate);
}

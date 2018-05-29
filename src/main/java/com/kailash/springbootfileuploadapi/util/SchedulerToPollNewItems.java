package com.kailash.springbootfileuploadapi.util;

import com.kailash.springbootfileuploadapi.entity.FileMetaData;
import com.kailash.springbootfileuploadapi.service.EmailService;
import com.kailash.springbootfileuploadapi.service.FileMetaDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SchedulerToPollNewItems {

    private static final Logger log = LoggerFactory.getLogger(SchedulerToPollNewItems.class);

    private final FileMetaDataService fileMetaDataService;

    private final EmailService emailService;

    @Autowired
    public SchedulerToPollNewItems(final FileMetaDataService fileMetaDataService, final EmailService emailService) {
        this.fileMetaDataService = fileMetaDataService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void sendEmailWithNewItemsInLastHour() {

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        log.info("sending email for last new items in 1 hour");
        final List<String> fileNames = fileMetaDataService.findLastOneHourNewItems(cal.getTime()).stream()
                .map(FileMetaData::getName)
                .collect(Collectors.toList());
        emailService.sendMail("test@test.com", "New Items in last 1 hour", "New Items: " + fileNames.toString());
    }
}

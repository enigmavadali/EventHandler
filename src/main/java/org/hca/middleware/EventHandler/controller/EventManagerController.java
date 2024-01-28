package org.hca.middleware.EventHandler.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hca.middleware.EventHandler.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EventManagerController {

    private static final Logger LOGGER = LogManager.getLogger(EventManagerController.class);

    @Autowired
    private FileManagerService fileManagerService;

    @GetMapping("/ping")
    public String ping(){
        return "Event Handler Service up and running!";
    }

    @PostMapping(value = "/createOrUpdateFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createOrUpdateFile(
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Folder-Name") String folderName,
            @RequestHeader("File-Name") String fileName,
            @RequestHeader("Facility-Id") String facilityId){
        try{
            LOGGER.info("Received file request for file {} from facility {}", fileName, facilityId);
            this.fileManagerService.saveOrOverrideFile(file, fileName, folderName);
            return ResponseEntity.ok("File received successfully!");
        } catch (Exception e){
            LOGGER.error("Error handling file ", e);
            return ResponseEntity.status(500).body("Error handling file: " + e.getMessage());
        }
    }
}

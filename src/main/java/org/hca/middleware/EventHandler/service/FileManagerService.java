package org.hca.middleware.EventHandler.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileManagerService {

    private static final Logger LOGGER = LogManager.getLogger(FileManagerService.class);

    @Value("${file.save.path}")
    private String dirPath;

    public void saveOrOverrideFile(MultipartFile file, String fileName, String folderName) throws Exception{
        try{
            Path uploadPath = Path.of(this.dirPath + folderName);
            Files.createDirectories(uploadPath);

            String filePath = this.dirPath + folderName + "/" + fileName;
            LOGGER.info("Saving file at path: {}", filePath);
            Files.copy(file.getInputStream(), new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Successfully saved {} at {}", fileName,uploadPath.toString());
        } catch (Exception e){
            LOGGER.error("Error while saving file ", e);
            throw e;
        }
    }

}

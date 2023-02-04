package com.example.refugeeshelter.controllers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ImageUploadController {
    private static String imageDirectory = System.getProperty("user.dir") + "/images/";

    @PostMapping(value = "/image", produces = {MediaType.IMAGE_PNG_VALUE, "application/json"})
    public ResponseEntity<?> uploadImage(@RequestParam("img") MultipartFile file,
                                         @RequestParam("name") String name) {
        makeDirectoryIfNotExist(imageDirectory);
        Path fileNamePath = Paths.get(imageDirectory,
                name.concat(".").concat(FilenameUtils.getExtension(file.getOriginalFilename())));
        try {
            Files.write(fileNamePath, file.getBytes());
            return new ResponseEntity<>(name, HttpStatus.CREATED);
        } catch (IOException ex) {
            return new ResponseEntity<>("Image is not uploaded", HttpStatus.BAD_REQUEST);
        }
    }

    @SneakyThrows
    @DeleteMapping(value = "/image/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable("id") Long id) {
        for (int i = 0; i < 5; i++) {
            File file = new File(imageDirectory + id + "_" + (i + 1)  + ".png");
            Files.deleteIfExists(file.toPath());
            File file2 = new File(imageDirectory + id + "_" + (i + 1)  + ".jpeg");
            Files.deleteIfExists(file2.toPath());
            File file3 = new File(imageDirectory + id + "_" + (i + 1)  + ".jpg");
            Files.deleteIfExists(file3.toPath());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @SneakyThrows
    @GetMapping("/image/{name}")
    @ResponseBody
    public ResponseEntity<?> getImageDynamicType(@PathVariable("name") String name) {
        File file = new File(imageDirectory + name + ".png");

        if (!file.exists()) {
            file = new File(imageDirectory + name + ".jpeg");
        }

        if (!file.exists()) {
            file = new File(imageDirectory + name + ".jpg");
        }

        if (!file.exists()) {
            return new ResponseEntity<>("Not Founded!", HttpStatus.BAD_REQUEST);
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
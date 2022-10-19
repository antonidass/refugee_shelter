package com.example.refugeeshelter.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class InfoController {
    @GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity getSwagger() throws IOException {
        ResponseEntity respEntity = null;
        log.info("Working directory =  {}", System.getProperty("user.dir"));

        File result=new File("/resources/swagger/index.html");
        if(result.exists()){
            InputStream inputStream = new FileInputStream("/resources/swagger/index.html");
            byte[]out=org.apache.commons.io.IOUtils.toByteArray(inputStream);
            respEntity = new ResponseEntity(out, HttpStatus.OK);
        }else{
            respEntity = new ResponseEntity ("File Not Found", HttpStatus.NOT_FOUND);
        }
        return respEntity;
    }
}

package com.step.forum.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ResourceController {

    @Value("${file.upload.path}")
    private String imageUploadPath;

    @RequestMapping("/uploads/{image-path}")
    @ResponseBody
    public byte[] getImageFromFileSystem(@PathVariable("image-path") String imagePath) throws IOException {

        imagePath = new String(DatatypeConverter.parseBase64Binary(imagePath));

        Path img = Paths.get(imageUploadPath, imagePath);
        return Files.readAllBytes(img);
    }

}

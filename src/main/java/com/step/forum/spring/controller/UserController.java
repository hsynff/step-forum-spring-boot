package com.step.forum.spring.controller;

import com.step.forum.spring.model.Role;
import com.step.forum.spring.model.User;
import com.step.forum.spring.service.UserService;
import com.step.forum.spring.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.concurrent.Executors;

@Controller
public class UserController {

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailUtil emailUtil;
    @Value("${file.upload.path}")
    private String imageUploadPath;

    @PostMapping("/register")
    public String register(@ModelAttribute("user")User user,@RequestParam("img") MultipartFile img) throws IOException {
        Path pathToSaveFile = Paths.get(imageUploadPath, user.getEmail());

        if (!Files.exists(pathToSaveFile)) {
            Files.createDirectories(pathToSaveFile);
        }

        Path file = Paths.get(pathToSaveFile.toString(), img.getOriginalFilename());

        Files.copy(img.getInputStream(), file, StandardCopyOption.REPLACE_EXISTING);

        Path pathToSaveDb = Paths.get(user.getEmail(), img.getOriginalFilename());

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(new Role(2));
        user.setToken(UUID.randomUUID().toString());
        user.setStatus(2);



        user.setImagePath(DatatypeConverter.printBase64Binary(pathToSaveDb.toString().getBytes()));

        userService.addUser(user);




        Executors
                .newSingleThreadExecutor()
                .submit(()-> emailUtil.sendSimpleMessage(user.getEmail(), user.getFirstName(), user.getToken()));


        return "redirect:/";
    }

    @RequestMapping("/activate")
    public String activateUser(@RequestParam("token")String token, RedirectAttributes redirectAttributes){
        try {
            userService.updateUserStatusByToken(token);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("message", "User activated");
        return "redirect:/login";
    }
}

package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.login.LoginManager;
import com.jb.CouponSystem.repos.CouponRepository;
import com.jb.CouponSystem.repos.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.UUID;

@RestController
@RequestMapping(value = "client")
@CrossOrigin(origins = "*", allowedHeaders = "*")

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor

public abstract class ClientController {

    @Autowired
    private LoginManager loginManager;

//    private final CustomerRepository customerRepository;
//    private final CouponRepository couponRepository;
//
//    private final ImageService imageService;
//
//    @Autowired
//    private TokenManager tokenManager;
//
//    @PostMapping("login")
//    public ResponseEntity<?> login(@RequestBody RequestLogin requestLogin) throws CouponSystemException {
//        String token = loginManager.login(requestLogin.getEmail(), requestLogin.getPassword(), requestLogin.getClientType());
//        Information information = tokenManager.getMap().get(token);
//        System.out.println("from client info------->" + information);
//
//        ResponseLogin responseLogin = ResponseLogin.builder()
//                .id(information.getId())
//                .clientType(information.getClientType())
//                .name(information.getName())
//                .token(token)
//                .build();
//        System.out.println("from client ---------->" + responseLogin);
//        return new ResponseEntity<>(responseLogin, HttpStatus.CREATED);
//    }
//
//    @GetMapping("coupons")
//    public ResponseEntity<?> allCoupons(){
//        return new ResponseEntity<>(couponService.getAllCoupons(),HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "coupons/images/{uuid}", method = RequestMethod.GET)
//    public String getCouponImage(@PathVariable UUID uuid, HttpServletResponse response) throws Exception {
//        Image image = imageService.getImage(uuid);
//
//        response.setHeader("Content-Disposition", "inline;filename=\"" + image.getId().toString() + "\"");
//        OutputStream out = response.getOutputStream();
//        response.setContentType(MediaType.IMAGE_PNG_VALUE);
//        IOUtils.copy(new ByteArrayInputStream(image.getImage()), out);
//        out.close();
//
//
//        return null;
//    }

}

package com.ygy.controller;

import com.ygy.service.CategorySevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * 文件的上传和下载
 */
@RestController
@RequestMapping("/common")
@ResponseBody
@Slf4j
public class CommonController{


    @Value("${reggie.path}")
    String basePath;
    public CommonController() throws IOException {

    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {

        //ClassPathResource classPathResource = new ClassPathResource("backend/images/dishpic/"+name);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(basePath+name)));
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] byt = new byte[1024];
        int len;
        while((len = bis.read(byt)) != -1){
            outputStream.write(byt);
        }

        outputStream.close();
        bis.close();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();
        String[] split = filename.split("\\.");
        String type = split[split.length-1];
        UUID uuid = UUID.randomUUID();
        String fileName = uuid+"."+type;
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(basePath+uuid+"."+type)));
        byte[] byt = new byte[1024];
        InputStream inputStream = file.getInputStream();
        int len;
        while((len = inputStream.read(byt)) != -1){
            bos.write(byt);
        }
        return new Result(Code.SAVE_OK, fileName);
    }

}

package com.itheima.controller;

import com.itheima.common.R;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.imgdir}")
    private String imgdir;

    //文件上传
    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        //1.准备好文件名称
        String fileName = UUID.randomUUID().toString();

        //2.准备好文件夹
        File dirFile = new File(imgdir);
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }

        //3.将临时文件转存到指定文件夹下
        try {
            file.transferTo(new File(dirFile,fileName));
        } catch (IOException e) {
            return R.error("文件上传失败");
        }
        return R.success(fileName);
    }

    //文件下载
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException{
        //1.输入流
        FileInputStream fis = new FileInputStream(new File(imgdir, name));
        //2.输出流
        ServletOutputStream sos = response.getOutputStream();
        response.setContentType("imge/jpeg");
        //3.对拷
        IOUtils.copy(fis,sos);
        fis.close();

    }
}

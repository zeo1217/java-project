package com.qf.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-05
 */

/**
 * 解决跨域问题
 */
@Controller
@Slf4j
public class CORSController {

    @CrossOrigin("*")
    @RequestMapping("lib/Hui-iconfont/1.0.1/iconfont.woff")
    public void woff(HttpServletResponse response) throws Exception {
      copyFile("classpath:static/lib/Hui-iconfont/1.0.1/iconfont.woff",response);
    }

    @CrossOrigin("*")
    @RequestMapping("lib/Hui-iconfont/1.0.1/iconfont.ttf")
    public void ttf(HttpServletResponse response)throws Exception{
        copyFile("classpath:static/lib/Hui-iconfont/1.0.1/iconfont.ttf",response);

    }


    public void copyFile(String filePath,HttpServletResponse response) throws Exception{
        //先要找到资源文件
        File file = ResourceUtils.getFile(filePath);
        //把文件转成流
        FileInputStream ips =null;
        try {
            ips = new FileInputStream(file);
            //拷贝
            IOUtils.copy(ips,response.getOutputStream());
        }catch (IOException e) {
            log.error("文件读取失败！", e);
        }finally {
            IOUtils.closeQuietly(ips);
        }
    }
}

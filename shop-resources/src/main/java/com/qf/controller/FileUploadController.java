package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-05
 */
@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    @Value("${fdfs.server}")
    private String fdfsServer;

    @Autowired
    private FastFileStorageClient  fastFileStorageClient;

    @RequestMapping("/createFile")
    public String createFile(MultipartFile file) throws Exception{
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getInputStream());
        System.out.println(file.getSize());


        String fileExtName = FilenameUtils.getExtension(file.getOriginalFilename());

        // 文件流，文件大小，文件后缀
        StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), fileExtName, null);

        // 最终上传的路径
        System.out.println(fdfsServer+storePath.getFullPath());

        // 返回文件的路径
        return fdfsServer+storePath.getFullPath();
    }
}

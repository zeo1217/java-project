package com.qf.shopresources;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopResourcesApplicationTests {



    public static void main(String[] args) {
        String str = "abc.png";
        String extension = FilenameUtils.getExtension(str);
        System.out.println(extension);
    }

    @Test
    void contextLoads() {
    }

}

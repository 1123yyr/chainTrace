package com.fabric;

import com.fabric.bean.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SdkApplicationTests {
//    @Value("${name}")
//    public String name;
    @Autowired
    private Path path;
    @Test
    public void contextLoads() {
        String keyFolderPath = path.getKeyFolderPath();
        String key=path.keyFileName;
//        String a="D:\\Golang\\fabric\\code\\sdkdemo\\src\\main\\resources\\crypto-config\\\\peerOrganizations\\\n" +
//                "                \\org1.example.com\\\\users\\\\Admin@org1.example.com\\\\msp\\\\keystore";
        System.out.println(keyFolderPath);
        System.out.println(key);
    }

}

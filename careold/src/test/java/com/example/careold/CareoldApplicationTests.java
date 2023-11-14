package com.example.careold;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CareoldApplicationTests {

    @Test
    public void contextLoads() {
        String s1="hello";
        if(s1=="hello"){

            System.out.println("s1 = \"hello\"");

        }else {

            System.out.println("s1 !=hello");
        }
        }

}


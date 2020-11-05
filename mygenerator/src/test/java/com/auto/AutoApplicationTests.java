package com.auto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoApplicationTests {

    @Test
    public void contextLoads() {
    }


    public static void main(String[] args) throws Exception {
        String path2 = ResourceUtils.getURL("classpath:").getPath();
        System.out.println(path2.substring(1));
    }

}

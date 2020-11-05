package auto.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * @author ZhangYou
 * 日 期: 创建时间: 2020/11/05
 * 版 本: v1.0
 * */
public class BaseUtil {
    private static Properties properties;

    public static Properties getProperties(String classPath) throws Exception {
        if (properties == null) {
            String path = System.getProperty("user.dir");
            path += classPath;
            properties = new Properties();
            // 使用InPutStream流读取properties文件
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            properties.load(bufferedReader);
        }
        return properties;
    }
}

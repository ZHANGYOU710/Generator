package auto.bean.utils;

import auto.AutoMain;
import auto.exception.BaseException;
import auto.common.BaseUtil;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 描 述: 各个参数配置
 * @author ZhangYou
 * 日 期: 创建时间: 2020/11/05
 * 版 本: v1.0
 * */
public class BeanConfig {
    private static Log log = LogFactory.getLog(AutoMain.class);
    /**
     * 数据库类
     **/
    public static String JPA_DRIVER_CLASS_NAME = "";
    /**
     * 数据库用户
     **/
    public static String JPA_USER = "";
    /**
     * 数据库密码
     **/
    public static String JPA_PASSWORD = "";
    /**
     * 数据库
     **/
    public static String JPA_DATABASE = "";
    /**
     * ftl模板所在目录
     **/
    public static String JPA_TEMPLATE_PATH = "src/main/java/auto/freemarker/templates/jpa/";
    /**
     * 生成文件路径
     **/
    public static String JPA_CLASS_PATH = "src/main/java/";
    /**
     * 表名（不填写默认全库生成）
     **/
    public static String JAP_TABLE_NAME = "";
    /**
     * 字段是否带下划线
     **/
    public static Boolean JAP_field_Underline = false;
    /**
     * 实体类文件名
     **/
    public static String JAP_Entity_Name = "";

    public static Integer SOUCETYPE = 0;

    public static Map<String, String> notesMap = new HashMap<>();
    /**
     * 生成等级
     */
    public static String FILELEVEL="";


    /**
     * 不同文件导入的包
     **/
    public static String JPA_IMPORTS_BEAN = "import java.lang.*;\nimport java.util.*;\nimport lombok.Data;\nimport lombok.experimental.Accessors;\nimport java.math.BigDecimal;";
    public static String JPA_IMPORTS_BEAN_VO = "import common.model.BaseEntity;";
    public static String JPA_IMPORTS_REPOSITORY = "import common.repository.CommonRepository;\nimport org.springframework.data.repository.NoRepositoryBean;\n\nimport java.lang.*;\nimport java.util.*;\nimport lombok.Data;";
    public static String JPA_IMPORTS_SERVICE = "import com.github.pagehelper.PageHelper;\nimport com.github.pagehelper.PageInfo;\n\nimport java.lang.*;\nimport java.util.*;";
    public static String JPA_IMPORTS_SERVICE_IMPL = "import com.github.pagehelper.PageHelper;\nimport com.github.pagehelper.PageInfo;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.stereotype.Service;\n\nimport java.lang.*;\nimport java.util.*;";
    public static String JPA_IMPORTS_CONTROLLER = "import com.github.pagehelper.PageInfo;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RequestMethod;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;";

    public BeanConfig() {
    }

    static {
        try {
            Properties properties = BaseUtil.getProperties("/src/main/resources/application.properties");

            // 获取key对应的value值
            JPA_DRIVER_CLASS_NAME = properties.getProperty("");
            JPA_DATABASE = properties.getProperty("url");
            JPA_USER = properties.getProperty("username");
            JPA_PASSWORD = properties.getProperty("password");
            JAP_TABLE_NAME = properties.getProperty("tablename");
            if (StringUtil.isEmpty(JAP_TABLE_NAME) || StringUtil.isEmpty(JPA_USER)
                    || StringUtil.isEmpty(JPA_PASSWORD) || StringUtil.isEmpty(JPA_DATABASE)) {
            }
            String type = properties.getProperty("souceType");
            if (type != null && type.equals("c3p0")) {
                SOUCETYPE = 1;
            }

            JAP_field_Underline = Boolean.valueOf(properties.getProperty("underline"));
            JPA_CLASS_PATH += properties.getProperty("packagepath");
            if (StringUtil.isEmpty(JPA_CLASS_PATH)) {
                throw new BaseException("缺少生成路径配置");
            }
            JPA_CLASS_PATH = JPA_CLASS_PATH.replace(".", "/");
            if (!(JPA_CLASS_PATH.substring(JPA_CLASS_PATH.length() - 1)).equals("/")) {
                JPA_CLASS_PATH += "/";
            }
            JAP_Entity_Name = properties.getProperty("classname");

            FILELEVEL=properties.getProperty("filelevel");


            String describe = properties.getProperty("notes.describe");
            String author = properties.getProperty("notes.author");
            String version = properties.getProperty("notes.version");
            String date = properties.getProperty("notes.date");
            if (describe == null || describe.equals("")) version = "";
            if (author == null || author.equals("")) version = "";
            if (version == null || version.equals("")) version = "v1.0";
            if (date == null || date.equals("")) date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            notesMap.put("author", author);
            notesMap.put("version", version);
            notesMap.put("creatortime", date);
            notesMap.put("describe", describe);
        } catch (BaseException e) {
            log.error(e.getMessage());
            log.error("———————————————————————生成失败———————————————————————");
            throw e;
        } catch (Exception e) {
            log.error("配置文件加载失败");
            e.printStackTrace();
        }

    }
}

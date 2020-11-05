package auto;

import auto.bean.utils.BeanConfig;
import auto.bean.utils.BeanUtils;
import auto.freemarker.JpaUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描 述: 请描述功能
 * @author ZhangYou
 * 日 期: 创建时间: 2020/11/05
 * 版 本: v1.0
 * */
public class AutoMain {
    private static Log log = LogFactory.getLog(AutoMain.class);

    public static void main(String[] args) {
        try {
            log.info("###################################################");
            log.info("##--------------------开始生成-------------------##");
            log.info("###################################################");
            JpaUtils.jpa();
            String page = BeanConfig.JPA_CLASS_PATH;
            BeanUtils.openFileDirectory(page.replace("/", "\\"));
            log.info("####################################################");
            log.info("## 生成完成 文件路径：" + BeanConfig.JPA_CLASS_PATH);
            log.info("####################################################");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("———————————————————————生成失败———————————————————————");
        }
    }
}

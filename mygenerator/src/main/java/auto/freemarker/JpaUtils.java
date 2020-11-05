package auto.freemarker;

import auto.bean.utils.BeanConfig;
import auto.bean.utils.BeanUtils;
import auto.pojo.ColumnPOJO;
import auto.pojo.TablePOJO;
import auto.common.TableComlunHandle;
import auto.freemarker.templates.ftlutils.FreemarkerUtils;
import com.github.pagehelper.util.StringUtil;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描 述: 请描述功能
 * @author ZhangYou
 * 日 期: 创建时间: 2020/11/05
 * 版 本: v1.0
 * */
public class JpaUtils {

    public static void jpa() throws Exception {

        jpaBase(BeanConfig.JAP_TABLE_NAME, "bean.ftl", BeanConfig.JPA_IMPORTS_BEAN, "", "pojo", ".java");
        jpaBase(BeanConfig.JAP_TABLE_NAME, "mapper.ftl", BeanConfig.JPA_IMPORTS_CONTROLLER, "Mapper", "mapper", ".java");
        jpaBase(BeanConfig.JAP_TABLE_NAME, "mapper_xml.ftl", BeanConfig.JPA_IMPORTS_CONTROLLER, "Mapper", "mapper", ".xml");


        /**
         * 可选生成模块
         */
        //生成mapper及xml
        if (StringUtil.isEmpty(BeanConfig.FILELEVEL) || BeanConfig.FILELEVEL.equals("service") || BeanConfig.FILELEVEL.equals("controller")) {
            jpaBase(BeanConfig.JAP_TABLE_NAME, "service.ftl", BeanConfig.JPA_IMPORTS_SERVICE, "Service", "service", ".java");
            jpaBase(BeanConfig.JAP_TABLE_NAME, "service_impl.ftl", BeanConfig.JPA_IMPORTS_SERVICE_IMPL, "ServiceImpl", "service//impl", ".java");
        }
        if (StringUtil.isEmpty(BeanConfig.FILELEVEL) || BeanConfig.FILELEVEL.equals("controller")) {
            jpaBase(BeanConfig.JAP_TABLE_NAME, "controller.ftl", BeanConfig.JPA_IMPORTS_CONTROLLER, "Controller", "controller", ".java");
        }

        jpaBase(BeanConfig.JAP_TABLE_NAME,"html.ftl",BeanConfig.JPA_IMPORTS_BEAN,"","",".html");

    }

    /**
     * @param ftlName
     * @param beanConfig
     * @param javaPostfix
     * @param fileName
     * @throws PropertyVetoException
     * @throws SQLException
     */
    public static void jpaBase(String tableName, String ftlName, String beanConfig, String javaPostfix, String fileName, String filePostfix) throws Exception {
        List<TablePOJO> tableList = new ArrayList<>();
        if (tableName == null || tableName.equals("")) {
            tableList = TableComlunHandle.tableColumnType();
        } else {
            tableList = TableComlunHandle.tableColumnType(tableName);
        }
        for (TablePOJO table : tableList) {
            String hump = BeanUtils.toHump((String) table.getTableName(), 1);
            if (StringUtil.isNotEmpty(BeanConfig.JAP_Entity_Name)) {
                hump = BeanConfig.JAP_Entity_Name;
            }
            String java = hump + javaPostfix;

            Map<String, Object> map = new HashMap<>();

            String classPath = BeanConfig.JPA_CLASS_PATH;
            String Package = "";
            if (classPath.contains("src/main/java")) {
                classPath = classPath.split("src/main/java/")[1].replace("/", ".");
                Package = classPath.substring(0, classPath.length() - 1);
                classPath += fileName;
            } else {
                classPath = null;
            }
            map.put("classPath", classPath);
            map.put("Package", Package);
            map.put("imports", beanConfig);
            map.put("className", table.getTableName());
            map.put("tableName", table.getTableName());
            map.put("fileName", java);
            map.put("newMember", filtration(table.getColumnCountList()));
            map.put("underline", BeanConfig.JAP_field_Underline);
            map.put("T", hump);
            map.put("Service", hump + "Service");
            map.put("Repository", hump + "Repository");
            map.put("Controller", toLowerCaseFirstOne(hump));
            map.put("Mapper", hump + "Mapper");
            map.put("mapping", javaPostfix);
            map.putAll(BeanConfig.notesMap);
            FreemarkerUtils.base(map, ftlName, fileName, java + filePostfix);
        }
    }

    /**
     * 过滤
     **/
    private static List<ColumnPOJO> filtration(List<ColumnPOJO> columnPOJOList) {
        List<ColumnPOJO> columnCountList = new ArrayList<>();
        for (ColumnPOJO columnPOJO : columnPOJOList) {
            columnCountList.add(columnPOJO);
        }
        return columnCountList;
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}

package auto.common;

import auto.bean.utils.BeanConfig;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

/**
 * @author ZhangYou
 * 日 期: 创建时间: 2020/11/05
 * 版 本: v1.0
 * */
public class DataSouce {

    private static ComboPooledDataSource cpds;
    /**
     * The duird mysql source.
     */
    private static DataSource druidMysqlSource;


    public static DataSource getC3P0Source() throws PropertyVetoException, SQLException {
        if (cpds == null) {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass(BeanConfig.JPA_DRIVER_CLASS_NAME);
            cpds.setJdbcUrl(BeanConfig.JPA_DATABASE);
            cpds.setUser(BeanConfig.JPA_USER);
            cpds.setPassword(BeanConfig.JPA_PASSWORD);
        }
        return cpds;
    }

    /**
     * 根据类型获取数据源.
     *
     * @return druid或者dbcp数据源
     * @throws Exception the exception
     * @NotThreadSafe
     */
    public static DataSource getDruidSource() throws Exception {
        if (druidMysqlSource == null) {
            druidMysqlSource = DruidDataSourceFactory.createDataSource(
                    BaseUtil.getProperties("/src/main/resources/application.properties"));
        }
        return druidMysqlSource;
    }

    public static void close() {
        if (cpds != null)
            cpds.close();
    }
}

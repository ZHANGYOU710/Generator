package auto.common;

import auto.bean.utils.BeanConfig;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 描 述: 请描述功能
 * @author ZhangYou
 * 日 期: 创建时间: 2020/11/05
 * 版 本: v1.0
 * */
public class DataCommon {

    private static Connection con;
    private static Statement sta;
    private static ResultSet rs;

    /**
     * 获取Connection
     *
     * @return
     * @throws SQLException
     * @throws PropertyVetoException
     */
    public static DataSource getDataSource(int souceType) throws Exception {
        DataSource dataSource;
        if (souceType == 1) {
            dataSource = DataSouce.getC3P0Source();
        } else {
            dataSource = DataSouce.getDruidSource();
        }
        // 得到一个Connection
        return dataSource;
    }

    public static ResultSet getConnection(String sql) throws Exception {
        /**默认使用Druid**/
        DataSource dataSource = getDataSource(BeanConfig.SOUCETYPE);
        con = dataSource.getConnection();
        sta = con.createStatement();
        rs = sta.executeQuery(sql);
        return rs;
    }


    public static void close() throws SQLException {
        rs.close();
        sta.close();
        con.close();
        DataSouce.close();
    }
}

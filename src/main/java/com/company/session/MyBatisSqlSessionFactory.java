package com.company.session;

import com.company.tools.ConstantData;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author santiago.mamani
 */
public class MyBatisSqlSessionFactory {

    private static final SqlSessionFactory SESSION;

    static {
        try {
            String resource = ConstantData.FILE_CONFIGURATION;
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SESSION = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return SESSION;
    }
}

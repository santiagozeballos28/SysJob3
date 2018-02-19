package com.company.session;

import com.company.tools.ConstantData;
import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author santiago.mamani
 */
public class Connection {

    public SqlSession getSqlSession() throws IOException {
        Reader reader = Resources.getResourceAsReader(ConstantData.FILE_CONFIGURATION);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        return sessionFactory.openSession();
    }
}

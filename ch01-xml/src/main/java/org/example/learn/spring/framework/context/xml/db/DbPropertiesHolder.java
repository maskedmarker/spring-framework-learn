package org.example.learn.spring.framework.context.xml.db;

import java.util.Properties;

public class DbPropertiesHolder {

    Properties dbConfiguration = new Properties();

    public Properties getDbConfiguration() {
        return dbConfiguration;
    }
    public void setDbConfiguration(Properties dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }
}

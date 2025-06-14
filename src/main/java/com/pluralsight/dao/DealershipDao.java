package com.pluralsight.dao;

import org.apache.commons.dbcp2.BasicDataSource;

public class DealershipDao {

    private BasicDataSource dataSource;

    public DealershipDao(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }


}
package com.pluralsight.dao;

import org.apache.commons.dbcp2.BasicDataSource;

public class SalesContractDao {

    private BasicDataSource dataSource;

    public SalesContractDao(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
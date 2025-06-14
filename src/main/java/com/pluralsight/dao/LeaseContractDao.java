package com.pluralsight.dao;

import org.apache.commons.dbcp2.BasicDataSource;

public class LeaseContractDao {

    private BasicDataSource dataSource;

    public LeaseContractDao(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
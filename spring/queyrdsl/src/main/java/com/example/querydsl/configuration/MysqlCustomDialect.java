package com.example.querydsl.configuration;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MysqlCustomDialect extends MySQL57Dialect {

    public MysqlCustomDialect() {
        super();
        registerFunction(
                "CreatePrefix",
                new StandardSQLFunction("createPrefix", StandardBasicTypes.STRING)
        );
    }
}

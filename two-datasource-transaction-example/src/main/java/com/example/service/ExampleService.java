package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.dao.ExampleDao;
import com.example.transaction.TransactionalFirst;
import com.example.transaction.TransactionalSecond;

@Service
public class ExampleService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ExampleDao dao;

    public ExampleService(final ExampleDao dao) {
        this.dao = dao;
    }

    @TransactionalFirst
    public void selectFirstDatabaseWithTransaction() {
        logger.info("begin selectFirstDatabaseWithTransaction");
        dao.selectFirstDatabase();
        dao.selectFirstDatabase();
        dao.selectFirstDatabase();
        logger.info("end selectFirstDatabaseWithTransaction");
    }

    @TransactionalSecond
    public void selectSecondDatabaseWithTransaction() {
        logger.info("begin selectSecondDatabaseWithTransaction");
        dao.selectSecondDatabase();
        dao.selectSecondDatabase();
        dao.selectSecondDatabase();
        logger.info("end selectSecondDatabaseWithTransaction");
    }

    public void selectFirstDatabase() {
        logger.info("begin selectFirstDatabase");
        dao.selectFirstDatabase();
        dao.selectFirstDatabase();
        dao.selectFirstDatabase();
        logger.info("end selectFirstDatabase");
    }

    public void selectSecondDatabase() {
        logger.info("begin selectSecondDatabase");
        dao.selectSecondDatabase();
        dao.selectSecondDatabase();
        dao.selectSecondDatabase();
        logger.info("end selectSecondDatabase");
    }
}

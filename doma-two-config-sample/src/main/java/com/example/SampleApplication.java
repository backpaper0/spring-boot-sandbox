package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.sql.DataSource;
import org.seasar.doma.jdbc.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

    @Autowired
    PrimaryDao primaryDao;

    @Autowired
    SecondaryDao secondaryDao;

    @Override
    public void run(String... args) throws Exception {
        String border = IntStream.range(0, 100).mapToObj(x -> "*").collect(Collectors.joining());

        System.out.println(border);

        showConfig(primaryDao.getInjectedConfig());

        System.out.println(border);

        showConfig(secondaryDao.getInjectedConfig());

        System.out.println(border);
    }

    private static void showConfig(Config config) throws Exception {
        System.out.println(config);
        System.out.println(config.getDialect());
        System.out.println(config.getSqlFileRepository());
        System.out.println(config.getNaming());

        DataSource dataSource = config.getDataSource();
        try (Connection con = dataSource.getConnection();
                PreparedStatement pst = con.prepareStatement("select database()");
                ResultSet rs = pst.executeQuery()) {
            rs.next();
            System.out.println(rs.getString(1));
        }
    }
}
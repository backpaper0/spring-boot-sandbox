package com.example.connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.connection.component.Foo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectionApplication.class)
public class ConnectionTest {

    @Autowired
    private Foo foo;
    @MockBean
    private DataSource dataSource;

    @Test
    public void fooWillCommit() throws Exception {

        final Connection con = mock(Connection.class);
        //        doAnswer(i -> {
        //            new Throwable().printStackTrace(System.out);
        //            return null;
        //        }).when(con).close();

        when(dataSource.getConnection()).thenReturn(con);

        foo.willCommit();

        verify(con).commit();
        verify(con).close();
    }

    @Test
    public void fooWillRollback() throws Exception {

        final Connection con = mock(Connection.class);

        when(dataSource.getConnection()).thenReturn(con);

        assertThrows(RuntimeException.class, () -> foo.willRollback());

        verify(con).rollback();
        verify(con).close();
    }
}

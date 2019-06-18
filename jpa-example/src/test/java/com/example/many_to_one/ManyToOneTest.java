package com.example.many_to_one;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@SpringBootTest
@Transactional
class ManyToOneTest {

    @Autowired
    private EntityManager em;

    private Long author1;
    private Long book1;
    private Long book2;
    private Long book3;

    @BeforeEach
    void setUp() throws Exception {

        final AuthorM2O a = new AuthorM2O();
        a.setName("コナン・ドイル");

        final BookM2O b1 = new BookM2O();
        b1.setTitle("緋色の研究");

        final BookM2O b2 = new BookM2O();
        b2.setTitle("四つの署名");

        final BookM2O b3 = new BookM2O();
        b3.setTitle("恐怖の谷");
        b3.setAuthor(a);

        em.persist(a);
        em.persist(b1);
        em.persist(b2);
        em.persist(b3);

        em.flush();

        author1 = a.getId();
        book1 = b1.getId();
        book2 = b2.getId();
        book3 = b3.getId();
    }

    @AfterEach
    void tearDown() throws Exception {
        em.remove(em.find(AuthorM2O.class, author1));
        em.remove(em.find(BookM2O.class, book1));
        em.remove(em.find(BookM2O.class, book2));
        em.remove(em.find(BookM2O.class, book3));
    }

    @Test
    void getAuthorViaBook() throws Exception {
        final BookM2O b = em.find(BookM2O.class, book3);
        assertEquals("コナン・ドイル", b.getAuthor().getName());
    }
}

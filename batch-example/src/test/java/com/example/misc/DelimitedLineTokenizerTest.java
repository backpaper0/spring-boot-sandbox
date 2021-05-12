package com.example.misc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;

public class DelimitedLineTokenizerTest {

    DelimitedLineTokenizer sut = new DelimitedLineTokenizer();

    @Test
    void testDefault() throws Exception {
        FieldSet fs = sut.tokenize("foo,bar,baz");
        assertEquals(3, fs.getFieldCount());
        assertEquals("foo", fs.readString(0));
        assertEquals("bar", fs.readString(1));
        assertEquals("baz", fs.readString(2));
    }

    @Test
    void testQuoted() throws Exception {
        FieldSet fs = sut.tokenize("foo,\"bar\",baz");
        assertEquals(3, fs.getFieldCount());
        assertEquals("foo", fs.readString(0));
        assertEquals("bar", fs.readString(1));
        assertEquals("baz", fs.readString(2));
    }

    @Test
    void testCommaInQuoted() throws Exception {
        FieldSet fs = sut.tokenize("foo,bar,\"baz,qux\"");
        assertEquals(3, fs.getFieldCount());
        assertEquals("foo", fs.readString(0));
        assertEquals("bar", fs.readString(1));
        assertEquals("baz,qux", fs.readString(2));
    }

    @Test
    void testWithName() throws Exception {
        sut.setNames("aaa", "bbb", "ccc");
        FieldSet fs = sut.tokenize("foo,bar,baz");
        assertEquals(3, fs.getFieldCount());
        assertEquals("foo", fs.readString("aaa"));
        assertEquals("bar", fs.readString("bbb"));
        assertEquals("baz", fs.readString("ccc"));
    }
}

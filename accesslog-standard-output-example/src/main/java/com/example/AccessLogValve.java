package com.example;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.catalina.valves.AbstractAccessLogValve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AccessLogValve extends AbstractAccessLogValve {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Writer writer = new Writer() {

        @Override
        public void write(final char[] cbuf, final int off, final int len) throws IOException {
            if (logger.isInfoEnabled()) {
                logger.info(String.valueOf(cbuf, off, len));
            }
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }
    };

    @Override
    protected void log(final CharArrayWriter message) {
        try {
            message.writeTo(writer);
        } catch (final IOException e) {
            logger.warn(sm.getString(
                    "accessLogValve.writeFail", message.toString()), e);
        }
    }
}

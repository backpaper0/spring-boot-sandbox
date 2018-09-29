package com.example;

import io.r2dbc.spi.Row;

public final class MessageMapper {

    public Message map(final Row row) {

        //see: io.r2dbc.postgresql.codec.Codec
        //     io.r2dbc.postgresql.codec.Codecs
        //     io.r2dbc.postgresql.codec.DefaultCodecs

        final Integer id = row.get("id", Integer.class);
        final String text = row.get("text", String.class);

        return new Message(id, text);
    }
}

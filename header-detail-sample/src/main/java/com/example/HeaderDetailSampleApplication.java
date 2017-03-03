package com.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.dao.HeaderDetailDao;

@SpringBootApplication
public class HeaderDetailSampleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HeaderDetailSampleApplication.class, args);
    }

    @Autowired
    HeaderDetailDao dao;

    @Override
    public void run(String... args) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLStreamWriter writer = XMLOutputFactory.newFactory().createXMLStreamWriter(out);
        writer.writeStartDocument();
        writer.writeStartElement("root");
        dao.selectAll(s -> {
            s.forEach(entity -> {
                try {
                    if (entity.detailId == entity.firstId) {
                        writer.writeStartElement("entity");
                        writer.writeStartElement("header");
                        writer.writeAttribute("id", String.valueOf(entity.headerId));
                        writer.writeCharacters(entity.headerName);
                        writer.writeEndElement();
                        writer.writeStartElement("details");
                    }
                    writer.writeStartElement("detail");
                    writer.writeAttribute("id", String.valueOf(entity.detailId));
                    writer.writeCharacters(entity.detailName);
                    writer.writeEndElement();
                    if (entity.detailId == entity.lastId) {
                        writer.writeEndElement();
                        writer.writeEndElement();
                    }
                } catch (XMLStreamException e) {
                    throw new RuntimeException(e);
                }
            });
            return null;
        });
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.flush();
        writer.close();

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");
        transformer.transform(new StreamSource(new ByteArrayInputStream(out.toByteArray())),
                new StreamResult(System.out));
    }
}

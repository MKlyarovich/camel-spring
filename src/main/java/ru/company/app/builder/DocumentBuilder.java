package ru.company.app.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.company.app.model.Document;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DocumentBuilder {

    public List<Document> getAllDocuments(List<Map<String, String>> dataList) {
        return dataList.stream()
                .map(data -> new Document(getId(data.get("id")), data.get("number"), getDate(data.get("date"))))
                .collect(Collectors.toList());
    }

    public Document getDocument(Map<String, String> data) {
        Document document = new Document();
        document.setId(getId(data.get("id")));
        document.setNumber(data.get("number"));
        document.setDate(getDate(data.get("date")));

        return document;
    }

    public Map<String, Object> addDocument(Exchange exchange) throws IOException {
        String documentAsString = exchange.getIn().getBody(String.class);
        Document document = new ObjectMapper().readValue(documentAsString, Document.class);

        Map<String, Object> result = new HashMap<>();
        result.put("number", document.getNumber());
        result.put("date", document.getDate());

        return result;
    }

    @Nullable
    private Long getId(Object value) {
        if (value instanceof Number) {
            return ((Integer) value).longValue();
        }

        return null;
    }

    @Nullable
    private Date getDate(Object value) {
        if (value instanceof Date) {
            return (Date) value;
        }

        return null;
    }
}
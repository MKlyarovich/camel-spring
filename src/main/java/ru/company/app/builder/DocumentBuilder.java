package ru.company.app.builder;

import org.apache.camel.Exchange;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.company.app.model.Document;

import java.util.*;

@Component
public class DocumentBuilder {

    public Map<String, Object> addDocument(Document document) {
        Map<String, Object> result = new HashMap<>();
        result.put("number", document.getNumber());
        result.put("date", document.getDate());

        return result;
    }

    @Nullable
    public Document getDocument(Exchange exchange) {
        Map map = exchange.getIn().getBody(Map.class);
        Object result = map.get("#result-set-1");

        if (result instanceof List) {
            List list = (ArrayList) result;
            Map data = (Map) list.get(0);

            return new Document(getId(data.get("id")), getNumber(data.get("number")), getDate(data.get("date")));
        }

        return null;
    }

    @Nullable
    private Long getId(Object value) {
        if (value instanceof Number) {
            return ((Integer) value).longValue();
        }

        return null;
    }

    @Nullable
    private String getNumber(Object value) {
        if (value instanceof String) {
            return (String) value;
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
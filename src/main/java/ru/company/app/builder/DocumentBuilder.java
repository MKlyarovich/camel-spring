package ru.company.app.builder;

import org.springframework.stereotype.Component;
import ru.company.app.model.Document;

import java.util.HashMap;
import java.util.Map;

@Component
public class DocumentBuilder {

    public Map<String, Object> addDocument(Document document) {
        Map<String, Object> result = new HashMap<>();
        result.put("number", document.getNumber());
        result.put("date", document.getDate());

        return result;
    }
}
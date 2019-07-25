package ru.company.app.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.company.app.builder.DocumentBuilder;
import ru.company.app.model.Document;

import javax.sql.DataSource;

@Component
public class RepositoryRouteBuilder extends RouteBuilder {

    private final DataSource dataSource;
    private final DocumentBuilder documentBuilder;

    @Autowired
    public RepositoryRouteBuilder(DataSource dataSource, DocumentBuilder documentBuilder) {
        this.dataSource = dataSource;
        this.documentBuilder = documentBuilder;
    }

    @Override
    public void configure() {
        SqlComponent sqlComponent = getContext().getComponent("sql", SqlComponent.class);
        sqlComponent.setDataSource(dataSource);
        getContext().addComponent("sqlComponent", sqlComponent);

        from("direct:getAllDocumentsRepository")
                .to("sqlComponent:{{sql.getAllDocuments}}")
                .bean(documentBuilder, "getAllDocuments")
                .marshal()
                .json(JsonLibrary.Jackson, Document.class);

        from("direct:addDocumentRepository")
                .bean(documentBuilder, "addDocument")
                .to("sqlComponent:{{sql.addDocument}}")
                .bean(documentBuilder, "getDocument")
                .marshal()
                .json(JsonLibrary.Jackson, Document.class);
    }
}
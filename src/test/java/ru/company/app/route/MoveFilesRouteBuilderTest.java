package ru.company.app.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import ru.company.app.processor.MoveFilesProcessor;

import java.io.File;
import java.nio.file.Paths;

public class MoveFilesRouteBuilderTest extends CamelTestSupport {

    private static final String CONTENT = "{" +
            "\"number\": \"number754\"," +
            "\"date\": 1564058295404" +
            "}";
    private static final String DESTINATION_FOLDER = "destination-folder";

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new MoveFilesRouteBuilder(new MoveFilesProcessor());
    }

    @Test
    public void moveFilesTest() {
        template.sendBody("direct:saveDocumentToFileSystem", CONTENT);

        File folder = new File(DESTINATION_FOLDER);
        assertTrue(folder.isDirectory());

        Exchange exchange = consumer.receive("file:destination-folder");
        String fileName = (String) exchange.getIn().getHeader(Exchange.FILE_NAME);
        File file = Paths.get(DESTINATION_FOLDER, fileName).toFile();
        assertTrue(file.isFile());
    }
}
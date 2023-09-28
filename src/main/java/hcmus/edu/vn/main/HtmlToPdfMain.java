package hcmus.edu.vn.main;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class HtmlToPdfMain {

    public static void main(String[] args) {
        try {
            generatePDFFromHTML();
            System.out.println("done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateHtmlToPdf() throws IOException {
        File inputHTML = new File("D:/TEST/esignContract-debit.html");
        Document doc = createWellFormedHtml(inputHTML);
        xhtmlToPdf(doc);
    }

    private static Document createWellFormedHtml(File inputHTML) throws IOException {
        try (InputStream inputStream = Files.newInputStream(inputHTML.toPath())){

            Document document = Jsoup.parse(inputStream, StandardCharsets.UTF_8.name(),"");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.outputSettings().charset(StandardCharsets.UTF_8.name());
            document.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
            // FileUtils.writeStringToFile(inputHTML, document.outerHtml(), StandardCharsets.UTF_8);
            return document;

        } catch (Exception e){
            LOGGER.error("createWellFormedHtml - error: ", e);
        }

        return null;
    }

    private static void xhtmlToPdf(Document doc) throws IOException {
        File font = new File("D:/TEST/arial.ttf");
        try (OutputStream os = Files.newOutputStream(Paths.get("D:/TEST/esignContract.pdf"))) {
            String baseUri = "D:/TEST/";
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri("D:/TEST/esignContract.pdf");
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
            builder.run();
        }
    }

    private static void generatePDFFromHTML() throws IOException, com.itextpdf.text.DocumentException {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("D:/TEST/html.pdf")));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, Files.newInputStream(Paths.get("D:/TEST/esignContract-debit.html")));
        document.close();
    }
}

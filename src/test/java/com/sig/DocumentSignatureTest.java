package com.sig;

import com.key.Keys;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class DocumentSignatureTest {

    DocumentProvider documentProvider = new DocumentProvider();

    @Test
    void signDocument() throws FileNotFoundException, TransformerException {
        Keys keys = new Keys();
        DocumentSignature documentSignature = new DocumentSignature(keys.rsaKeyPair());
        Document doc = documentProvider.createFromFile();
        documentSignature.signDocument(doc);
        writeToFile(doc);
    }

    private void writeToFile(Document document) throws FileNotFoundException, TransformerException {
        String path = getClass().getResource("/inp-transformed.xml").getFile();
        System.out.println(path);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(fileOutputStream));
    }
}
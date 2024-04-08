package com.yuanshuaicn.utils;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


/**
 * @program: rise
 * @description:
 * @author: yuanshuai
 * @create: 2024-04-08 17:54
 **/
@Slf4j
public class XmlDom {
    public static String createDom(String locale, String genderName, String voiceName, String textToSynthesize){
        Document doc = null;
        Element speak, voice;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            doc = builder.newDocument();
            if (doc != null){
                speak = doc.createElement("speak");
                speak.setAttribute("version", "1.0");
                speak.setAttribute("xml:lang", "en-US");
                voice = doc.createElement("voice");
                voice.setAttribute("xml:lang", locale);
                voice.setAttribute("xml:gender", genderName);
                voice.setAttribute("name", voiceName);
                voice.appendChild(doc.createTextNode(textToSynthesize));
                speak.appendChild(voice);
                doc.appendChild(speak);
            }
        } catch (ParserConfigurationException e) {
            log.error("Create ssml document failed: {}",e.getMessage());
            return null;
        }
        return transformDom(doc);
    }

    private static String transformDom(Document doc){
        StringWriter writer = new StringWriter();
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException e) {
            log.error("Transform ssml document failed: {}",e.getMessage());
            return null;
        }
        return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }
}

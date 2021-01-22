package cn.itcast.jsoup;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsoupDemo {

    public static void main(String[] args) throws IOException, XpathSyntaxErrorException {
        String path = JsoupDemo.class.getClassLoader().getResource("student.xml").getPath();
        Document document = Jsoup.parse(new File(path), "utf-8");
//        Elements elements = document.getElementsByTag("name");
//        Element element = elements.get(0);
//        System.out.println(element.text());
//        String str = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
//                "<students>\n" +
//                "    <student number=\"heima_0001\">\n" +
//                "        <name>tom</name>\n" +
//                "        <age>18</age>\n" +
//                "        <sex>male</sex>\n" +
//                "    </student>\n" +
//                "    <student number=\"heima_0002\">\n" +
//                "        <name>jack</name>\n" +
//                "        <age>18</age>\n" +
//                "        <sex>female</sex>\n" +
//                "    </student>\n" +
//                "</students>";
//        Document document = Jsoup.parse(str);
//        System.out.println(document);

        JXDocument jxDocument = new JXDocument(document);
        List<JXNode> jxNodes = jxDocument.selN("//student/name[@id='itcast']");
        for (JXNode jxNode: jxNodes){
            System.out.println(jxNode);
        }
    }
}

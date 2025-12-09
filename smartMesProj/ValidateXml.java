import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.File;

public class ValidateXml {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/main/resources/mapper/EquipmentDataMapper.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.newDocumentBuilder().parse(xmlFile);
            System.out.println("XML is valid");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

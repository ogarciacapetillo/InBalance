package main.utils;

import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by os344312 on 9/8/2016.
 */
public class XMLParser {

    private String _fileName;

    public XMLParser(String fileName){
        this.setFileName(fileName);
    }

    private String convertToFileURL() {
        String path = new File(this.getFileName()).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    public Map<String,String> parseFile()  {
        Map<String,String> map=null;
        try {
            //Create a JAX parser factory and configure it
            SAXParserFactory _pf = SAXParserFactory.newInstance();

            // Set namespaceAware to true to get a parser that corresponds to
            // the default SAX2 namespace feature setting.  This is necessary
            // because the default value from JAXP 1.0 was defined to be false.
            _pf.setNamespaceAware(true);
            SAXParser _parser = _pf.newSAXParser();
            // Set the ContentHandler of the XMLReader
            XMLReader xmlReader = _parser.getXMLReader();
            Handler myHandler = new Handler();
            xmlReader.setContentHandler(myHandler);

            // Set an ErrorHandler before parsing
            xmlReader.setErrorHandler(new Handler.MyErrorHandler(System.err));

            // Tell the XMLReader to parse the XML document
            xmlReader.parse(convertToFileURL());
            List<SourceXML> attributeList = myHandler.getAttributeList();
            if (attributeList.size()>0)
            {
                map = new HashMap<>();
                for(SourceXML attribute: attributeList){
                    map.put("Index",attribute.getOrderReference()+"_"+attribute.getAssignmentKey());
                    map.put("OrderNo",attribute.getOrderReference());
                    map.put("AssignmentKey",attribute.getAssignmentKey());
                    map.put("RejectChoice",attribute.getRejectChoice());
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException caught");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return map;
    }

    public String getFileName() {
        return _fileName;
    }

    public void setFileName(String fileName) {
        this._fileName = fileName;
    }
}

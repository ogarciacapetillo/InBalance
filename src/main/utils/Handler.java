package main.utils;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by os344312 on 9/8/2016.
 */
public class Handler extends DefaultHandler {
    /** Constants used for JAXP 1.2 */
    static final String JAXP_SCHEMA_LANGUAGE =
            "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA =
            "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE =
            "http://java.sun.com/xml/jaxp/properties/schemaSource";

    private List<SourceXML> attributesByOrder=null;
    private SourceXML request=null;
    String orderNo="";

    public List<SourceXML> getAttributeList(){
        return attributesByOrder;
    }

    // Parser calls this once at the beginning of a document
    @SuppressWarnings("rawtypes")
    public void startDocument() throws SAXException {

    }

    boolean bOrderRef = false;
    boolean bOrderNo = false;
    boolean bAssigmentKey = false;
    boolean bRejectChoice = false;

    // Parser calls this for each element in a document
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        String key = localName;
        if (key.equalsIgnoreCase("Promise")){
            request = new SourceXML();
            if(attributesByOrder == null){
                attributesByOrder = new ArrayList<>();
            }
            orderNo =getAttribute(atts,"OrderReference");
            request.setOrderReference(orderNo);
            System.out.println("OrderNo: " +getAttribute(atts,"OrderReference"));
            bOrderRef = true;
        }else if(key.equalsIgnoreCase("Assignment")){
            request.setOrderReference(orderNo);
            request.setAssignmentKey(cleanAssignment(getAttribute(atts,"AssignmentKey")));
            System.out.println("AssignmentKey: "+cleanAssignment(getAttribute(atts,"AssignmentKey")));
            request.setRejectChoice(getAttribute(atts,"RejectChoice"));
            System.out.println("RejectChoice: "+getAttribute(atts,"RejectChoice"));
        }
    }

    private String getAttribute(Attributes att, String atVal){
        try{
            if (att.getValue(atVal)!=null){
                return att.getValue(atVal);
            }else{
                return "";
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }

    private String cleanAssignment(String val){
        String clean=val;
        clean=val.substring(0,val.indexOf(":"));
        return clean;
    }

    // Parser calls this once after parsing a document
    @Override
    public void characters(char ch[], int start, int length) throws SAXException{
        try{
            if (bOrderRef){
                //Get the attribute OrderReference

                bOrderRef = false;
            }else if (bOrderNo){
                bOrderNo = false;

            }else if (bAssigmentKey){
                bAssigmentKey = false;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException  {
        if (localName.equalsIgnoreCase("Assignment")){
            attributesByOrder.add(request);
            request = null;
            request = new SourceXML();
            /*String line =(tc.getIdentifier()+","+tc.getName()+","+tc.getDuration()+","+tc.getPass()+","+tc.getFail()+","+tc.getSkip()+","+tc.getTimestamp()+","+tc.getJobname()+","+tc.getBuildno()+","+tc.getEnv()+System.lineSeparator());
            FileWriter fw = new FileWriter();
            fw.writeToFile (line,tc.getJobname());*/
        }
    }

    @SuppressWarnings("rawtypes")
    public void endDocument() throws SAXException {

    }

    // Error handler to report errors and warnings
    public static class MyErrorHandler implements ErrorHandler {
        /** Error handler output goes here */
        private PrintStream out;

        MyErrorHandler(PrintStream out) {
            this.out = out;
        }

        /**
         * Returns a string describing parse exception details
         */
        private String getParseExceptionInfo(SAXParseException spe) {
            String systemId = spe.getSystemId();
            if (systemId == null) {
                systemId = "null";
            }
            String info = "URI=" + systemId +
                    " Line=" + spe.getLineNumber() +
                    ": " + spe.getMessage();
            return info;
        }

        // The following methods are standard SAX ErrorHandler methods.
        // See SAX documentation for more info.

        public void warning(SAXParseException spe) throws SAXException {
            out.println("Warning: " + getParseExceptionInfo(spe));
        }

        public void error(SAXParseException spe) throws SAXException {
            String message = "Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }

        public void fatalError(SAXParseException spe) throws SAXException {
            String message = "Fatal Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }
    }
}

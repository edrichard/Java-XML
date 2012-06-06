package p.xml;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.io.*;

/**
 * @author edrichard
 */
public class SaxDocBalises {
    public static void main(String[] args) {
        DefaultHandler gestionnaire = new DefaultHandler()
        {
            @Override
            public void startElement(String uri, String localName, String balise, Attributes attributs) throws SAXException
            {
                System.out.println("Balise ouvrante : " + balise);
            }
            
            @Override
            public void endElement(String uri, String localName, String balise)
            {
                System.out.println("Balise Fermante : " + balise);
            }
            
            @Override
            public void characters(char[] caracteres, int debut, int longueur) throws SAXException
            {
                System.out.println(new String(caracteres, debut, longueur));
            }
            
            @Override
            public void error(SAXParseException e) throws SAXException
            {
                System.err.println("Error : " + e.getMessage());
            }
        };



        try
        {
            // --- Creation d'une fabrique de parseurs SAX
            SAXParserFactory fabriqueSAX = SAXParserFactory.newInstance();
            // --- Validation … mais il faut une DTD ou un schema
            //fabriqueSAX.setValidating(true);
            // --- Creation d'un parseur SAX
            SAXParser parseurSAX = fabriqueSAX.newSAXParser();
            // --- Demarrage de l'analyse
            parseurSAX.parse("villesDocument.xml", gestionnaire);
        }
        catch(ParserConfigurationException e)
        {
            System.err.println("Erreur de configuration du parseur\nlors de l'appel a SAXParser.newSAXParser()");
        }
        catch(SAXException e)
        {
            System.err.println(e.toString());
        }
        catch(IOException e)
        {
            System.err.println(e.toString());
        }
    }
}

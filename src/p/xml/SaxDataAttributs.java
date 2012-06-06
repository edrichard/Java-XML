package p.xml;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.io.*;

/**
 * @author edrichard
 */
public class SaxDataAttributs {
    public static void main(String[] args) {
        // --- Objet et sa fonction Callback qui sera appelee sur chaque evenement
        DefaultHandler gestionnaire = new DefaultHandler()
        {
            @Override
            // ---------------------
            public void startElement(String uri, String localName, String balise, Attributes attributs) throws SAXException
            {
                for(int i=0; i<attributs.getLength(); i++)
                {
                    System.out.println(attributs.getQName(i) + " : " + attributs.getValue(i));
                }
            }
        };

        try
        {
            // --- Creation d'une fabrique de parseurs SAX
            SAXParserFactory fabriqueSAX = SAXParserFactory.newInstance();
            // --- Creation d'un parseur SAX
            SAXParser parseurSAX = fabriqueSAX.newSAXParser();
            // --- Demarrage de l'analyse
            parseurSAX.parse("villesData.xml", gestionnaire);
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
    } // --- FIN du main
}

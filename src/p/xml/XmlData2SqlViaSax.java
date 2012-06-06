package p.xml;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.io.*;

/**
 * @author edrichard
 */
public class XmlData2SqlViaSax {
    public static void main(String[] args) {
        // --- Objet et sa fonction Callback qui sera appelee sur chaque evenement
        DefaultHandler gestionnaire = new DefaultHandler()
        {
            @Override
            // ---------------------
            public void startElement(String uri, String localName, String balise, Attributes attributs) throws SAXException
            {
                if(attributs.getLength() > 0){
                    String lsContenu = "";
                    String lsNomChamp = "";
                    String lsValueChamp = "";

                    lsContenu = "INSERT INTO villes";

                    lsNomChamp = "('";
                    lsValueChamp = "('";

                    for(int i=0; i<attributs.getLength(); i++)
                    {
                        lsNomChamp += attributs.getQName(i) + "','";
                        lsValueChamp += attributs.getValue(i) + "','";
                    }

                    lsNomChamp = lsNomChamp.substring(0, lsNomChamp.length()-2);
                    lsValueChamp = lsValueChamp.substring(0, lsValueChamp.length()-2);

                    lsContenu += lsNomChamp + ") VALUES" + lsValueChamp + ")";
                    System.out.println(lsContenu);
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
    }
}

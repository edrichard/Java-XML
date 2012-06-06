package p.xml;

import java.io.*;
import java.sql.*;

/**
 * @author edrichard
 */
public class Bd2XML {
    
    public static void main(String[] args) {
        BD bd = new BD();
        String lsTable = "pays";
        String lsContenu = "";
        lsContenu += "<?xml version='1.0' encoding='utf-8'?>\n";
        lsContenu += "<!-- "+lsTable+"Document.xml -->\n";
        lsContenu += "<" + lsTable + ">\n";
       
        String lsEnfant = lsTable.substring(0,lsTable.length()-1);
   
    try {
        bd.seConnecter("cours", "root", "alonso");
        Connection lcn = bd.getConnexion();
        PreparedStatement lpst = lcn.prepareStatement("SELECT * FROM pays"); // paysSelect() ou villesSelect()
        ResultSet lrs = lpst.executeQuery();
       
        ResultSetMetaData lrsmd = lrs.getMetaData();
       
        int liCols = lrsmd.getColumnCount();
       
        while(lrs.next()){
            lsContenu += "\t<" + lsEnfant + " ";
            
            for (int i=1;i<=liCols;i++)
            {
                lsContenu += lrsmd.getColumnName(i) + "='" + lrs.getString(i)+"' ";
            }
            
            lsContenu += "/>\n";
        }
        
        
        lsContenu += "</" + lsTable + ">";
       
        System.out.println(lsContenu);
       
        FileWriter lfwFichier = new FileWriter(lsTable+".xml"); //pays.csv ou villes.csv
        BufferedWriter lbwBuffer = new BufferedWriter(lfwFichier);
        lbwBuffer.write(lsContenu);
        lbwBuffer.close();
        lfwFichier.close();
       
        bd.seDeconnecter();
   
    }
    catch(Exception e)
    {
        System.out.println("Erreor : ");
    }
    }
}

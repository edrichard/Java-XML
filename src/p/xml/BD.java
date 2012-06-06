package p.xml;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author stagiaire
 */
public class BD {

    // --- Déclarations
    Connection icConnexion = null;
//    Statement lstSql = null;
//    ResultSet lrs = null;
//    String lsPilote = "";
//    String lsServeur = "127.0.0.1";
//    String lsBD = "cours";
//    String lsUt = "root";
//    String lsMdp = "";
//    String lsDSN = "";
//    String lsSelect = "SELECT * FROM villes";
//    String lsContenu = "";

    public String seConnecter(String asBd, String asUt, String asMdp) {
        String Result;

        String lsPilote = "org.gjt.mm.mysql.Driver";
        String lsDSN = "jdbc:mysql://localhost:3306/" + asBd;
        try {
            Class.forName(lsPilote);
            icConnexion = DriverManager.getConnection(lsDSN, asUt, asMdp);
            Result = "OK";
        } catch (ClassNotFoundException e) {
            Result = "Pilote ? " + e.getMessage();
        } catch (SQLException ex) {
            Result = "SQL ? " + ex.getMessage();
        }





        return Result;

    }

    public String getCurseurCSV(String asSelect) {
        String lsContenu = "";
        try {
            Statement lstSql = icConnexion.createStatement();
            ResultSet lrs = lstSql.executeQuery(asSelect);
            while (lrs.next()) {
                lsContenu += lrs.getString(1) + "-" + lrs.getString(2) + "\n";
            }
            lrs.close();
            lstSql.close();

        } //boolean seDeconnecter()
        catch (SQLException e) {
            lsContenu = "SQL = " + e.getMessage();
        }
        return lsContenu;
    }

    public boolean seDeconnecter() {
        boolean retour;

        try {


            icConnexion.close();

            retour = true;

        } catch (SQLException ex) {
            retour = false;
        }

        return retour;
    }
   
    public int supprimer(String asTable, String asCle, String asValeur){
        int liretour=0;
       
        try {
           
           
            this.icConnexion.setAutoCommit(false);
            Statement lst = icConnexion.createStatement();
            liretour = lst.executeUpdate("DELETE FROM " + asTable + " WHERE " +  asCle + " = '" + asValeur + "'");
            this.icConnexion.commit();
           
            lst.close();
           
           
        } catch (SQLException e) {
            liretour = -1;
        }
       
       
       
       
        return liretour;
       
    }
   
    public int valider()
    {
        int liretour = 1;
       
        try {
            this.icConnexion.commit();
        } catch (Exception e) {
            liretour = -1;
        }
         return liretour;
       
    }
   
    public int annuler()
    {
        int liretour = 1;
       
        try {
            this.icConnexion.rollback();
        } catch (Exception e) {
            liretour = -1;
        }
         return liretour;
       
    }
   
     public int inserer(String asTable, HashMap<String,String> ahmColonnesValeurs) {

       int liRetour = 0;
       String lsInsert = "";

       StringBuilder lsbColonnes = new StringBuilder("");
       StringBuilder lsbValeurs = new StringBuilder("");

       // --- Boucle sur le tableau associatif
       for (Map.Entry paireCleValeur: ahmColonnesValeurs.entrySet()) {
           String cle    = (String)paireCleValeur.getKey();
           String valeur = (String)paireCleValeur.getValue();

           lsbColonnes.append(cle);
           lsbColonnes.append(",");

           lsbValeurs.append("'");
           lsbValeurs.append(valeur);
           lsbValeurs.append("'");
           lsbValeurs.append(",");
       }
       lsbColonnes = lsbColonnes.deleteCharAt(lsbColonnes.length()-1);
       lsbValeurs = lsbValeurs.deleteCharAt(lsbValeurs.length()-1);

       lsInsert = "INSERT INTO " + asTable + "(" + lsbColonnes + ") VALUES (" + lsbValeurs + ")";
//       System.out.println(lsInsert);

       try {
           Statement lst = this.icConnexion.createStatement();
           liRetour = lst.executeUpdate(lsInsert);
       }
       catch (SQLException e) {
//           System.out.println(e.getMessage());
           liRetour = -1;
       }

       return liRetour;
   } // Fin inserer()
    
     public Connection getConnexion()
     {
         return this.icConnexion;
     }
}

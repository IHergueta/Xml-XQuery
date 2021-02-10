/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xquery;

import java.util.Scanner;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultItem;
import javax.xml.xquery.XQResultSequence;
import javax.xml.xquery.XQSequence;
import javax.xml.xquery.XQSequenceType;
import net.xqj.exist.ExistXQDataSource;
import org.xmldb.api.base.XMLDBException;
import static xquery.Ejercicio3.numeroCompras;

/**
 *
 * @author Ignacio
 */
public class Ejercicio2 {
    
    public static void main(String[] args) throws XQException {
        
        XQConnection con =  obtenConexion();
        
        Scanner sc = new Scanner(System.in);
        
        
        String[] parametros = new String[6];
        
  
        System.out.println("Dime el titulo del nuevo cd");
        parametros[0]=sc.next();
        
        System.out.println("Dime el artista  del nuevo cd");
        parametros[1]=sc.next();
        
        System.out.println("Dime el pais del nuevo cd");
        parametros[2]=sc.next();
        
        System.out.println("Dime la compania del nuevo cd");
        parametros[3]=sc.next();
        
        System.out.println("Dime el precio del nuevo cd");
        parametros[4]=sc.next();
        System.out.println("Dime el anio del nuevo cd");
        parametros[5]=sc.next();
        
        
        try{
             
             nuevoCD(con, parametros);
             
         }catch(Exception e){
             
             e.printStackTrace();
         }
        
        
        
         
        
    }
        
        
        
    public static void nuevoCD(XQConnection con ,String [] parametros) throws XQException{
        
        int id2= 0;
      
        XQExpression expr = con.createExpression();

        XQSequence id = expr.executeQuery("for $cd in count(doc('/db/pruebas/catalogo.xml')/CATALOG/CD)\n" +
        "return $cd");
        
         while (id.next()) {  
             id2 = id.getInt() +1;
      }  
      
         String id3 = id2 +"";
        expr.executeCommand("update insert\n" +
            "<CD>\n" +
            "<ID> " +  id3  + " </ID>\n" +
            "<TITLE> " + parametros[0] + " </TITLE>\n" +
            "<ARTIST> " + parametros[1] + "</ARTIST>\n" +
            "<COUNTRY> " + parametros[2] + "</COUNTRY>\n" +
            "<COMPANY> " + parametros[3] + "</COMPANY>\n" +      
            "<PRICE> " + parametros[4] + "</PRICE>\n" + 
            "<YEAR> " + parametros[5] + " </YEAR>\n" +     
            "</CD>\n" +
            "into\n" +
            "doc('/db/pruebas/catalogo.xml')/CATALOG");
        
        
        
        
      
 
        
        
    }
    
    public static XQConnection obtenConexion () throws XQException{
        
        XQDataSource server = new ExistXQDataSource();
        server.setProperty("serverName","localhost");
        server.setProperty("port","8090");
        server.setProperty("user","admin");
        server.setProperty("password","admin");
        
        XQConnection conexion = server.getConnection("admin","admin");
        
       
        
        return conexion;

    }
}



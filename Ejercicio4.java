/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xquery;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQSequence;
import static xquery.Ejercicio2.obtenConexion;
import static xquery.Ejercicio3.numeroCompras;

/**
 *
 * @author Ignacio
 */
public class Ejercicio4 {
    
     public static void main(String[] args) throws XQException {
         
         XQConnection con =  obtenConexion();
          
         Scanner sc = new Scanner(System.in);
         
         System.out.println("Dime el dni");
         String dni = sc.next();
         
         System.out.println("Dime el CP");
         String cp = sc.next();
         
         try{
             
             modificarCP(con, dni, cp);
         }catch(IOException e){
             
             e.printStackTrace();
         }
         
         
                 
          
         
         
     }
     
     public static void modificarCP(XQConnection con , String dni,String cp) throws XQException, IOException{
         
         String cpanti2 ="";
         XQExpression expr = con.createExpression();

       
         
        
         
         XQSequence cont = expr.executeQuery("for $num in count(doc('/db/pruebas/clientes.xml')//cliente[@DNI='" + dni + "']) return $num");
       
          XQSequence cpanti = expr.executeQuery("for $cp in doc('/db/pruebas/clientes.xml')//cliente[@DNI='" + dni + "']/CP return data($cp)");
                
          while(cpanti.next()){
                    
                    cpanti2 = cpanti.getItemAsString(null);
                    
                }
          
        while ( cont.next()){
            
            if(cont.getInt() == 0){
                 
                
                 System.out.println("El cliente con DNI = " + dni +" no se encuentra en la base de datos");
                 
             }else{
                
                 expr.executeCommand("update value\n" +
            "doc('/db/pruebas/clientes.xml')/clientes/cliente[@DNI='" + dni + "']/CP\n" +
            "with\n" +
            "\" "+ cp + "\"");
                System.out.println("Ok");
                
                File file = new File("./src/archivo_seguridad/registra_cambios.txt");
                
                if (!file.exists()) {
                    
                    file.createNewFile();
                }
  
                FileWriter x = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(x);
             
                long millis=System.currentTimeMillis();  
                java.sql.Date date=new java.sql.Date(millis);  
                
                
                bw.write("\n-------------------------------------");
                bw.write("\nMODIFICACION CLIENTE DNI = " + dni + " , FECHA = " + date );
                
               
                
                
                bw.write("\nCP ANTERIOR = " + cpanti2);
                bw.write("\nCP NUEVO = " + cp);
                bw.close();
                
                
                
             
      }  
                 
                
            }
        
        
       
         
         
     }
    
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xquery;

import java.util.Scanner;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQSequence;
import static xquery.Ejercicio2.obtenConexion;

/**
 *
 * @author Ignacio
 */
public class Ejercicio3 {
    
     public static void main(String[] args) throws XQException {
         
         XQConnection con =  obtenConexion();
          
         Scanner sc = new Scanner(System.in);
         
         System.out.println("Dime el dni");
         String dni = sc.nextLine();
         
         try{
             
             numeroCompras(con, dni);
             
         }catch(Exception e){
             
             e.printStackTrace();
         }
         
                 
          
         
         
     }
    
     
     public static void numeroCompras(XQConnection con ,String dni) throws XQException{
         
         
        XQExpression expr = con.createExpression();

        XQSequence num = expr.executeQuery("for $num in count(doc('/db/pruebas/compras.xml')//producto/cliente[@DNI='" + dni + "']) return $num\n");
       
        
         XQSequence cont = expr.executeQuery("for $num in count(doc('/db/pruebas/clientes.xml')//cliente[@DNI='" + dni + "']) return $num");
       
        while ( cont.next()){
            
            if(cont.getInt() == 0){
                 
                
                 System.out.println("El cliente con DNI = " + dni +" no se encuentra en la base de datos");
             }else{
                
                 while (num.next()) {  
             
             System.out.println("Compras --> " + num.getItemAsString(null));
             
      }  
                 
                
            }
        }
        
     }
}

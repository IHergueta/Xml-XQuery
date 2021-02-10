/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xquery;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import static xquery.Ejercicio2.nuevoCD;


/**
 *
 * @author Ignacio
 */
public class Ejercicio1 {
    
    
      public static void main(String[] args) throws InstantiationException, ClassNotFoundException, IllegalAccessException, XMLDBException{
        // TODO code application logic here
        
         

          Collection col = obtenColeccion("pruebas");
          String [] aa = col.listResources();
          
          for(int i =0;i<aa.length;i++){
              
              System.out.println(aa[i]);
          }
          
          
          System.out.println("-----------------------");
          try{
             
             crearCopiaSeguridad(col);
             
         }catch(Exception e){
             
             e.printStackTrace();
         }
          
                  
          
      

        
       
    }

     public static Collection obtenColeccion(String nomCol ) throws InstantiationException, ClassNotFoundException, IllegalAccessException, XMLDBException {
        
        String driver = "org.exist.xmldb.DatabaseImpl"; // driver para eXist
        Class c1 = Class.forName(driver); // Cargamos el driver
        Database database = (Database) c1.newInstance(); // Instancia de la BD
        DatabaseManager.registerDatabase((org.xmldb.api.base.Database) database); // Registro del driver  
        
        String uri = "xmldb:exist://localhost:8090/exist/xmlrpc/db/"+nomCol; // Colec.
        String usu = "admin"; // usuario
        String pass = "admin"; // contraseña
        Collection col = (Collection) DatabaseManager.getCollection(uri,usu,pass);
        
        return col;
        
        
    }
     
     public static void crearCopiaSeguridad(Collection col) throws XMLDBException, InstantiationException, ClassNotFoundException, IllegalAccessException{
         
          Collection copia= obtenColeccion("copia_seguridad");
          Collection elpapa = col.getParentCollection();
          
          String [] resources = col.listResources();
          
          
          if(copia==null){
              
                //obtengo el servicio para crear
                CollectionManagementService mgtService = (CollectionManagementService)elpapa.getService("CollectionManagementService","1.0");

                //la creo
                mgtService.createCollection("copia_seguridad");

                System.out.println("copia_segurida creada");
                
          }else{
              
              
          }
       
          
          for(int i =0;i<resources.length;i++){

              
                String reg ="(^.+)\\.xml";
                Pattern patron = Pattern.compile(reg);
                Matcher mat = patron.matcher(resources[i]);
                mat.find();
                
               
                
                //tiempo
                long millis=System.currentTimeMillis();  
                java.sql.Date date=new java.sql.Date(millis);  

                Resource nuevoRecurso  =  copia.createResource("COPIA_"+ mat.group(1)+"_"+ date +".xml",XMLResource.RESOURCE_TYPE);

                nuevoRecurso.setContent(col.getResource(resources[i]).getContent());
                copia.storeResource(nuevoRecurso);

                  
              }
              
          
         
         
         
          }
         
     }

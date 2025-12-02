package ppt.practica4;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*******************************************************************************
 * Protocolos de Transporte Grado en Ingeniería Telemática
 * Departamento de Ingeniería de Telecomunicación
 * Univerisdad de Jaén
 *
 *******************************************************************************
 * Práctica 4. Implementación de un servidor socket TCP para un protocolo
 *             de aplicación estándar en Java
 * Fichero: HttpConnection.java
 * Versión: 2.0
 * Fecha: 11/2025
 * Descripción: Clase sencilla de atención al protocolo HTTP/1.1
 * Autor: Juan Carlos Cuevas Martínez
 *
 *******************************************************************************
 * Alumno 1:
 * Alumno 2:
 *******************************************************************************
 */

/**
 * Clase que implementa la interfaz Runnable para ser ejecutada en una hebra que
 * gestiones una conexión entrante el un servidor HTTP/1.1
 * Esta clase solo está previto que implemente el comando GET
 * @author jccuevas
 */
public class HttpConnection implements Runnable {
    
    //Parámetros globales del servicio
    public static final String DOCUMENT_ROOT="./www/";
    public static final String DEFAULT_DOCUMENT="/index.html";
    
    public static final int HTTP_1_1_REQUEST_PARAMETERS = 0;//CAMBIAR
    public static final String CRLF = "\r\n";//Fin de línea

    private Socket socket = null;
    
    
/**
 * Esta es una clase que gestiona el protocolo HTTP/1.1
 * @param s Socket conectado con el cliente
 */
    public HttpConnection(Socket s) {
        socket = s;
    }
    
    /**
     * Otro método inútil
     * @param s Un parámetro de cadene
     * @param p Un número
     * @return Otro número
     */
    public int action(String s, int p){
    return 0;}

    /**
     * 
     */
    @Override
    public void run() {
        String path=DEFAULT_DOCUMENT;
        DataOutputStream dos = null;
        try {
            System.out.println("Incoming HTTP connection with " + socket.getInetAddress().toString());
            dos = new DataOutputStream(socket.getOutputStream());

            BufferedReader bis = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = bis.readLine();//Se obtiene la línea de petición/request line
            //Request line: GET /index.html HTTP/1.1CRLF
            String request_line = line;//Se guarda para mostrarla más tarde
            String host_field="";//Para guardar la cabecera Host
            
            
           System.out.println("Leido[" + request_line.length() + "]: " + request_line);
            String [] parts = line.split(" ");
            
            if(parts.length==3){
                
                //Comparar antes de todo incluso la versión HTTP/1.0 o HTTP/1.1
                   if(parts[0].equalsIgnoreCase("GET"))
                   {  
                       
            
                    if(line!=null){
                        System.out.println("Leido[" + line.length() + "]: " + line);
                        path = parts[1];// "/web/action/g.html"
                        //Se leen todas las cabeceras hasta la línea en blanco o fin
                        //del buffer
                        while (!(line = bis.readLine()).equals("") && line != null) {
                            System.out.println("Leido[" + line.length() + "]: " + line);

                            //TAREA 2 Buscamos la cabecera HOST
                            //nombre:valor
                            //String header[] = line.split(":")
                            // String.startsWith
                            // String.endsWith

                        }

                        //TAREA 2. Analiazar la línea de petición, MÉTODO SP RUTA SP VERSIÓN
                        //TAREA 2. Extraer el método
                        //TAREA 2. Extraer la ruta y recurso
                        if(path.equals("/")){
                            path = DEFAULT_DOCUMENT;
                        }

                       
                        String file_path = DOCUMENT_ROOT+host_field+path;
                        System.out.println("ruta el recurso:" + file_path);
                       
                        String contentType = getType(path);
                        byte[] data = getDataEx(file_path);
                        
                        
                        
                            // Esto se debe eliminar solo está para responder al cliente
                        // con una respuesta básica para comprobar conectividad
                        dos.write(("HTTP/1.1 200 OK"+CRLF).getBytes());
                        dos.write(("Connection: close"+CRLF).getBytes());
                        dos.write(("Content-Type: text/html"+CRLF).getBytes());
                        dos.write(("Content-Length: "+data.length+CRLF).getBytes());
                        dos.write((CRLF).getBytes());
                        dos.write(data);
                        dos.flush();
                        //Eliminar hasta aquí
                       //envío los datos
                        
                            
                        
                       
                    }
                }else{
                       //Método no permitido
                   }
            }else{
                //Error de formato de la petición
                System.out.println("Error 400");
                dos.write(("HTTP/1.1 400 OK"+CRLF).getBytes());
                dos.write(("Connection: close"+CRLF).getBytes());
                dos.write((CRLF).getBytes());
                dos.flush();
            }
        } 
        catch (FileNotFoundException exNF){
            System.out.println("Error 404");
            try {
                dos.write(("HTTP/1.1 404 Not found"+CRLF).getBytes());
                   dos.write(("Connection: close"+CRLF).getBytes());
                            dos.write((CRLF).getBytes());
                            dos.flush();
            } catch (IOException ex) {
                System.getLogger(HttpConnection.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
                         
        }
        
        catch (IOException ex) {
            Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dos.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private String getType(String path) {
       return "text/html";
    }

    private byte[] getData(String file_path) {
        FileInputStream fis = null;
        try {
            File f = new File(file_path);
            fis = new FileInputStream(f);
            return null;
        } catch (FileNotFoundException ex) {
            System.getLogger(HttpConnection.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return null;
        } catch(IOException ex2){
             System.getLogger(HttpConnection.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex2);
            return null;
        }
        finally {
            try {
                fis.close();
            } catch (IOException ex) {
                System.getLogger(HttpConnection.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    private byte[] getDataEx(String file_path) throws FileNotFoundException {
        FileInputStream fis = null;
        
            File f = new File(file_path);
            fis = new FileInputStream(f);
           
        
            try{
                fis.close();
            } catch (IOException ex) {
                System.getLogger(HttpConnection.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            return null;
        }

    

}

package tienda;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import tiendasax.*;

public class servidor extends conexion //Se hereda de conexion para hacer uso de los sockets y dem�s
{
    TiendaSAX sax = new TiendaSAX();

    public servidor() throws IOException {
        super("servidor");
    } //Se usa el constructor para servidor de Conexion

    public void startServer()//Metodo para iniciar el servidor
    {
        try {
            System.out.println("Esperando..."); //Esperando conexion

            cs = ss.accept(); //Accept comienza el socket y espera una conexion desde un cliente

            System.out.println("Cliente en linea");

            //Se obtiene el flujo de salida del cliente para enviarle mensajes
            salidaCliente = new DataOutputStream(cs.getOutputStream());

            //Se le envia un mensaje al cliente usando su flujo de salida
            salidaCliente.writeUTF("Peticion recibida y aceptada");

            //Se obtiene el flujo entrante desde el cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));

//            while((mensajeServidor = entrada.readLine()) != null) //Mientras haya mensajes desde el cliente
//            {
//                //Se muestra por pantalla el mensaje recibido
//                System.out.println(mensajeServidor);
//            }
            
//            File file = new File("test1.txt");
//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write(entrada);
//            fileWriter.flush();
//            fileWriter.close();

            System.out.println("Fin de la conexion");

            ss.close();//Se finaliza la conexion con el cliente
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        /*DataInputStream input;
    	 BufferedInputStream bis;
    	 BufferedOutputStream bos;
    	 int in;
    	 byte[] byteArray;
    	 
    	//crear dom 
    	 TiendaDOM dom = new TiendaDOM();
    	 dom.generadorDom.generarDocument("10.0.1.1", 80, "tienda", "10.0.1.2", 80, "cliente", "aviso", "valido");
    	 try {
			dom.generadorDom.generarXML();
		} catch (TransformerConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
    	 //Fichero a transferir
    	 final String filename = "c:\\test.pdf";
    	 
    	try{
    	 final File localFile = new File( filename );
    	 Socket clicli = new Socket("localhost", 5000);
    	 bis = new BufferedInputStream(new FileInputStream(localFile));
    	 bos = new BufferedOutputStream(clicli.getOutputStream());
    	 //Enviamos el nombre del fichero
    	 DataOutputStream dos=new DataOutputStream(clicli.getOutputStream());
    	 dos.writeUTF(localFile.getName());
    	 //Enviamos el fichero
    	 byteArray = new byte[8192];
    	 while ((in = bis.read(byteArray)) != -1){
    	 bos.write(byteArray,0,in);
    	 }
    	 
    	bis.close();
    	 bos.close();
    	 
    	}catch ( Exception e ) {
    	 System.err.println(e);
    	 }*/
    }
}

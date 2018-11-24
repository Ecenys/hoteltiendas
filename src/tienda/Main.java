package tienda;
import java.io.IOException;



//Clase principal que har� uso del servidor
public class Main
{
    public static void main(String[] args) throws IOException
    {
        servidor serv = new servidor(); //Se crea el servidor
        System.out.println("Iniciando servidor\n");
        serv.startServer(); //Se inicia el servidor
    }
}
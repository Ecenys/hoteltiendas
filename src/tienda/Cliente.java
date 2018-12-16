package tienda;

import Servidor.Main;
import java.util.ArrayList;

/**
 *
 * @author Oskar-C97
 */
public class Cliente {
    int puerto, id;
    String ip;
    int tiendaactual=0; //Sirve para identificar en que tienda esta un cliente
    ArrayList<Integer> listaIDTiendas = new ArrayList<Integer>(); //Guardamos solo los ID de las tiendas, ya que el resto de datos de una los podemos averiguar a partir de su id.
    //ArrayList<Tienda> listaTiendas = new ArrayList<Tienda>();

    public Cliente(String ip, int puerto, int id, ArrayList<Integer> listaIDTiendas ) {
        this.puerto = puerto;
        this.id = id;
        this.ip = ip;
        this.listaIDTiendas = listaIDTiendas;
    }

    public int getPuerto() {
        return puerto;
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public ArrayList<Integer> getListaIDTiendas() {
        return listaIDTiendas;
    }

    //Metodo get de tienda, ya que solo almacenamos el ID de estas, tenemos que crear el cuerpo del mensaje para una tienda completa.
    //Al ser esta la direcion y el ip del servidor mas el id de la tienda, los colocamos en un String en orden y lo devolvemos.
    public String getXMLListaTiendas() {
        String tiendas = "<listaTiendas>";
        for (int idtienda: listaIDTiendas) {
            tiendas += "<tienda><ip>"+Main.getIplocal()+"</ip><puerto>"+Main.getPort()+"</puerto><id>"+idtienda+"</id></tienda>";
        }
        tiendas+="</listaTiendas>";
        return tiendas;
    }

    public int getTiendaactual() {
        return tiendaactual;
    }

    public void setTiendaactual(int tiendaactual) {
        this.tiendaactual = tiendaactual;
    }
    
    
}

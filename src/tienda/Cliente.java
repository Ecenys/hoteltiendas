package tienda;

import java.util.ArrayList;

/**
 *
 * @author Oskar-C97
 */
public class Cliente {
    int puerto, id;
    String ip;
    int tiendaactual=0;
    ArrayList<Integer> listaIDTiendas = new ArrayList<Integer>();
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

    public String getListaTiendas() {
        String tiendas = "<listaTiendas>";
        for (int idtienda: listaIDTiendas) {
            tiendas += "<tienda><ip>10.0.69.175</ip><puerto>80</puerto><id>"+idtienda+"</id></tienda>";
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

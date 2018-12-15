package tienda;

import Servidor.SendPOST;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import tiendadom.GeneradorDOM;

public class Tienda {

    private int id;
    private ArrayList<Producto> almacen, error;
    private boolean encontrado;
    private String resultado = "";

    public String Comprar(ArrayList<Producto> listaProductos) {
        for (Producto p : listaProductos) { //Recorre todos los productos de la lista del cliente
            encontrado = false;
            for (Producto a : almacen) {    //Recorre todos los productos del almacen
                if (a.getNombre().equals(p.getNombre())) {  //Si el nombre del producto coincide con producto de almacen
                    if (a.getCantidad() >= p.getCantidad()) {
                        //Si hay cantidad suficiente, retira del almacen y coloca la cantidad restante
                        a.setCantidad(a.getCantidad() - p.getCantidad());
                        resultado += "<producto><nombre>"+p.getNombre()+"</nombre><cantidad>"+p.getCantidad()+"</cantidad></producto>";
                        encontrado = true;
                        break; //Si encuentra el producto en el almacen, pasa a comprobar el siguiente articulo
                    } else {
                        //Si no, coge los que queden, y notifica cuantos le quedan por comprar al cliente
//                        mensaje += " - Producto: "+p.getNombre()+" Cantidad: "+(p.getCantidad() - a.getCantidad());
                        p.setCantidad(p.getCantidad() - a.getCantidad());
                        resultado += "<producto><nombre>"+p.getNombre()+"</nombre><cantidad>"+a.getCantidad()+"</cantidad></producto>";
                        a.setCantidad(0);
                        encontrado = true;
                        break; //Si encuentra el producto en el almacen, pasa a comprobar el siguiente articulo
                    }
                }
            }
            if (!encontrado) {
                    //Si el producto no se encuentra en almacen, lo notifica como articulo no procesado
//                    mensaje += " - Producto: "+p.getNombre()+" Cantidad: "+p.getCantidad();
                    resultado += "<producto><nombre>"+p.getNombre()+"</nombre><cantidad>"+0+"</cantidad></producto>";
                }
        }

       return resultado;
    }
    
    public void Comunica(GeneradorDOM generadorDom, String ip, int puerto, String rol, int iddestino, String tipo, String cuerpo) throws IOException, ParserConfigurationException, TransformerException{
        SendPOST post = new SendPOST();
        post.GeneraDOM(generadorDom, id, tipo, iddestino, iddestino, tipo, tipo, cuerpo);
    }

    //Gets y Sets
    public void setId(int id) {
        this.id = id;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        almacen = listaProductos;
    }

    public int getId() {
        return id;
    }

    public void setAlmacen(ArrayList<Producto> almacen) {
        this.almacen = almacen;
    }

}
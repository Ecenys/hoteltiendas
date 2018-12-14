package tienda;

import Servidor.SendPOST;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Tienda {

    private int id;
    private ArrayList<Producto> almacen, error;
    private boolean encontrado;
    // private String mensaje = new String();

    public void Comprar(ArrayList<Producto> listaProductos) {
        for (Producto p : listaProductos) { //Recorre todos los productos de la lista del cliente
            encontrado = false;
            for (Producto a : almacen) {    //Recorre todos los productos del almacen
                if (a.getNombre().equals(p.getNombre())) {  //Si el nombre del producto coincide con producto de almacen
                    if (a.getCantidad() >= p.getCantidad()) {
                        //Si hay cantidad suficiente, retira del almacen y coloca la cantidad restante
                        a.setCantidad(a.getCantidad() - p.getCantidad());
                        encontrado = true;
                        break; //Si encuentra el producto en el almacen, pasa a comprobar el siguiente articulo
                    } else {
                        //Si no, coge los que queden, y notifica cuantos le quedan por comprar al cliente
//                        mensaje += " - Producto: "+p.getNombre()+" Cantidad: "+(p.getCantidad() - a.getCantidad());
                        p.setCantidad(p.getCantidad() - a.getCantidad());
                        error.add(p);
                        a.setCantidad(0);
                        encontrado = true;
                        break; //Si encuentra el producto en el almacen, pasa a comprobar el siguiente articulo
                    }
                }
            }
            if (!encontrado) {
                    //Si el producto no se encuentra en almacen, lo notifica como articulo no procesado
//                    mensaje += " - Producto: "+p.getNombre()+" Cantidad: "+p.getCantidad();
                    error.add(p);
                }
        }

        // Mandar mensaje POST de error o completado
    }
    
    public void Comunica(String ip, int puerto, String rol, int iddestino, String tipo, String cuerpo) throws IOException, ParserConfigurationException, TransformerException{
        SendPOST post = new SendPOST();
        post.sendPOST(id,ip,puerto,iddestino,rol,tipo,cuerpo);
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
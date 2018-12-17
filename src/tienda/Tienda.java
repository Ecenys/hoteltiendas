package tienda;

import Servidor.SendPOST;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import tiendadom.GeneradorDOM;

/**
 * -Clase Tienda- Clase encargada de simular una tienda. Por lo tanto contiene
 * metodos para simular la compra y para comunicar al cliente
 *
 * @author Oskar-C97
 */
public class Tienda {

    private int id;
    private ArrayList<Producto> almacen, error;
    private boolean encontrado;
    private String resultado = "";

    //constructor
    public Tienda() {
    }

    /**
     * -Metodo Comprar- Simula las acciones necesarias para que el cliente
     * compre. Para ello se le pasan los productos y las cantidades y la tienda
     * devuelve cuanta cantidad ha sido capaz de vender al cliente
     *
     * @param listaProductos
     * @return
     */
    public String Comprar(ArrayList<Producto> listaProductos) {
        for (Producto p : listaProductos) { //Recorre todos los productos de la lista del cliente
            encontrado = false;
            for (Producto a : almacen) {    //Recorre todos los productos del almacen
                if (a.getNombre().equals(p.getNombre())) {  //Si el nombre del producto coincide con producto de almacen
                    if (a.getCantidad() >= p.getCantidad()) {
                        //Si hay cantidad suficiente, retira del almacen y coloca la cantidad restante
                        a.setCantidad(a.getCantidad() - p.getCantidad());
                        resultado += "<producto><nombre>" + p.getNombre() + "</nombre><cantidad>" + p.getCantidad() + "</cantidad></producto>";
                        encontrado = true;
                        break; //Si encuentra el producto en el almacen, pasa a comprobar el siguiente articulo
                    } else {
                        //Si no, coge los que queden, y notifica cuantos le quedan por comprar al cliente
//                        mensaje += " - Producto: "+p.getNombre()+" Cantidad: "+(p.getCantidad() - a.getCantidad());
                        p.setCantidad(p.getCantidad() - a.getCantidad());
                        resultado += "<producto><nombre>" + p.getNombre() + "</nombre><cantidad>" + a.getCantidad() + "</cantidad></producto>";
                        a.setCantidad(0);
                        encontrado = true;
                        break; //Si encuentra el producto en el almacen, pasa a comprobar el siguiente articulo
                    }
                }
            }
            if (!encontrado) {
                //Si el producto no se encuentra en almacen, lo notifica como articulo no procesado
//                    mensaje += " - Producto: "+p.getNombre()+" Cantidad: "+p.getCantidad();
                resultado += "<producto><nombre>" + p.getNombre() + "</nombre><cantidad>" + 0 + "</cantidad></producto>";
            }
        }

        return resultado;
    }

    /**
     * -Metodo Comunica- Encargado de generar el documento de respuesta para el
     * cliente, este metodo puede ser llamado directamente, pero el envio ha de
     * realizarse manualmente por el programador, ya que no envia el documento
     *
     * @param generadorDom
     * @param ipdestino
     * @param puertodestino
     * @param roldestino
     * @param iddestino
     * @param tipo
     * @param cuerpo
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public void Comunica(GeneradorDOM generadorDom, String ip, int puerto, String rol, int iddestino, String tipo, String cuerpo) throws IOException, ParserConfigurationException, TransformerException {
        SendPOST post = new SendPOST();
        post.GeneraDOM(generadorDom, id, ip, puerto, iddestino, rol, tipo, cuerpo);
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

package tienda;
import java.awt.List;
import java.util.ArrayList;

public class tienda {

	public tienda() {}
		// TODO Auto-generated constructor stub
	int id;
	int [] Productos;
	String [] Clientes;
	private int cuantos=0;
	ArrayList<Integer> ListaPersonas = new ArrayList<Integer>();
	//ArrayList<Integer> miArrayList = new ArrayList<Integer>();

	public tienda (int ida, int [] productos)
	{
	
	this.id =ida;
	this.Productos = productos;
	
	}
	
	public void anadeCliente(String idclien, int idborra)
	{
		this.ListaPersonas.add(idborra);
		this.Clientes[this.cuantos]=idclien;
		this.cuantos=cuantos+1;
		//this.Clien.add(idclien);
	}
	
	public void borraCliente(String idclien, int idborra)
	{
		this.ListaPersonas.remove(idborra);
		
		for (int i = 0; i==this.cuantos; ++i) {
		if(Clientes[i]==idclien) 
			this.Clientes[i]="null";
			
		}
		
	}
		
	

}

/*
Clase que mapea la instancia Empeno


*/

package domain;

import java.time.LocalDate;

public class Empeno  {
	

	private int empenoId;
	private LocalDate fechaEmpeno ;
	private Articulo articulo;
	private Prestamo prestamo;
	private int clienteId;


	public Empeno (LocalDate fecha, int ci, Prestamo pre, Articulo ar){

			this.fechaEmpeno=fecha;
			clienteId = ci;
			this.prestamo = pre;
			this.articulo = ar;
	}

	public Empeno (int ci,int ei, LocalDate fecha, Prestamo pre, Articulo ar){

			this.empenoId =ei;
			this. fechaEmpeno=fecha;
			this. prestamo = pre;
			this.articulo = ar;
			this.clienteId = ci;
	}

	public int getEmpenoId (){


		return empenoId;
	}

	public LocalDate getFechaEmpeno (){


		return fechaEmpeno;
	}

	public int getClienteId (){


		return clienteId;
	}	

	public Articulo getArticulo (){

		return articulo;

	}

	public Prestamo getPrestamo (){


		return prestamo;
	}
}


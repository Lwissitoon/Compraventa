/*
Clase que controla el Registro de un Empeno



*/

package domain;

import domain.Articulo;
import domain.Empeno;
import domain.Prestamo;
import dataAccess.EmpenoDA;


public class RegistrarEmpenoController{
		

	private  Articulo articulo;
	private  Prestamo prestamo;
	private  Empeno empeno;
	private EmpenoDA empenoDA;
	private int resultado = 0;

	public  int  registrarEmpeno (Empeno em){


		empenoDA = new EmpenoDA();
		resultado = empenoDA.registrarEmpeno(em);


		return resultado;

	}


}
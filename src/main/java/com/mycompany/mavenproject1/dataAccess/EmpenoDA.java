/*

Clase que maneja el acceso a base de datos 
para registrar un empeno



*/

package dataAccess;

import domain.Articulo;
import domain.Empeno;
import domain.Prestamo;


import java.sql.*;

public class EmpenoDA{
	
	private Articulo articulo;
	private Prestamo prestamo;
	private Empeno empeno;
	private Date fechaSQL ;
	private ResultSet rs ;
	private PreparedStatement stat;
	private int prestamoId, articuloId;

	public  int registrarEmpeno (Empeno em){

		String sentenciaSQL;
		Object [] parametrosArticulo;
		Object [] parametrosPrestamo;
		Object [] parametrosEmpeno;

		empeno = em;

		articulo = empeno.getArticulo();
		prestamo = empeno.getPrestamo();

//Insercion en la tabla Articulos

		sentenciaSQL = "insert into Articulos (tipoDeArticulo, NumeroDeSerie, Marca, Modelo, estado, ValorDeEmpeno, Descripcion) values (?,?,?,?,?,?,?)";
		parametrosArticulo = new Object [] {articulo.getTipoDeArticulo(), articulo.getNumeroDeSerie(), articulo.getMarca(), articulo.getModelo(),"Empenado", articulo.getValorDeEmpeno(), articulo.getDescripcion()};

		try{


			Db.ejecutarQueryPrepared(sentenciaSQL,parametrosArticulo);

		}


		catch (SQLException e){

			e.printStackTrace();

		}

		catch (Exception e){

			e.printStackTrace();

		}




		//Obtener el Generated Keys hecho por la insercion
		// en la tabla Articulos, para obtener el primary key
		// de la tupla insertada y asi hacer una insercion en la tabla
		// Empenos	

		rs = Db.getGeneratedKeys();

		try 

			{
				 while (rs.next()) {

		      	articuloId =rs.getInt(1);

		      	System.out.println("Key de articulo "+ articuloId);

		      }
		    		

		}
		
		catch (Exception e) {


			e.printStackTrace();
		}


//Insercion en la tabla Prestamos

		sentenciaSQL = "insert into Prestamos (montoPrestado, fechaDesembolso) values (?,?)";
		fechaSQL = Date.valueOf(prestamo.getFechaDesembolso());


		parametrosArticulo = new Object [] {prestamo.getMontoPrestado(), fechaSQL};

		try{


			Db.ejecutarQueryPrepared(sentenciaSQL,parametrosArticulo);

		}


		catch (SQLException e){

			e.printStackTrace();

		}

		catch (Exception e){

			e.printStackTrace();

		}
		



		//Obtener el Generated Keys hecho por la insercion
		// en la tabla Prestamos, para obtener el primary key
		// de la tupla insertada y asi hacer una insercion en la tabla
		// Empenos	

	
				rs = Db.getGeneratedKeys();

		try 

		{
		    while (rs.next()) 
		    {

		    		prestamoId =rs.getInt(1) ;
		    		
		     		   System.out.println("Imprime Key : "+prestamoId);
		            
		    }		

		}
		
		catch (Exception e) {


			e.printStackTrace();
		}


		//Insercion en la tabla Emepeno


		sentenciaSQL = "insert into Empenos (fechaEmpeno, clienteId, articuloId, prestamoId) values (?,?,?,?)";
		fechaSQL = Date.valueOf(prestamo.getFechaDesembolso());


		parametrosArticulo = new Object [] {fechaSQL,empeno.getClienteId(), articuloId, prestamoId};

		try{


		 Db.ejecutarQueryPrepared(sentenciaSQL,parametrosArticulo);

		}


		catch (SQLException e){

			e.printStackTrace();

		}

		catch (Exception e){

			e.printStackTrace();

		}


		rs = Db.getGeneratedKeys();

//devuelve  1 si se ha insertado en la tabla Empenos

		try{

			if(rs.next())	{

				int resultado; 

				resultado = rs.getInt(1);
			

				System.out.println("El Key del empeno es : "+ resultado);

				return resultado;

			}

		}
		
		catch (Exception e){

			e.printStackTrace();

		}
		


//Devuelve 0  si no se hizo insercion en la tabla empeno
	 	return 0;

	}



}
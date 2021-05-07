/*



BuscarArticulo GUI

Vista de la Funcionalidad Buscar Articulo

*/

package view;

import com.mycompany.mavenproject1.view.BuscarArticuloGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import domain.ControladorSesion;
import domain.BuscarArticuloController;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyListener;




class BuscarArticuloGUI extends JFrame implements ActionListener{
	
	private DefaultTableModel aModel;
	private BuscarArticuloController buController ;
	private ResultSet rs;
	private JTable tabla;
	private JPanel panelFiltro;
	
	private JButton verDetallesBoton;
	private JButton volverAtrasBoton;
	private JButton registrarPagoBoton;
	private JButton registrarVentaBoton;
	private JDialog registrarPagoVentana;
	private JTextField tableFilterText;
	private JLabel tableFilterLabel;
	private JRadioButton todosLosArticulosRadio;
	private JRadioButton articulosEmpenadosRadio;
	private JRadioButton articulosPerdidosRadio;
	private JRadioButton articulosEnVentaRadio;
	private JRadioButton articulosVendidosRadio;
	private ButtonGroup articulosRadioGroup;

	
	public BuscarArticuloGUI (){


	  setTitle("BuscarArticulo");
	  setSize(600,600);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  setLocationRelativeTo(null);  
	  setResizable(true) ;
	  setLayout(new FlowLayout());

	  
	  verDetallesBoton = new JButton ("Ver Detalles Art.");
	  registrarPagoBoton = new JButton ("Registrar Pago");
	  registrarVentaBoton = new JButton ("Registrar Venta");
	  volverAtrasBoton = new JButton("Volver Atras");
	 


	  registrarPagoBoton.setEnabled(false);
	  registrarPagoBoton.addActionListener(new RegistrarPagoListener());


	  registrarVentaBoton.setEnabled(false);	  
	  registrarVentaBoton.addActionListener(new RegistrarVentaListener());

	  volverAtrasBoton.addActionListener(this);

	  add(verDetallesBoton);
	  add(registrarPagoBoton);
	  add(registrarVentaBoton);
	  add(volverAtrasBoton);
	  add(addPanelFiltro());



	  verDetallesBoton.addActionListener(this);
	  tableFilterText.addKeyListener(new FiltrarTablaListener());

		try{

			
			buController = new BuscarArticuloController();

			rs = buController.getArticulos();

			String [] nombreColumnasTablas = {"articuloID","numeroDeSerie","tipoDeArticulo","estado","marca","modelo","valorDeEmpeno","precioDeVenta","descripcion"};
		 

			aModel = new DefaultTableModel();
			aModel.setColumnIdentifiers(nombreColumnasTablas);
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();



			int columnCount = getColumnCount(rs);



			Object [] data = new Object [columnCount] ;


			int i =0;

			while (rs.next()) {

				System.out.println("Un Articulo");

				int j = 0;


				

				data[j++] = rs.getInt("articuloID");
				data[j++] = rs.getString("numeroDeSerie");
				data[j++] = rs.getString("tipoDeArticulo");
				data[j++] = rs.getString("estado");
				data[j++] = rs.getString("marca");
				data[j++] = rs.getString("modelo");
				data[j++] = rs.getDouble("valorDeEmpeno");
				data[j++] = rs.getDouble("precioDeVenta");
				data[j++] = rs.getString("descripcion");					


				aModel.addRow(data);

			}		

			 tabla = new JTable(aModel);

///////////// Anadiendo el listener 
			 // Y configurando la tabla para que nos permite escuchar
			 // cuando seleccionemos una fila


			 ///Si la fila que seleccionamos, el articulo esta empenado o perdido
			 /// nos permite selecionar el boton Registrar pago o Registrar Venta respectivamente

			  ListSelectionModel modeloLista = tabla.getSelectionModel();
			  modeloLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			  modeloLista.addListSelectionListener(new ListSelectionListener() {

			      public void valueChanged(ListSelectionEvent e) {
			        String estadoArticulo = null;

			        int selectedRow = tabla.getSelectedRow();
			        
			          for (int j = 0; j < columnCount; j++) {
			            estadoArticulo = (String) tabla.getValueAt(selectedRow, 3);
			          }

			          if (estadoArticulo.equals("Empenado")||estadoArticulo.equals ("empenado")){

			          	registrarPagoBoton.setEnabled(true);
			          	registrarVentaBoton.setEnabled(false);
			          }

			          else if (estadoArticulo.equals ("Perdido")||estadoArticulo.equals("perdido")){

			          	registrarPagoBoton.setEnabled(false);
			          	registrarVentaBoton.setEnabled(true);

			          }



			         else if (estadoArticulo.equals ("")||estadoArticulo.equals("")){

			          	registrarPagoBoton.setEnabled(false);
			          	registrarVentaBoton.setEnabled(true);

			         }
			         			          

			          else {


			          	registrarPagoBoton.setEnabled(false);
						registrarVentaBoton.setEnabled(false);

			          }
			       
			        System.out.println("Imprime Seleccion: " + estadoArticulo);
			      }

    });			 


			  tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			  tabla.setColumnSelectionAllowed(false);
			  tabla.setCellSelectionEnabled(false);
			  tabla.setRowSelectionAllowed(true);
			  tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			 JScrollPane scrollablePane = new JScrollPane (tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


			 JPanel panelTabla = new JPanel(new BorderLayout());


			 panelTabla.add(tabla.getTableHeader(), BorderLayout.NORTH);

			 panelTabla.add(scrollablePane, BorderLayout.CENTER);		

			 add( panelTabla );

		}

			

		

		catch (SQLException e){

			e.printStackTrace();

		}		

		catch (Exception e){

			e.printStackTrace();

		}

		
	


	}
	
	
	public void actionPerformed(ActionEvent ae){


		if (ae.getSource().equals(volverAtrasBoton)){
			cerrarVentana();
		}


		if (ae.getSource().equals(registrarPagoBoton)){
			registrarPago();
		}

		if (ae.getSource ().equals(verDetallesBoton)){
			
		}

	}	
	
	

	void ocultar (){

		this.setVisible(false);

	}

	void mostrar (){


		this.setVisible(true);

	}




		


	private int getColumnCount(ResultSet rs) {

		try {

			if(rs != null)
				return rs.getMetaData().getColumnCount();

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return 0;
	}	

	//Metodo Instancia Lo elementos del Panel De Filtros 
	// y Los anade a la vista de Buscar Elementos

	
	private  JPanel addPanelFiltro (){

		panelFiltro = new JPanel();
		tableFilterLabel = new JLabel("Filtrar Articulos");
		tableFilterText= new JTextField(10);
		/*
		RadioListener rl = new RadioListener();
		articulosRadioGroup = new ButtonGroup();
		todosLosArticulosRadio = new JRadioButton("Todos");
		todosLosArticulosRadio.addItemListener(rl);
		articulosEmpenadosRadio = new JRadioButton("Empenado")	;
		articulosEmpenadosRadio.addItemListener(rl);
		articulosPerdidosRadio = new JRadioButton("Perdido");
		articulosPerdidosRadio.addItemListener(rl);
		articulosEnVentaRadio = new JRadioButton("En Venta");
		articulosEnVentaRadio.addItemListener(rl);
		articulosVendidosRadio = new JRadioButton("Vendido");		
		articulosVendidosRadio.addItemListener(rl);
		
		panelFiltro.add(todosLosArticulosRadio);
		panelFiltro.add(articulosEmpenadosRadio);
		panelFiltro.add(articulosPerdidosRadio);
		panelFiltro.add(articulosEnVentaRadio);
		panelFiltro.add(articulosVendidosRadio);

		articulosRadioGroup.add(todosLosArticulosRadio);
		articulosRadioGroup.add(articulosPerdidosRadio);
		articulosRadioGroup.add(articulosPerdidosRadio);
		articulosRadioGroup.add(articulosEnVentaRadio);
		articulosRadioGroup.add(articulosVendidosRadio);
		
	*/

	  panelFiltro.setBorder(BorderFactory.createTitledBorder("Filtros"));
	  
	  panelFiltro.add(tableFilterLabel);
	  panelFiltro.add(tableFilterText);

	  return panelFiltro;
	}


	/*
	private class RadioListener implements ItemListener{
		
		public void itemStateChanged (ItemEvent event){
			String s = ((JRadioButton) event.getItem()).getText();
			System.out.println(s);
			filtrarTablaByRadioButtons(s);
		}
	}

	// Metodo para filtrar Usando 
	// Los Radio Buttons
	private void filtrarTablaByRadioButtons(String query){
		
		
		TableRowSorter<DefaultTableModel> trs = new TableRowSorter <DefaultTableModel> (aModel);		
		tabla.setRowSorter(trs);
		
		if (query!="Todos"){
			trs.setRowFilter(RowFilter.regexFilter(query));
		}

		else {
			tabla.setRowSorter(trs);
		}
	}
	
*/
	//Este metodo pemite filtrar la tabla a traves de cualquier columna 
	// del modelo de la Tabla
	private void filtrarTabla (String query){
		TableRowSorter <DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(aModel);
		tabla.setRowSorter(tr);
		tr.setRowFilter(RowFilter.regexFilter(query));

	}

	class FiltrarTablaListener implements KeyListener{
		public void keyReleased(KeyEvent e){
				String query = tableFilterText.getText();
				filtrarTabla(query);
		}
		public void keyTyped(KeyEvent e){

		}
		
		public void keyPressed(KeyEvent e){

		}		

	}

	class RegistrarPagoListener implements ActionListener{


		public void actionPerformed (ActionEvent ae){


			registrarPago();
				
			}

	}




	class RegistrarVentaListener implements ActionListener{
		public void actionPerformed (ActionEvent ae){
				
			}
	}


	void cerrarVentana (){


		dispose();
		VentanaPrincipalGUI ventana = new VentanaPrincipalGUI ();
		ventana.setVisible(true);

	}

	void registrarPago(){


		registrarPagoVentana = new JDialog(this, "Registrar Pago");
		mostrar(false);
		registrarPagoVentana.setSize(new Dimension (500,500));
		registrarPagoVentana.setLocationRelativeTo(null);

		registrarPagoVentana.setVisible (true);
		registrarPagoVentana.addWindowListener(new WindowAdapter() {
			
		public void windowClosing(WindowEvent e) {

				mostrar(true);
				registrarPagoVentana.setVisible(false);
			}
		
			public void windowClosed(WindowEvent e) {
			
				mostrar(true);
				registrarPagoVentana.setVisible(false);
			}

		});

	}



	public void mostrar (boolean b){


		this.setVisible(b);
	}

	
	
}






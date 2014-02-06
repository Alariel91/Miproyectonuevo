package com.mony.beans;



import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ListaReproduccion extends JTable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> listaReproduccion;
	private DefaultTableModel modelo;
	
	public ListaReproduccion()
	{
		super();
		
		modelo = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int fila, int columna)
			{
				return false;
			}
		
		};
		
		listaReproduccion = new ArrayList<String>();
	}
	public void inicializar(ArrayList<String> listaReproduccion)
	{
		TableColumn columna = null;
				
		columna = new TableColumn();
			columna.setHeaderValue((String) "Lista de Reproducción" );
			columna.setPreferredWidth(10);
			columna.setResizable(false);
			addColumn(columna);
			modelo.addColumn(columna.getHeaderValue());
			
		this.setModel(modelo);
		
		this.listaReproduccion = listaReproduccion;
	}
	
	public void anadirFila(ArrayList<String> listaReproduccion)
	{
		for (int i=0; i< listaReproduccion.size(); i++)
		{
			Object[] fila = new Object[]
			{
					listaReproduccion.get(i)
			};
			modelo.addRow(fila);
		}
			
	}	
	public int seleccionado(){
			int fila=0;
			fila= getSelectedRow();
			System.out.println(fila);
		
			return fila;
	}
	
	public void listar()
	{
		vaciar();
		anadirFila(listaReproduccion);
	}
	
	public void vaciar()
	{
		modelo.setNumRows(0);
	}
}

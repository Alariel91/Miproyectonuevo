package com.mony.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 * Clase que contiene todas las acciones relacionadas con ficheros
 * escribir, leer, pasar fichero a XML.
 * @author Mony
 * @version 1.0
 */
public class Ficheros {
	public static ArrayList<String>recientes;
	/**
	 * Metodo que se utiliza para escribir todos los datos de un objeto
	 * en un fichero determinado pasandole la ruta de éste.
	 * @param objeto
	 * @param rutaFichero
	 * @throws IOException
	 */
	public static void escribirObjeto(ArrayList<String> listaReproduccion) throws IOException {
		
		 FileWriter fichero = null;
	        PrintWriter pw = null;
	        try
	        {
	            fichero = new FileWriter("listaReproduccion.txt");
	            Util.mensajeInformacion("La lista de reproduccion se ha guardado correctamente.");
	            pw = new PrintWriter(fichero);
	 
	            for (int i = 0; i < listaReproduccion.size(); i++){
	            	 pw.println(listaReproduccion.get(i));
	
	            }
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	           try {
	           if (null != fichero)
	              fichero.close();
	           } catch (Exception e2) {
	              e2.printStackTrace();
	           }
	        }
	}
	
	public static void LeerArchivo(ArrayList<String> listaReproduccion) throws IOException
	{
		recientes=new ArrayList<String>();
		//Si existe el Archivo TXT con las Descargas Completadas, lo leo
		File ficheroListaReproduccion = new File("listaReproduccion.txt");
		if (ficheroListaReproduccion.exists())
		{
			String linea;
			BufferedReader lectura = new BufferedReader(new FileReader("ListaReproduccion.txt"));
			while ((linea=lectura.readLine())!=null)
			{
				System.out.println(linea);
				recientes.add(linea);
			}
			lectura.close();
		}
		
	}
}

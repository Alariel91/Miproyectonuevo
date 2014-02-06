package com.mony.util;

import javax.swing.JOptionPane;

public class Util 
{
	public static void mensajeError(String mensaje)
	{
		JOptionPane.showMessageDialog(null, 
				mensaje,
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}
	

	public static void mensajeError(String titulo, String mensaje)
	{
		JOptionPane.showMessageDialog(null, 
				mensaje,
				titulo,
				JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mensajeInformacion(String mensaje)
	{
		JOptionPane.showMessageDialog(null, 
			mensaje,
			"Información",
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void mensajeInformacion(String titulo, String mensaje)
	{
		JOptionPane.showMessageDialog(null, 
			mensaje,
			titulo,
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static int mensajeConfirmacion(String mensaje)
	{
		return JOptionPane.showConfirmDialog(null, 
					mensaje,
					"Confirmacion",
					JOptionPane.YES_NO_OPTION);
	}
	
}

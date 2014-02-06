package com.mony.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VideoExterno extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfUrl;
	private JLabel lblNewLabel;
	private String url;
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public enum AccionDialogo{
		ACEPTAR,CANCELAR
	};
	private AccionDialogo accion;
	
	public AccionDialogo mostrarDialogo(){
		this.setVisible(true);
		return accion;
	}
	
	private void aceptar(){
		url=tfUrl.getText();

		accion=AccionDialogo.ACEPTAR;
		this.setVisible(false);
		
	}
	private void cancelar(){
		this.setVisible(false);
		accion=AccionDialogo.CANCELAR;
	}

	/**
	 * Create the dialog.
	 */
	public VideoExterno() {
		this.setModal(true);
		setBounds(100, 100, 387, 128);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		tfUrl = new JTextField();
		tfUrl.setBounds(20, 26, 341, 20);
		contentPanel.add(tfUrl);
		tfUrl.setColumns(10);
		
		lblNewLabel = new JLabel("Url:");
		lblNewLabel.setBounds(20, 11, 46, 14);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						aceptar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelar();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

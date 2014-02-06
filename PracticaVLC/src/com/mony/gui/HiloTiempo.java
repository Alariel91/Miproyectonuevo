package com.mony.gui;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingWorker;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class HiloTiempo extends SwingWorker <Void,Integer> {
	private int tamano;
	private int tiempo;
	private JSlider barraProgreso;
	private EmbeddedMediaPlayerComponent mediaPlayer;
	private JLabel tiempoTotal;
	private JLabel tiempoTranscurrido;
	
	public HiloTiempo(JSlider barraProgreso, EmbeddedMediaPlayerComponent mediaPlayer,JLabel tiempoTranscurrido,JLabel tiempoTotal){
		
		this.mediaPlayer=mediaPlayer;
		this.barraProgreso=barraProgreso;
		this.tiempoTotal=tiempoTotal;
		this.tiempoTranscurrido=tiempoTranscurrido;
	}
	@Override
	protected Void doInBackground() throws Exception {
		while(mediaPlayer.getMediaPlayer().getLength()==0){
			Thread.sleep(100);
		}
		this.tamano= (int) mediaPlayer.getMediaPlayer().getLength();
		barraProgreso.setMaximum(tamano);
		int segundostotal = tamano / 1000;
		int minutostotal = segundostotal / 60;
		segundostotal = segundostotal - (minutostotal*60);
		
		this.tiempoTotal.setText(String.valueOf(minutostotal+ ":" + segundostotal));
		
		
		  for(int i=0; i<=this.tamano; i++){
			   tiempo = (int) mediaPlayer.getMediaPlayer().getTime();
			   }
		  barraProgreso.setValue(tiempo);
		  	int segundos = tiempo / 1000;
			int minutos = segundos / 60;
			segundos = segundos - (minutos*60);
		  this.tiempoTranscurrido.setText(String.valueOf(minutos +":" +segundos));
		
		return null;
	}
	


}

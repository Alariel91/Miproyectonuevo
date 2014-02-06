package com.mony.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.mrl.CdMrl;
import uk.co.caprica.vlcj.mrl.SimpleDvdMrl;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.mony.beans.ListaReproduccion;
import com.mony.gui.VideoExterno.AccionDialogo;
import com.mony.practicavlc.filter.VideoFilter;
import com.mony.util.Ficheros;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class Principal {

	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu mnArchivo;
	private JMenu mnOpciones;
	private JPanel panelBotones;
	private JButton btPlay;
	private JPanel panelLista;
	private JInternalFrame internalFrame;
	private JMenuItem mntmAbrirVideo;
	private JMenuItem mntmSalir;

	// Componente que permite gestionar los ficheros de video
	private EmbeddedMediaPlayerComponent mediaPlayer;
	
	// Ubicación de las librerías de VLC
	private static final String LIB_VLC = "C:\\Users\\MONY\\Desktop\\vlc-2.1.2-win64\\vlc-2.1.2\\";
	private enum Estado {PLAY, PAUSE, STOP};
	private Estado estado;
	private File ficheroVideo;
	private JSlider barraVolumen;
	private JLabel lbVolumen;
	private JButton btStop;
	private JSlider barraProgreso;
	private JButton btAtras;


	private JMenuItem mntmCapturaPantalla;
	private JMenu mnGeometraVideo;
	private JMenuItem menuItem;
	private JMenuItem menuItem_1;
	private JLabel lbTiempoTotal;
	private JLabel lbTiempoTranscurrido;
	private JScrollPane scrollPane;
	public ListaReproduccion  ListaReproduccion;
	
	@SuppressWarnings("unused")
	private int contador = 0;
	
	private ArrayList<String> listaReproduccion = new ArrayList<String>();
	private JButton btAdelante;
	private JLabel lbPorcentaje;
	private JButton btVerLista;
	private JButton btEsconder;
	private JButton btAgregar;
	private JButton btEliminar;
	
	private ArrayList <File> rutas=new ArrayList<File>();
	private JMenuItem mntmGuardarLista;
	private JMenuItem mntmAbrirDvd;
	private JMenuItem mnSubtitulos;
	private JMenuItem mnCD;
	private JMenuItem mnStreaming;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		inicializarVLCJ();
		initialize();
		iniciarVideo();
		ListaReproduccion.inicializar(listaReproduccion);
		
	}

	/*
	 * Reproduce un video seleccionado por el usuario
	 */
	private void abrirVideo() {
		JFileChooser jfc = new JFileChooser();
		jfc.addChoosableFileFilter(new VideoFilter());
		jfc.setAcceptAllFileFilterUsed(false);
		if (jfc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
			return;
		rutas.add(jfc.getSelectedFile());
		ficheroVideo = jfc.getSelectedFile();
		estado = Estado.STOP;
		reproducirVideo();
		
		listaReproduccion.add(ficheroVideo.getName()); 
		contador++;
		ListaReproduccion.listar();
	}
	
	/*
	 * Inicializa el componente que reproduce los videos
	 */
	private void iniciarVideo() {
	
		internalFrame.setContentPane(mediaPlayer);
		internalFrame.setVisible(true);
		
		estado = Estado.STOP;
		
	}
	
	/*
	 * Reproduce/pausa el archivo de video
	 */
	private void reproducirVideo() {
		
		// El reproductor está parado
		if (estado == Estado.STOP) {
			internalFrame.setTitle(ficheroVideo.getName());
			mediaPlayer.getMediaPlayer().playMedia(ficheroVideo.getAbsolutePath());
			estado = Estado.PLAY;
			btPlay.setText("||");
		}
		// El reproductor está pausado
		else if (estado == Estado.PAUSE) {
			mediaPlayer.getMediaPlayer().play();
			estado = Estado.PLAY;
			btPlay.setText("||");
			
		}
		// El reproductor está reproduciendo
		else {
			mediaPlayer.getMediaPlayer().pause();
			estado = Estado.PAUSE;
			btPlay.setText(">");
		}
		barraDeTiempo();
	}
		
	
	/*
	 * Inicializa la libreria VLCJ, cargando las librerías del sistema e instanciando el reproductor
	 */
	private void inicializarVLCJ() {
		
		cargaLibreria();
		mediaPlayer = new EmbeddedMediaPlayerComponent();
	}
	
	/*
	 * Carga la libreria libvlc.dll en la ruta indicada
	 * Es necesario instalar la aplicación VLC 2.X 
	 */
	
	private void cargaLibreria() {
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), LIB_VLC);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
	}
	private void cambiarVolumen(){
		mediaPlayer.getMediaPlayer().setVolume(barraVolumen.getValue());
	}

	private void esconderLista(){
		panelLista.setVisible(false);
		btEsconder.setEnabled(false);
		btVerLista.setEnabled(true);
		btAgregar.setVisible(false);
		btEliminar.setVisible(false);
	}
	private void verLista(){
		panelLista.setVisible(true);
		btEsconder.setEnabled(true);
		btVerLista.setEnabled(false);
		btAgregar.setVisible(true);
		btEliminar.setVisible(true);
	}
	private void agregar(){
		JFileChooser jfc = new JFileChooser();
		jfc.addChoosableFileFilter(new VideoFilter());
		jfc.setAcceptAllFileFilterUsed(false);
		if (jfc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
			return;
		
		rutas.add(jfc.getSelectedFile());
		ficheroVideo=jfc.getSelectedFile();
		listaReproduccion.add(ficheroVideo.getName());
		contador++;
		ListaReproduccion.anadirFila(listaReproduccion);
		ListaReproduccion.listar();
	}
	private void stop(){
		mediaPlayer.getMediaPlayer().stop();
		  estado = Estado.STOP;
		  btPlay.setText(">");
		  barraProgreso.setValue(0);
		  internalFrame.setTitle("");
	}
	
	private void eliminar(){
		rutas.clear();
		listaReproduccion.clear();
		ListaReproduccion.vaciar();
		ListaReproduccion.listar();
	}
	
	private void capturar(){
			File rutaFichero;
			String extension = ".png";
			JFileChooser fileChooser = new JFileChooser();
		
			FileNameExtensionFilter filtroDeTexto = new FileNameExtensionFilter("Archivo de Imagen (PNG)","png");
			fileChooser.setFileFilter(filtroDeTexto);
			

			if (fileChooser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION){
				return;
			}
			else{
				rutaFichero = fileChooser.getSelectedFile();
			}
			
			File captura = new File(rutaFichero+extension);
		
			mediaPlayer.getMediaPlayer().saveSnapshot(captura);
		
	}
	
	private void guardarLista(){
		try {
			Ficheros.escribirObjeto(listaReproduccion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void reproducirDVD(){
		String mrl = new SimpleDvdMrl().device("/D:\\")
                 .value();
		estado = Estado.PLAY;	
		btPlay.setText("||");
		 mediaPlayer.getMediaPlayer().playMedia(mrl);
		 
	}
	
	private void reproducirCD(){
		String mrl = new CdMrl().device("/D:\\").track(-1)
                .value();
		
		System.out.println(mrl);
		estado = Estado.PLAY;	
		btPlay.setText("||");
		mediaPlayer.getMediaPlayerFactory().newMediaList().addMedia(mrl);
		mediaPlayer.getMediaPlayer().playMedia(mrl); 
		 
	}
	
	private void barraDeTiempo(){
		HiloTiempo hilo=new HiloTiempo(barraProgreso,mediaPlayer,lbTiempoTranscurrido,lbTiempoTotal);
		hilo.execute();
	}
	
	private void retroceder(){
		int tiempoActual=0;
		  for(int i=0; i<=mediaPlayer.getMediaPlayer().getLength(); i++){
		   tiempoActual = (int) mediaPlayer.getMediaPlayer().getTime();
		  }
		  tiempoActual = tiempoActual-10000;
		  mediaPlayer.getMediaPlayer().setTime(tiempoActual);
		  barraProgreso.setValue(tiempoActual);
	}
	private void avanzar(){
		int tiempoActual=0;
		  for(int i=0; i<=mediaPlayer.getMediaPlayer().getLength(); i++){
		   tiempoActual = (int) mediaPlayer.getMediaPlayer().getTime();
		  }
		  tiempoActual = tiempoActual+10000;
		  mediaPlayer.getMediaPlayer().setTime(tiempoActual);
		  barraProgreso.setValue((int) tiempoActual);
	}
	private void moverBarra(){
		int tiempoActual=barraProgreso.getValue();
		 mediaPlayer.getMediaPlayer().setTime(tiempoActual);
		 barraProgreso.setValue((int) tiempoActual);
	}
	
	private void agregarSubtitulos(){
		File archivo;
		JFileChooser jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(false);
		if (jfc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
		   return;
		   
		 archivo = jfc.getSelectedFile();
		 mediaPlayer.getMediaPlayer().setSubTitleFile(archivo);
	}
	
	private void Streaming(){
		
		VideoExterno jVideoExterno=new VideoExterno();
		if(jVideoExterno.mostrarDialogo()==AccionDialogo.CANCELAR)
			return;

		String mrl ="http://"+ jVideoExterno.getUrl();
		System.out.println(mrl);
		estado = Estado.PLAY;	
		btPlay.setText("||");
		mediaPlayer.getMediaPlayer().setPlaySubItems(true);
		//String[] options = {":sout=#transcode{vcodec=h264,venc=x264{cfr=40},scale=1,acodec=mp4a,ab=96,channels=2,samplerate=44100}:file{dst=C:/test.mp4},dst=display}"};
		String[] options={":sout=#duplicate{dst=std{access=file,mux=webm,dst='video.avi'}, dst=display"};
		mediaPlayer.getMediaPlayer().playMedia(mrl,options);
		//mediaPlayer.getMediaPlayer().playMedia(mrl,":sout=#standard{mux=ts,access=file,dst=C:/capture.avi}"); 
		    
	    
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("MediaMony Player");
		//PARA QUE SALGA DIRECTAMENTE MAXIMIZADO
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		mntmAbrirVideo = new JMenuItem("Abrir...");
		mntmAbrirVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVideo();
			}
		});
		mnArchivo.add(mntmAbrirVideo);
		
		mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		mntmGuardarLista = new JMenuItem("Guardar lista");
		mntmGuardarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarLista();
			}
		});
		
		mntmAbrirDvd = new JMenuItem("Reproducir DVD");
		mntmAbrirDvd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reproducirDVD();
			}
		});
		
		mnStreaming = new JMenuItem("Streaming...");
		mnStreaming.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Streaming();
			}
		});
		mnArchivo.add(mnStreaming);
		mnArchivo.add(mntmAbrirDvd);
		
		mnCD = new JMenuItem("Reproducir CD");
		mnCD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reproducirCD();
			}
		});
		mnArchivo.add(mnCD);
		mnArchivo.add(mntmGuardarLista);
		mnArchivo.add(mntmSalir);
		
		mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		
		mntmCapturaPantalla = new JMenuItem("Capturar");
		mntmCapturaPantalla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				capturar();
			}
		});
		mnOpciones.add(mntmCapturaPantalla);
		
		mnGeometraVideo = new JMenu("Pantalla");
		mnOpciones.add(mnGeometraVideo);
		
		menuItem = new JMenuItem("4:3");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.getMediaPlayer().setCropGeometry("4:3");
			}
		});
		mnGeometraVideo.add(menuItem);
		
		menuItem_1 = new JMenuItem("16:9");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.getMediaPlayer().setCropGeometry("16:9");
			}
		});
		mnGeometraVideo.add(menuItem_1);
		
		mnSubtitulos = new JMenuItem("Subtitulos");
		mnSubtitulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				agregarSubtitulos();
			}
		});
		mnOpciones.add(mnSubtitulos);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panelBotones = new JPanel();
		frame.getContentPane().add(panelBotones, BorderLayout.SOUTH);
		
		barraVolumen = new JSlider();
		barraVolumen.setMaximum(200);
		barraVolumen.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				cambiarVolumen();
			}
		});
		
		barraProgreso = new JSlider();
		barraProgreso.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				barraDeTiempo();
			}
		});
		//barra al principio cuando se inicie el programa
		barraProgreso.setValue(0);
		barraProgreso.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				moverBarra();
			}
		});
		
		
		btStop = new JButton("STOP");
		btStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		panelBotones.add(btStop);
		
		lbTiempoTranscurrido = new JLabel("00:00 -");
		panelBotones.add(lbTiempoTranscurrido);
		
		lbTiempoTotal = new JLabel("00:00");
		panelBotones.add(lbTiempoTotal);
		panelBotones.add(barraProgreso);
		
		btPlay = new JButton(">");
		btPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reproducirVideo();
			}
		});
		
		btAtras = new JButton("<<");
		btAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				retroceder();
			}
		});
		
		panelBotones.add(btAtras);
		panelBotones.add(btPlay);
		
		btAdelante = new JButton(">>");
		btAdelante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				avanzar();
			}
		});
		panelBotones.add(btAdelante);
		
		lbVolumen = new JLabel("Volumen");
		panelBotones.add(lbVolumen);
		panelBotones.add(barraVolumen);
		
		btVerLista = new JButton("Ver Lista");
		btVerLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verLista();
			}
		});
		panelBotones.add(btVerLista);
		
		btEsconder = new JButton("Esconder");
		btEsconder.setEnabled(false);
		btEsconder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				esconderLista();
			}
		});
		panelBotones.add(btEsconder);
		
		btAgregar = new JButton("+");
		btAgregar.setVisible(false);
		btAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregar();
			}
		});
		panelBotones.add(btAgregar);
		
		btEliminar = new JButton("-");
		btEliminar.setVisible(false);
		btEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		panelBotones.add(btEliminar);
		
		lbPorcentaje = new JLabel("");
		panelBotones.add(lbPorcentaje);
		
		panelLista = new JPanel();
		frame.getContentPane().add(panelLista, BorderLayout.EAST);
		panelLista.setSize(50, 100);
		panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.X_AXIS));
		panelLista.setVisible(false);
		
		scrollPane = new JScrollPane();
	
		panelLista.add(scrollPane);
		
		ListaReproduccion = new ListaReproduccion();
		ListaReproduccion.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			        if (e.getClickCount() == 2) {
			        	int num=ListaReproduccion.seleccionado();
			        	ficheroVideo=rutas.get(num);
			        	mediaPlayer.getMediaPlayer().stop();
			        	
			        	System.out.println(ficheroVideo);
			        	
			        	internalFrame.setTitle(ficheroVideo.getName());
						mediaPlayer.getMediaPlayer().playMedia(ficheroVideo.getAbsolutePath());
						estado = Estado.PLAY;	
						btPlay.setText("||");
						
			        }
			}
		});
		scrollPane.setViewportView(ListaReproduccion);
		
		internalFrame = new JInternalFrame("");
		internalFrame.setFrameIcon(null);
		internalFrame.setBorder(null);
		frame.getContentPane().add(internalFrame, BorderLayout.CENTER);
		internalFrame.setVisible(true);

		frame.setLocationRelativeTo(null);
	}

}

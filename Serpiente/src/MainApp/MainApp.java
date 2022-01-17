package MainApp;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import snake.ControlTeclado;
import snake.MyButtonListener;
import snake.MySnakeFrame;
import snake.TableroJuego;

public class MainApp {

	private static MySnakeFrame frame;

	public static MySnakeFrame getFrame() {
		return frame;
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO code application logic here

		/**
		 * @wbp.parser.entryPoint
		 */

		int contador;
		JPanel mainPanel;
		TableroJuego tablero;
		JPanel botonera;
		JLabel puntos;
		JLabel puntosNum;
		JButton start;
		JButton pause;
		ControlTeclado miControlador;
		
		int tam1 = Tama�o1();
		
		int opcion = 2;
		opcion = dificultad();
		
		// 1. Crear el frame.

		frame = new MySnakeFrame();

		// asignamos el tamaño a nuestra ventana, y hacemos que se cierre cuando nos
		// pulsan
		// la X de cerrar la ventana
		frame.setSize(tam1, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 3. Ahora creamos los componentes y los ponemos en la frame (ventana).

		// El panel de fondo. Rellena el frame, y sirve de contenedor del tablero y de
		// la botonera.
		mainPanel = new JPanel(new BorderLayout());

		// Ahora creamos el tablero. Recordamos: no deja de ser un panel un poquito7
		// "especial"
		tablero = new TableroJuego();
		
		
		
		// Les damos las propiedades a nuestro tablero. Su color, tamaño y borde
		tablero.setBorder(BorderFactory.createLineBorder(Color.black));
		tablero.setBackground(new java.awt.Color(230, 210, 200));
		tablero.setSize(600, 400);

		// Le damos un enlace al tablero para que sepa quién es su frame (ventana) y
		// así
		// sepa
		// quién contiene la serpiente y quién controla el juego...
		tablero.setSnakeFrame(frame);

		// Ahora el turno de la botonera. Tendrá los dos botones y las etiquetas de
		// texto
		botonera = new JPanel();
		botonera.setBorder(BorderFactory.createLineBorder(Color.black));
		botonera.setBackground(new java.awt.Color(150, 150, 150));

		// Ahora definimos las dos etiquetas para los puntos.
		puntos = new JLabel();
		puntos.setText("Puntos: ");
		puntos.setBackground(new java.awt.Color(190, 190, 190));

		puntosNum = new JLabel();
		puntosNum.setText("0");
		puntosNum.setBackground(new java.awt.Color(190, 190, 190));

		// turno de los botones de empezar y pausar/continuar
		start = new JButton();
		start.setSize(50, 20);
		start.setText("Start");
		start.addActionListener(new MyButtonListener(frame, tablero));

		pause = new JButton();
		pause.setSize(50, 20);
		pause.setText("Pause");
		pause.addActionListener(new MyButtonListener(frame, tablero));

		// Preparamos el control del teclado
		miControlador = new ControlTeclado();
		miControlador.setSnakeFrame(frame); // le damos al controlador de teclado un enlace el frame principal
		tablero.addKeyListener(miControlador); // le decimos al tablero que el teclado es cosa de nuestro controlador
		tablero.setFocusable(true); // permitimos que el tablero pueda coger el foco.

		// Añadimos los componentes uno a uno, cada uno en su contenedor, y al final el
		// panel principal
		// se añade al frame principal.
		botonera.add(start);
		botonera.add(pause);
		botonera.add(puntos);
		botonera.add(puntosNum);

		mainPanel.add(botonera, BorderLayout.PAGE_END);
		mainPanel.add(tablero, BorderLayout.CENTER);
		frame.getContentPane().add(mainPanel);

		frame.setVisible(true); // activamos la ventana principal para que sea "pintable"

		contador = 0; // nuestro control de los pasos del tiempo. Cada vez que contador cuenta un
						// paso, pasan 10ms

		
		
		
		while (true) { // por siempre jamás (hasta que nos cierren la ventana) estamos controlando el
						// juego.

			// actualizamos el estado del juego
			if (contador % Switch(opcion) == 0) { // cada 200ms nos movemos o crecemos...
				if (contador == 80) { // Cada 600ms crecemos y reseteamos el contador
					contador = 0;
					frame.tocaCrecer();
					// hemos crecido... actualizamos puntos.
					puntosNum.setText(Integer.toString(frame.getSerpiente().getPuntos()));
				} else { // a los 200 y 400 ms nos movemos...
					contador++;
					frame.tocaMoverse();
				}
				frame.comprobarEstado(tablero.getHeight(), tablero.getWidth()); // comprobamos si hemos muerto o no.

			} else { // Cada vez que no hay que moverse o crecer, simplemente contamos...
				contador++;
			}

			// hemos terminado?? mostramos msg
			if (frame.mostrarFin()) {
				JOptionPane.showMessageDialog(frame,
						"Se acabo vaquero, has conseguido " + puntosNum.getText() + " puntos");
			}

			// Repintamos
			tablero.repaint();

			// Esperamos para dar tiempo al thread de repintado a pintar.
			Thread.sleep(5);
		}

	}

	public static int dificultad() {
		
		// Con JCombobox
		int seleccion = JOptionPane.showOptionDialog(getFrame(), "Seleccione opcion", "Selector de opciones",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, // null para icono por defecto.
				new Object[] { "Facil", "Medio", "Dificil", "Imposible" }, // null para YES, NO y CANCEL
				"opcion 1");

		if (seleccion != -1)
			System.out.println("seleccionada opcion " + (seleccion + 1));
		if (seleccion == 0) {
			return 1;
		} else if (seleccion == 1) {
			return 2;
		} else if (seleccion == 2) {
			return 3;
		} else if (seleccion == 3) {
			return 4;
		}
		return 1;
	}

	public static int Switch(int opc) {

		int velocidad = 1;

		switch (opc) {
		case 1:
			velocidad = 40;
			break;
		case 2:
			velocidad = 15;
			break;
		case 3:
			velocidad = 5;
			break;
		case 4:
			velocidad = 1;
			break;
		}
		return velocidad;
	}

	public static int Tama�o1() {
	int cord1 = 0;
	String seleccion = JOptionPane.showInputDialog(
				   getFrame(),
				   "Indica el tama�o 1",
				   JOptionPane.QUESTION_MESSAGE);  // el icono sera un iterrogante
				        
				System.out.println("El usuario ha escrito "+seleccion);
		
	return cord1;		
				
				
		

	}
	
	public static int Tama�o2() {
		int cord2 = 0;
		String seleccion = JOptionPane.showInputDialog(
					   getFrame(),
					   "Indica el tama�o 2",
					   JOptionPane.QUESTION_MESSAGE);  // el icono sera un iterrogante
					        
					System.out.println("El usuario ha escrito "+seleccion);
			
		return cord2;		
					
					
			

		}
}

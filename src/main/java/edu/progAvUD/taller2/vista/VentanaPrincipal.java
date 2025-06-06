/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.progAvUD.taller2.vista;

import edu.progAvUD.taller2.control.ControlGrafico;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Esta clase esta encargada de manejar el momento en el que aparece cada panel
 * y ademas crearlos
 *
 * @author Andres Felipe
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private ControlGrafico controlGrafico;

    /**
     * Sirve para conocer el otro panel
     */
    public PanelPrincipal panelPrincipal;

    /**
     *
     */
    public PanelMesa panelMesa;

    /**
     * Sirve para conocer el otro panel
     */
    public DialogComprarFichas dialogComprarFichas;

    /**
     * Este panel tapa una de las cartas del crupier
     */
    public PanelCarta cartaOculta;

    /**
     *Este panel esta dedicado para las cartas de cada persona, dependiendo si divide o no
     */
    public JPanel panel1Jugador1;

    /**
     *Este panel esta dedicado para las cartas de cada persona, dependiendo si divide o no
     */
    public JPanel panel2Jugador1;

    /**
     *Este panel esta dedicado para las cartas de cada persona, dependiendo si divide o no
     */
    public JPanel panel1Jugador2;

    /**
     *Este panel esta dedicado para las cartas de cada persona, dependiendo si divide o no
     */
    public JPanel panel2Jugador2;

    /**
     * Creates new form VentanaPrincipal
     *
     * @param controlGrafico
     */
    public VentanaPrincipal(ControlGrafico controlGrafico) {
        this.controlGrafico = controlGrafico;
        initComponents();
        setVisible(true);
        this.panelPrincipal = new PanelPrincipal();
        this.panelMesa = new PanelMesa();
        this.dialogComprarFichas = new DialogComprarFichas(this, true);
    }

    /**
     * Sirve para buscar un archivo con ciertas especificaciones
     *
     * @return devuelve el archivo para escribir los datos de la persona
     * @throws java.io.IOException devuelve un posible error
     */
    public File pedirArchivoPersonaSerializado() throws IOException {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/src/main/java/edu/progAvUD/taller2/data");
        chooser.setDialogTitle("Seleccione una carpeta para guardar el archivo .bin");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.resetChoosableFileFilters();
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

    /**
     * Sirve para buscar un archivo con ciertas especificaciones
     *
     * @return devuelve el archivo para escribir los datos de partida
     * @throws java.io.IOException devuelve un error
     */
    public File pedirArchivoAleatorio() throws IOException {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/src/main/java/edu/progAvUD/taller2/data");
        chooser.setDialogTitle("Seleccione una carpeta para guardar el archivo .dat");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.resetChoosableFileFilters();
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de éxito.
     *
     * @param mensaje Mensaje a mostrar al usuario.
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
     *
     * @param mensaje Mensaje a mostrar al usuario.
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Sirve para buscar un archivo con ciertas especificaciones
     *
     * @return devuelve el archivo
     */
    public File pedirArchivo() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir") + "/src/main/java/edu/progAvUD/taller2/data");

        // Aplica un filtro para archivos .properties
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos .properties", "properties"));
        fileChooser.showOpenDialog(null);
        // Mostrar el diálogo
        return fileChooser.getSelectedFile();
    }

    /**
     *Pide el archivo para cargar el crupier
     * @return el archivo de el crupier serializable
     * @throws FileNotFoundException devuelve un error
     */
    public File pedirArchivoCrupier() throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir") + "/src/main/java/edu/progAvUD/taller2/data");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos .bin", "bin"));
        fileChooser.showOpenDialog(null);
        return fileChooser.getSelectedFile();
    }

    /**
     * Se encarga de mostrar cada panel
     *
     * @param panel
     */
    public void mostrarPanel(JPanel panel) {
        setContentPane(panel);
        pack(); // Ajusta el tamaño de la ventana según el contenido
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        revalidate();
        repaint();
    }

    /**
     * Se encarga de crear el panelCarta para adaptar la carta a la que toco
     *
     * @param numeroCarta es el valor de la carta
     * @param palo es la figura de la carta
     * @return devuelve el panel
     */
    public PanelCarta crearCarta(String numeroCarta, String palo) {
        PanelCarta carta = new PanelCarta();
        String path = System.getProperty("user.dir") + "/src/main/java/edu/progAvUD/taller2/imagenes/" + palo + ".png";
        ImageIcon imagenCarta = new ImageIcon(path);
        carta.jLabelFiguraCarta.setIcon(imagenCarta);
        carta.jLabelDenominacionCarta1.setText(numeroCarta);
        carta.jLabelDenominacionCarta2.setText(numeroCarta);
        return carta;
    }

    /**
     *Muestra la carta que el crupier tiene tapada
     */
    public void mostrarCartaOculta() {
        cartaOculta = crearCarta("", "OCULTA");
        cartaOculta.remove(cartaOculta.jLabelDenominacionCarta1);
        cartaOculta.remove(cartaOculta.jLabelDenominacionCarta2);
        cartaOculta.setLayout(new GridLayout(1, 1));
        cartaOculta.jLabelFiguraCarta.setPreferredSize(new Dimension(50, 79));
        cartaOculta.setBackground(new Color(153, 29, 31));
        cartaOculta.revalidate();
        cartaOculta.repaint();
        panelMesa.jPanelCartasCrupier.add(cartaOculta);
    }

    /**
     *Este metodo muestra el panel para hacer el seguro
     * @return un entero para validar
     */
    public int mostrarJOptionPanelSeguro() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        JLabel texto1 = new JLabel("Seleccione la cantidad de fichas que quiere asegurar");
        JLabel texto2 = new JLabel("Recuerde que solo pude asegurar la mitad de la apuesta");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(texto1);
        panel.add(Box.createVerticalStrut(5));
        panel.add(texto2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(spinner);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Seleccione un valor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        return controlGrafico.verificarEstadoJOptionPane(result, spinner.getValue());

    }

    /**
     *Este metodo divide el panel en 2 en caso de que la persona divida
     */
    public void dividirPanelCartasJugador1() {
        panelMesa.jPanelCartasJugador1.removeAll();
        panelMesa.jPanelCartasJugador1.revalidate();
        panelMesa.jPanelCartasJugador1.repaint();
        panelMesa.jPanelCartasJugador1.setLayout(new GridLayout(1, 2));
        panel1Jugador1 = new JPanel(new FlowLayout());
        panel2Jugador1 = new JPanel(new FlowLayout());
        
        panel1Jugador1.setBackground(new Color(28,111,65));
        panel2Jugador1.setBackground(new Color(28,111,65));

        panelMesa.jPanelCartasJugador1.add(panel1Jugador1);
        panelMesa.jPanelCartasJugador1.add(panel2Jugador1);
        panelMesa.jPanelCartasJugador1.revalidate();
        panelMesa.jPanelCartasJugador1.repaint();
    }

    /**
     *Este metodo divide el panel en 2 en caso de que la persona divida
     */
    public void dividirPanelCartasJugador2() {
        panelMesa.jPanelCartasJugador2.removeAll();
        panelMesa.jPanelCartasJugador2.revalidate();
        panelMesa.jPanelCartasJugador2.repaint();
        panelMesa.jPanelCartasJugador2.setLayout(new GridLayout(1, 2));
        panel1Jugador2 = new JPanel(new FlowLayout());
        panel2Jugador2 = new JPanel(new FlowLayout());
        
        panel1Jugador2.setBackground(new Color(28,111,65));
        panel2Jugador2.setBackground(new Color(28,111,65));

        panelMesa.jPanelCartasJugador2.add(panel1Jugador2);
        panelMesa.jPanelCartasJugador2.add(panel2Jugador2);
        panelMesa.jPanelCartasJugador2.revalidate();
        panelMesa.jPanelCartasJugador2.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

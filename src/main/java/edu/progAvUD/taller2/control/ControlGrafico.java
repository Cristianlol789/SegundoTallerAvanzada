package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.vista.PanelCarta;
import edu.progAvUD.taller2.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 *
 * @author Andres Felipe
 */
public class ControlGrafico implements ActionListener {

    private ControlPrincipal controlPrincipal;
    private VentanaPrincipal ventanaPrincipal;
    private int jugadorQuePrecionoComprar;

    public ControlGrafico(ControlPrincipal controlprincipal) {
        this.controlPrincipal = controlprincipal;
        this.ventanaPrincipal = new VentanaPrincipal(this);
        ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelPrincipal);
        ventanaPrincipal.panelPrincipal.jButtonJugar.setEnabled(false);

        ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.addActionListener(this);
        ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.addActionListener(this);
        ventanaPrincipal.panelPrincipal.jButtonJugar.addActionListener(this);

        ventanaPrincipal.panelMesa.jButtonRepartir.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonPedir.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonPlantarse.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonDoblar.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonDividir.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2.addActionListener(this);

        ventanaPrincipal.panelMesa.jButtonSeguroJugador1.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonSeguroJugador2.addActionListener(this);

        ventanaPrincipal.dialogComprarFichas.jButtonComprarFichas.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores) {
            ventanaPrincipal.setVisible(false);
            controlPrincipal.crearConexionPropiedades();
            ventanaPrincipal.setVisible(true);
            controlPrincipal.cargarJugadores();
            ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.setEnabled(false);
            mostrarBotonJugar();
        }
        if (e.getSource() == ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier) {
            ventanaPrincipal.setVisible(false);
            controlPrincipal.crearConexionPropiedades();
            ventanaPrincipal.setVisible(true);
            controlPrincipal.cargarCrupier();
            ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.setEnabled(false);
            mostrarBotonJugar();
        }
        if (e.getSource() == ventanaPrincipal.panelPrincipal.jButtonJugar) {
            controlPrincipal.conteoJugadores();
            controlPrincipal.seleccionarJugadores();
            ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelMesa);
            ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(false);
            ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(false);
            ventanaPrincipal.panelMesa.jButtonRepartir.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(false);
            ventanaPrincipal.panelMesa.jLabelTextoCantidadApostada1.setVisible(false);
            ventanaPrincipal.panelMesa.jLabelTextoCantidadApostada2.setVisible(false);
            ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador1.setVisible(false);
            ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador2.setVisible(false);

        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1) {
            String cedulaJugador1 = controlPrincipal.darCedulaJugadoresEnPartida("jugador1");

            jugadorQuePrecionoComprar = 1;
            if (0 == controlPrincipal.darCantidadFichasJugador(cedulaJugador1)) {
                ventanaPrincipal.dialogComprarFichas.jLabelCantidadDinero.setText("$" + controlPrincipal.darCantidadDineroJugador(cedulaJugador1));
                ventanaPrincipal.dialogComprarFichas.setVisible(true);
            } else {
                int cantidadAApostar = (int) ventanaPrincipal.panelMesa.jSpinnerCantidadFichasApostar1.getValue();
                if (controlPrincipal.verificarFichasAApostar(cedulaJugador1, cantidadAApostar)) {
                    controlPrincipal.setFichasApostadasJugador1(cantidadAApostar);

                    ventanaPrincipal.panelMesa.jSpinnerCantidadFichasApostar1.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabelTextoApostar1.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabelCantidadFichasJugador1.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabeltTextoCantidadFichasJugador1.setVisible(false);
                    ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1.setVisible(false);

                    ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador1.setText(cantidadAApostar + "");
                    ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador1.setVisible(true);
                    ventanaPrincipal.panelMesa.jLabelTextoCantidadApostada1.setVisible(true);

                    mostrarMensajeExito("El jugador 1 ha apostado " + cantidadAApostar + " fichas");

                    if (!ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1.isVisible() && !ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2.isVisible()) {
                        ventanaPrincipal.panelMesa.jButtonRepartir.setEnabled(true);
                    }

                } else {
                    mostrarMensajeError("No se tiene la cantidad de fichas para apostar");
                }
            }
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2) {
            String cedulaJugador2 = controlPrincipal.darCedulaJugadoresEnPartida("jugador2");
            jugadorQuePrecionoComprar = 2;
            if (0 == controlPrincipal.darCantidadFichasJugador(cedulaJugador2)) {
                ventanaPrincipal.dialogComprarFichas.jLabelCantidadDinero.setText("$" + controlPrincipal.darCantidadDineroJugador(cedulaJugador2));
                ventanaPrincipal.dialogComprarFichas.setVisible(true);
            } else {
                int cantidadAApostar = (int) ventanaPrincipal.panelMesa.jSpinnerCantidadFichasApostar2.getValue();
                if (controlPrincipal.verificarFichasAApostar(cedulaJugador2, cantidadAApostar)) {
                    controlPrincipal.setFichasApostadasJugador2(cantidadAApostar);

                    ventanaPrincipal.panelMesa.jSpinnerCantidadFichasApostar2.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabelTextoApostar2.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabelCantidadFichasJugador2.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabeltTextoCantidadFichasJugador2.setVisible(false);
                    ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2.setVisible(false);

                    ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador2.setText(cantidadAApostar + "");
                    ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador2.setVisible(true);
                    ventanaPrincipal.panelMesa.jLabelTextoCantidadApostada2.setVisible(true);

                    mostrarMensajeExito("El jugador 2 ha apostado " + cantidadAApostar + " fichas");

                    if (!ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1.isVisible() && !ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2.isVisible()) {
                        ventanaPrincipal.panelMesa.jButtonRepartir.setEnabled(true);
                    }
                } else {
                    mostrarMensajeError("No se tiene la cantidad de fichas para apostar");
                }
            }
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonDividir) {

        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonDoblar) {

        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonPedir) {

        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonPlantarse) {

        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonRepartir) {
            controlPrincipal.crearMazo();
            for (int i = 1; i <= 2; i++) {
                controlPrincipal.darCartas("Jugador1");
                controlPrincipal.darCartas("Jugador2");
                controlPrincipal.darCartas("Crupier");
            }
            ventanaPrincipal.panelMesa.jButtonRepartir.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(true);
            ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(true);
            ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(true);
            
            controlPrincipal.setTurnoJugador("Jugador1");
            ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText(controlPrincipal.getTurnoJugador());
            
            if(controlPrincipal.verificarCartarIguales(controlPrincipal.getTurnoJugador())){
                ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(true);
            }
        }
        if (e.getSource() == ventanaPrincipal.dialogComprarFichas.jButtonComprarFichas) {
            if (jugadorQuePrecionoComprar == 1) {
                int cantidadCompra = (int) ventanaPrincipal.dialogComprarFichas.jSpinnerFichasAComprar.getValue();
                if (controlPrincipal.verificarFichasAComprar(controlPrincipal.darCedulaJugadoresEnPartida("jugador1"), cantidadCompra)) {
                    mostrarMensajeExito("Se han comprado correctamente");
                    ventanaPrincipal.dialogComprarFichas.dispose();
                } else {
                    mostrarMensajeError("No tiene suficientes fondos");
                }
            } else if (jugadorQuePrecionoComprar == 2) {
                int cantidadCompra = (int) ventanaPrincipal.dialogComprarFichas.jSpinnerFichasAComprar.getValue();
                if (controlPrincipal.verificarFichasAComprar(controlPrincipal.darCedulaJugadoresEnPartida("jugador2"), cantidadCompra)) {
                    mostrarMensajeExito("Se han comprado correctamente");
                    ventanaPrincipal.dialogComprarFichas.dispose();
                } else {
                    mostrarMensajeError("No tiene suficientes fondos");
                }
            }
        }
    }

    public void mostrarCarta(String palo, String denominacion, String duenoCarta) {
        if (duenoCarta.equals("Jugador1")) {
            if (null != denominacion) switch (denominacion) {
                case "AZ":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("A", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "DOS":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("2", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "TRES":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("3", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "CUATRO":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("4", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "CINCO":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("5", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "SEIS":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("6", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "SIETE":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("7", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "OCHO":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("8", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "NUEVE":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("9", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "DIEZ":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("10", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "J":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("J", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "Q":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("Q", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                case "K":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("K", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador1.add(carta);
                        break;
                    }
                default:
                    break;
            }

        } else if (duenoCarta.equals("Jugador2")) {
            if (null != denominacion) switch (denominacion) {
                case "AZ":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("A", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "DOS":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("2", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "TRES":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("3", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "CUATRO":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("4", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "CINCO":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("5", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "SEIS":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("6", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "SIETE":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("7", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "OCHO":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("8", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "NUEVE":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("9", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "DIEZ":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("10", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "J":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("J", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "Q":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("Q", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                case "K":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("K", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasJugador2.add(carta);
                        break;
                    }
                default:
                    break;
            }

        } else if (duenoCarta.equals("Crupier")) {
            if (null != denominacion) switch (denominacion) {
                case "AZ":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("A", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "DOS":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("2", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "TRES":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("3", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "CUATRO":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("4", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "CINCO":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("5", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "SEIS":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("6", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "SIETE":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("7", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "OCHO":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("8", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "NUEVE":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("9", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "DIEZ":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("10", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "J":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("J", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "Q":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("Q", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                case "K":{
                    PanelCarta carta = ventanaPrincipal.crearCarta("K", palo);
                    ventanaPrincipal.panelMesa.jPanelCartasCrupier.add(carta);
                        break;
                    }
                default:
                    break;
            }

        }

        ventanaPrincipal.panelMesa.jPanelCartasJugador1.revalidate();
        ventanaPrincipal.panelMesa.jPanelCartasJugador1.repaint();
    }

    public void mostrarBotonJugar() {
        if (!ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.isEnabled() && !ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.isEnabled()) {
            ventanaPrincipal.panelPrincipal.jButtonJugar.setEnabled(true);
        }
    }

    public void mostraDatosJugador1(String cedulaJugador1, double cantidadFichasJugador1) {
        ventanaPrincipal.panelMesa.jLabelCedulaJugador1.setText(cedulaJugador1);
        ventanaPrincipal.panelMesa.jLabelCantidadFichasJugador1.setText(cantidadFichasJugador1 + "");

    }

    public void mostraDatosJugador2(String cedulaJugador2, double cantidadFichasJugador2) {
        ventanaPrincipal.panelMesa.jLabelCedulaJugador2.setText(cedulaJugador2);
        ventanaPrincipal.panelMesa.jLabelCantidadFichasJugador2.setText(cantidadFichasJugador2 + "");
    }

    public VentanaPrincipal getVentanaPrincipal() {
        return ventanaPrincipal;
    }

    public void mostrarMensajeError(String mensaje) {
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }

    public void mostrarMensajeExito(String mensaje) {
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }

    public File pedirArchivo() {
        return ventanaPrincipal.pedirArchivo();
    }

    public File pedirArchivoSerializado() {
        return ventanaPrincipal.pedirArchivoSerializacion();
    }

    public File pedirArchivoAleatorio() {
        return ventanaPrincipal.pedirArchivoAleatorio();
    }
}

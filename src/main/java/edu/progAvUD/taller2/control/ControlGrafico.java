package edu.progAvUD.taller2.control;

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
            ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelMesa);
            ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(false);
            ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(false);
            ventanaPrincipal.panelMesa.jButtonRepartir.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(false);
            controlPrincipal.conteoJugadores();
            controlPrincipal.seleccionarJugadores();
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1) {
            if (0 == controlPrincipal.darCantidadFichasJugador(ventanaPrincipal.panelMesa.jLabelCedulaJugador1.getText())) {
                ventanaPrincipal.dialogComprarFichas.setVisible(true);
            }

        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2) {

            if (0 == controlPrincipal.darCantidadFichasJugador(ventanaPrincipal.panelMesa.jLabelCedulaJugador2.getText())) {
                ventanaPrincipal.dialogComprarFichas.setVisible(true);
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

        }
    }

    public void mostrarBotonJugar() {
        if (!ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.isEnabled() && !ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.isEnabled()) {
            ventanaPrincipal.panelPrincipal.jButtonJugar.setEnabled(true);
        }
    }

    public void mostrarDatosJugadores(String cedulaJugador1, String cedulaJugador2, double cantidadFichasJugador1, double cantidadFichasJugador2) {
        ventanaPrincipal.panelMesa.jLabelCedulaJugador1.setText(cedulaJugador1);
        ventanaPrincipal.panelMesa.jLabelCedulaJugador2.setText(cedulaJugador2);
        ventanaPrincipal.panelMesa.jLabelCantidadFichasJugador1.setText(cantidadFichasJugador1 + "");
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
}

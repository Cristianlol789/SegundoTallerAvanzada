package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 *
 * @author Andres Felipe
 */
public class ControlGrafico implements ActionListener{
    
    private ControlPrincipal controlprincipal;
    private VentanaPrincipal ventanaPrincipal;

    public ControlGrafico(ControlPrincipal controlprincipal) {
        this.controlprincipal = controlprincipal;
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
        ventanaPrincipal.panelMesa.jButtonApostarFichas.addActionListener(this);
        
        ventanaPrincipal.panelMesa.jButtonSeguroJugador1.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonSeguroJugador2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores){
            ventanaPrincipal.setVisible(false);
            controlprincipal.crearConexionPropiedades();
            ventanaPrincipal.setVisible(true);
            controlprincipal.cargarJugadores();
            ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.setEnabled(false);
            mostrarBotonJugar();
        }
        if(e.getSource()==ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier){
            ventanaPrincipal.setVisible(false);
            controlprincipal.crearConexionPropiedades();
            ventanaPrincipal.setVisible(true);
            controlprincipal.cargarCrupier();
            ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.setEnabled(false);
            mostrarBotonJugar();
        }
        if (e.getSource()==ventanaPrincipal.panelPrincipal.jButtonJugar){
            ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelMesa);
            ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(false);
            ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(false);
            ventanaPrincipal.panelMesa.jButtonRepartir.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(false);
        }

    }
    
    public void mostrarBotonJugar(){
        if(!ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.isEnabled()&& !ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.isEnabled()){
            ventanaPrincipal.panelPrincipal.jButtonJugar.setEnabled(true);
        }
    }
    
    public VentanaPrincipal getVentanaPrincipal() {
        return ventanaPrincipal;
    }
    
    public void mostrarMensajeError(String mensaje){
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }

    public void mostrarMensajeExito(String mensaje){
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }
    
    public File pedirArchivo(){
        return ventanaPrincipal.pedirArchivo();
    }
}

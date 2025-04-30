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
        ventanaPrincipal.panelPrincipal.jButtonJugar.setVisible(false);
        
        ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.addActionListener(this);
        ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.addActionListener(this);
        ventanaPrincipal.panelPrincipal.jButtonJugar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores){
            ventanaPrincipal.setVisible(false);
            controlprincipal.crearConexionPropiedades();
            ventanaPrincipal.setVisible(true);
            controlprincipal.cargarJugadores();
            ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.setVisible(false);
            mostrarBotonJugar();
        }
        if(e.getSource()==ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier){
            ventanaPrincipal.setVisible(false);
            controlprincipal.crearConexionPropiedades();
            ventanaPrincipal.setVisible(true);
            controlprincipal.cargarCrupier();
            ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.setVisible(false);
            mostrarBotonJugar();
        }
        if (e.getSource()==ventanaPrincipal.panelPrincipal.jButtonJugar){
            ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelMesa);
        }
    }
    
    public void mostrarBotonJugar(){
        if(!ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.isVisible() && !ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.isVisible()){
            ventanaPrincipal.panelPrincipal.jButtonJugar.setVisible(true);
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

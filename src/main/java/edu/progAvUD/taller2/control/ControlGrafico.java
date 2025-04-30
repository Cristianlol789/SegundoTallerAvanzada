package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.vista.VentanaPrincipal;
import java.io.File;

/**
 *
 * @author Andres Felipe
 */
public class ControlGrafico {
    
    private ControlPrincipal controlprincipal;
    private VentanaPrincipal ventanaPrincipal;

    public ControlGrafico(ControlPrincipal controlprincipal) {
        this.controlprincipal = controlprincipal;
        this.ventanaPrincipal = new VentanaPrincipal(this);
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

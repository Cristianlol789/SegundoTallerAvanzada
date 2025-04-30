package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.Carta;
import java.util.ArrayList;

/**
 *
 * @author Andres Felipe
 */
public class ControlCarta {

    private ControlPrincipal controlPrincipal;

    public ControlCarta(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
    }

    public Carta crearCarta(String palo, String denominacion) {
        Carta carta = new Carta(palo, denominacion);
        return carta;
    }
}

package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.Carta;
import java.util.ArrayList;

/**
 *
 * @author Andres Felipe
 */
public class ControlCarta {

    private ControlPrincipal controlPrincipal;
    private ArrayList<Carta> cartas;

    public ControlCarta(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.cartas = new ArrayList<>();
    }

    public void crearCarta() {
        for (Carta.Palo palo : Carta.Palo.values()) {
            for (Carta.Denominacion denom : Carta.Denominacion.values()) {
                Carta carta = new Carta(palo.name(), denom.name());
                cartas.add(carta);
            }
        }
    }
}

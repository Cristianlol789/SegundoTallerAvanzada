package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.Carta;

/**
 * Controlador responsable de la creación de instancias de la clase Carta.
 * Centraliza la lógica para generar cartas a partir de palo y denominación.
 *
 * @author Andres Felipe
 */
public class ControlCarta {

    /**
     * Referencia al controlador principal de la aplicación (para mostrar
     * mensajes o acceder a recursos si es necesario).
     */
    private ControlPrincipal controlPrincipal;

    /**
     * Inicializa el controlador de cartas con la referencia al
     * ControlPrincipal.
     *
     * @param controlPrincipal Instancia de ControlPrincipal usada para
     * interacción con este.
     */
    public ControlCarta(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
    }

    /**
     * Crea una nueva carta con el palo y denominación especificados.
     *
     * @param palo Palo de la carta (por ejemplo, "Corazones", "Diamantes").
     * @param denominacion Denominación de la carta (por ejemplo, "A", "K",
     * "10").
     * @return Objeto Carta recién creado con los valores proporcionados.
     */
    public Carta crearCarta(String palo, String denominacion) {
        return new Carta(palo, denominacion);
    }
}
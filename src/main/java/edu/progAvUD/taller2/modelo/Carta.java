package edu.progAvUD.taller2.modelo;

/**
 * Esta clase muestra el modelado de la carta que se utilizara al momento de crear el mazo para empezar el juego
 * @author Cristianlol789
 */
public class Carta {
    
    /**
     *
     */
    public enum Palo {

        /**
         *Es la figura que tiene como predefinida esta clase
         */
        CORAZONES,

        /**
         *Es la figura que tiene como predefinida esta clase
         */
        TREBOLES,

        /**
         *Es la figura que tiene como predefinida esta clase
         */
        PICAS,

        /**
         *Es la figura que tiene como predefinida esta clase
         */
        DIAMANTES
    }
    
    /**
     *
     */
    public enum Denominacion{

        /**
         *Este es el valor de la carta en este caso puede valer 11 0 1
         */
        A,

        /**
         *Es el valor de la carta
         */
        DOS,

        /**
         *Es el valor de la carta
         */
        TRES,

        /**
         *Es el valor de la carta
         */
        CUATRO,

        /**
         *Es el valor de la carta
         */
        CINCO,

        /**
         *Es el valor de la carta
         */
        SEIS,

        /**
         *Es el valor de la carta
         */
        SIETE,

        /**
         *Es el valor de la carta
         */
        OCHO,

        /**
         *Es el valor de la carta
         */
        NUEVE,

        /**
         *Es el valor de la carta
         */
        DIEZ,

        /**
         *Es el valor de la carta, en este caso su valor es 10
         */
        J,

        /**
         *Es el valor de la carta, en este caso su valor es 10
         */
        Q,

        /**
         *Es el valor de la carta, en este caso su valor es 10
         */
        K
    }
    
    private Palo palo;
    private Denominacion denominacion;

    /**
     * Este metodo sirve para construir la carta
     * @param palo es la figura que tomara la carta
     * @param denominacion es el valor que tomara la carta
     */
    public Carta(String palo, String denominacion) {
        this.palo = Palo.valueOf(palo);
        this.denominacion = Denominacion.valueOf(denominacion);
    }

    /**
     *Este metodo sirve para mostrar la figura de la carta
     * @return la figura que tiene la carta
     */
    public Palo getPalo() {
        return palo;
    }

    /**
     *Este metodo sirve para cambiar la figura en la carta
     * @param palo es el parametro que muestra la figura de la carta
     */
    public void setPalo(Palo palo) {
        this.palo = palo;
    }

    /**
     *Este parametro sirve para saber el valor de la carta
     * @return devuelve el valor de esta carta
     */
    public Denominacion getDenominacion() {
        return denominacion;
    }

    /**
     *Este parametro permite cambiar el valor de la carta
     * @param denominacion es el nuevo parametro para cambiar el valor anterior
     */
    public void setDenominacion(Denominacion denominacion) {
        this.denominacion = denominacion;
    }
    
}
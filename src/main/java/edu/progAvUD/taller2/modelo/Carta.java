package edu.progAvUD.taller2.modelo;

public class Carta {
    
    public enum Palo {
        CORAZONES, TREBOLES, PICAS, DIAMANTES
    }
    
    public enum Denominacion{
        A, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, DIEZ, J, Q, K
    }
    
    private Palo palo;
    private Denominacion denominacion;

    public Carta(String palo, String denominacion) {
        this.palo = Palo.valueOf(palo);
        this.denominacion = Denominacion.valueOf(denominacion);
    }

    public Palo getPalo() {
        return palo;
    }

    public void setPalo(Palo palo) {
        this.palo = palo;
    }

    public Denominacion getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(Denominacion denominacion) {
        this.denominacion = denominacion;
    }
    
}
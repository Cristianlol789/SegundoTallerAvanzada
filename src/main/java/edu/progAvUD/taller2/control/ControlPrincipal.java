package edu.progAvUD.taller2.control;

public class ControlPrincipal {

    private ControlGrafico controlGrafico;
    private ControlCarta controlCarta;
    private ControlPersona controlPersona;

    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
        controlPersona = new ControlPersona(this);
        controlCarta = new ControlCarta(this);
    }

    public void mostrarMensajeError(String mensaje) {
        controlGrafico.mostrarMensajeError(mensaje);
    }

    public void mostrarMensajeExito(String mensaje) {
        controlGrafico.mostrarMensajeExito(mensaje);
    }

}

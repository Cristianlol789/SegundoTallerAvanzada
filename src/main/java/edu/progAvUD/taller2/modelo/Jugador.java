package edu.progAvUD.taller2.modelo;

/**
 *
 * @author Andres Felipe
 */
public class Jugador extends Persona {
    
    private String direccion;
    private String telefono;
    private double dinero;
    private double cantidadFichas;

    public Jugador(String nombre, String cedula, String apellido, String direccion, String telefono, double dinero, double cantidadFichas) {
        super(nombre, cedula, apellido);
        this.direccion = direccion;
        this.telefono = telefono;
        this.dinero = dinero;
        this.cantidadFichas = cantidadFichas;
    }

    public Jugador(String cedula) {
        super(cedula);
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public double getCantidadFichas() {
        return cantidadFichas;
    }

    public void setCantidadFichas(double cantidadFichas) {
        this.cantidadFichas = cantidadFichas;
    }

}
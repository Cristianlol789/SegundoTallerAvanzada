package edu.progAvUD.taller2.modelo;

/**
 *
 * @author Andres Felipe
 */
public class Jugador extends Persona {
    
    private String direccion;
    private String telefono;

    public Jugador(String direccion, String telefono, String nombre, String cedula, String apellido) {
        super(nombre, cedula, apellido);
        this.direccion = direccion;
        this.telefono = telefono;
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

}
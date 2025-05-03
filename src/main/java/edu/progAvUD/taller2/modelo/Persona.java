package edu.progAvUD.taller2.modelo;

/**
 * Clase base que representa a una persona participante en el juego.
 * Incluye atributos comunes a Jugador y Crupier.
 *
 * @author Andres Felipe
 */
public class Persona {

    /**
     * Nombre de pila de la persona.
     */
    protected String nombre;

    /**
     * Documento de identidad de la persona.
     */
    protected String cedula;

    /**
     * Apellido de la persona.
     */
    protected String apellido;

    /**
     * Construye una Persona con nombre, cédula y apellido.
     *
     * @param nombre   Nombre de pila.
     * @param cedula   Documento de identidad.
     * @param apellido Apellido.
     */
    public Persona(String nombre, String cedula, String apellido) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.apellido = apellido;
    }

    /**
     * Construye una Persona vacía (sin inicializar campos).
     */
    public Persona() {
    }

    /**
     * Construye una Persona únicamente con cédula.
     * Útil para operaciones donde solo se conoce el identificador.
     *
     * @param cedula Documento de identidad.
     */
    public Persona(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Obtiene el nombre de pila.
     *
     * @return Nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre de pila.
     *
     * @param nombre Nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la cédula.
     *
     * @return Documento de identidad.
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Asigna la cédula.
     *
     * @param cedula Documento de identidad a asignar.
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Obtiene el apellido.
     *
     * @return Apellido de la persona.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Asigna el apellido.
     *
     * @param apellido Apellido a asignar.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
package edu.progAvUD.taller2.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Esta clase es la encargada de serializar y deserializar al crupier para las
 * rondas del juego. Permite guardar y cargar objetos de tipo Persona utilizando
 * streams de objetos.
 *
 * @author Andres Felipe
 */
public class Serializacion {

    private FileOutputStream fileOutSerializacion;
    private ObjectOutputStream salidaSerializacion;
    private FileInputStream fileInSerializacion;
    private ObjectInputStream entradaSerializacion;

    /**
     * Crea la instancia inicializando los flujos de entrada y salida para el
     * archivo.
     *
     * @param archivo Archivo en el que se guardará o leerá la información
     * serializada.
     * @throws FileNotFoundException Si el archivo no existe.
     * @throws IOException Si ocurre un error al abrir los flujos.
     */
    public Serializacion(File archivo) throws FileNotFoundException, IOException {
        fileOutSerializacion = new FileOutputStream(archivo);
        salidaSerializacion = new ObjectOutputStream(fileOutSerializacion);
        fileInSerializacion = new FileInputStream(archivo);
        entradaSerializacion = new ObjectInputStream(fileInSerializacion);
    }

    /**
     * Cierra el flujo de salida de objetos.
     *
     * @throws IOException Si ocurre un error al cerrar el flujo.
     */
    public void cerrarArchivoSerializadoOut() throws IOException {
        salidaSerializacion.close();
    }

    /**
     * Cierra el flujo de entrada de objetos.
     *
     * @throws IOException Si ocurre un error al cerrar el flujo.
     */
    public void cerrarArchivoSerializadoIn() throws IOException {
        entradaSerializacion.close();
    }

    /**
     * Escribe un objeto Persona en el archivo serializado.
     *
     * @param persona Objeto Persona a serializar.
     * @throws IOException Si ocurre un error al escribir el objeto.
     */
    public void escribirArchivoSerializado(Persona persona) throws IOException {
        salidaSerializacion.writeObject(persona);
    }

    /**
     * Lee un objeto Persona del archivo serializado.
     *
     * @return Objeto Persona deserializado.
     * @throws IOException Si ocurre un error de lectura.
     * @throws ClassNotFoundException Si la clase del objeto no se encuentra.
     */
    public Persona leerArchivoSerializado() throws IOException, ClassNotFoundException {
        Persona persona = (Persona) entradaSerializacion.readObject();
        return persona;
    }

    public FileOutputStream getFileOutSerializacion() {
        return fileOutSerializacion;
    }

    public void setFileOutSerializacion(FileOutputStream fileOutSerializacion) {
        this.fileOutSerializacion = fileOutSerializacion;
    }

    public ObjectOutputStream getSalidaSerializacion() {
        return salidaSerializacion;
    }

    public void setSalidaSerializacion(ObjectOutputStream salidaSerializacion) {
        this.salidaSerializacion = salidaSerializacion;
    }

    public FileInputStream getFileInSerializacion() {
        return fileInSerializacion;
    }

    public void setFileInSerializacion(FileInputStream fileInSerializacion) {
        this.fileInSerializacion = fileInSerializacion;
    }

    public ObjectInputStream getEntradaSerializacion() {
        return entradaSerializacion;
    }

    public void setEntradaSerializacion(ObjectInputStream entradaSerializacion) {
        this.entradaSerializacion = entradaSerializacion;
    }
}

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
        fileInSerializacion = new FileInputStream(archivo);
        entradaSerializacion = new ObjectInputStream(fileInSerializacion);
    }
    
    public Serializacion(File archivo1, String a) throws FileNotFoundException, IOException {
        fileOutSerializacion = new FileOutputStream(archivo1);
        salidaSerializacion = new ObjectOutputStream(fileOutSerializacion);
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

    /**
     * Obtiene el FileOutputStream asociado.
     *
     * @return Stream de salida para escritura de bytes.
     */
    public FileOutputStream getFileOutSerializacion() {
        return fileOutSerializacion;
    }

    /**
     * Asigna un nuevo FileOutputStream para serialización.
     *
     * @param fileOutSerializacion Stream de salida a asignar.
     */
    public void setFileOutSerializacion(FileOutputStream fileOutSerializacion) {
        this.fileOutSerializacion = fileOutSerializacion;
    }

    /**
     * Obtiene el ObjectOutputStream asociado para escribir objetos.
     *
     * @return Stream de objeto para serialización.
     */
    public ObjectOutputStream getSalidaSerializacion() {
        return salidaSerializacion;
    }

    /**
     * Asigna un nuevo ObjectOutputStream para serialización.
     *
     * @param salidaSerializacion Stream de objeto a asignar.
     */
    public void setSalidaSerializacion(ObjectOutputStream salidaSerializacion) {
        this.salidaSerializacion = salidaSerializacion;
    }

    /**
     * Obtiene el FileInputStream asociado.
     *
     * @return Stream de entrada para lectura de bytes.
     */
    public FileInputStream getFileInSerializacion() {
        return fileInSerializacion;
    }

    /**
     * Asigna un nuevo FileInputStream para deserialización.
     *
     * @param fileInSerializacion Stream de entrada a asignar.
     */
    public void setFileInSerializacion(FileInputStream fileInSerializacion) {
        this.fileInSerializacion = fileInSerializacion;
    }

    /**
     * Obtiene el ObjectInputStream asociado para leer objetos.
     *
     * @return Stream de objeto para deserialización.
     */
    public ObjectInputStream getEntradaSerializacion() {
        return entradaSerializacion;
    }

    /**
     * Asigna un nuevo ObjectInputStream para deserialización.
     *
     * @param entradaSerializacion Stream de objeto a asignar.
     */
    public void setEntradaSerializacion(ObjectInputStream entradaSerializacion) {
        this.entradaSerializacion = entradaSerializacion;
    }
}
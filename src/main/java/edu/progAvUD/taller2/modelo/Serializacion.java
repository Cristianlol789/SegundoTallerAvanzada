package edu.progAvUD.taller2.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Andres Felipe
 */
public class Serializacion {

    private FileOutputStream fileOutSerializacion;
    private ObjectOutputStream salidaSerializacion;
    private FileInputStream fileInSerializacion;
    private ObjectInputStream entradaSerializacion;

    public Serializacion(File archivo) throws FileNotFoundException, IOException {
        fileOutSerializacion = new FileOutputStream(archivo);
        salidaSerializacion = new ObjectOutputStream(fileOutSerializacion);
        fileInSerializacion = new FileInputStream(archivo);
        entradaSerializacion = new ObjectInputStream(fileInSerializacion);
    }

    public void cerrarArchivoSerializadoOut() throws IOException {
        salidaSerializacion.close();
    }

    public void cerrarArchivoSerializadoIn() throws IOException {
        entradaSerializacion.close();
    }

    public void escribirArchivoSerializado(Persona persona) throws IOException {
        salidaSerializacion.writeObject(persona);
    }

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

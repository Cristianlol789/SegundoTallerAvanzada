package edu.progAvUD.taller2.modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Andres Felipe
 */
public class Serializacion {

    private FileOutputStream fileOut;
    private ObjectOutputStream salida;
    private FileInputStream fileIn;
    private ObjectInputStream entrada;

    public Serializacion() {

    }

    public FileOutputStream getFileOut() {
        return fileOut;
    }

    public void setFileOut(FileOutputStream fileOut) {
        this.fileOut = fileOut;
    }

    public ObjectOutputStream getSalida() {
        return salida;
    }

    public void setSalida(ObjectOutputStream salida) {
        this.salida = salida;
    }

    public FileInputStream getFileIn() {
        return fileIn;
    }

    public void setFileIn(FileInputStream fileIn) {
        this.fileIn = fileIn;
    }

    public ObjectInputStream getEntrada() {
        return entrada;
    }

    public void setEntrada(ObjectInputStream entrada) {
        this.entrada = entrada;
    }

}

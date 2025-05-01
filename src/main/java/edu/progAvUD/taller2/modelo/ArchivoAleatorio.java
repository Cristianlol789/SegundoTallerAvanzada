package edu.progAvUD.taller2.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 *
 * @author Cristianlol789
 */
public class ArchivoAleatorio {

    private String numeroRonda;
    private String ganador;
    private File fl;
    RandomAccessFile archivo;

    public ArchivoAleatorio(File archivo2) throws FileNotFoundException {
        this.numeroRonda = numeroRonda;
        this.ganador = ganador;
        this.fl = archivo2;
        archivo = new RandomAccessFile(fl, "rw");
    }

    public String getNumeroRonda() {
        return numeroRonda;
    }

    public void setNumeroRonda(String numeroRonda) {
        this.numeroRonda = numeroRonda;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public File getFl() {
        return fl;
    }

    public void setFl(File fl) {
        this.fl = fl;
    }

    public RandomAccessFile getArchivo() {
        return archivo;
    }

    public void setArchivo(RandomAccessFile archivo) {
        this.archivo = archivo;
    }

}
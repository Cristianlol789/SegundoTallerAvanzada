package edu.progAvUD.taller2.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * La clase ConexionArchivoAleatorio permite manejar un archivo de acceso aleatorio
 * donde se registran el número de ronda y el ganador correspondiente.
 * Proporciona métodos para escribir información en el archivo y acceder a sus propiedades.
 *
 * @author Cristianlol789
 */
public class ConexionArchivoAleatorio {

    /**
     * Número de la ronda actual que se va a registrar.
     */
    private String numeroRonda;

    /**
     * Nombre o identificación del ganador de la ronda actual.
     */
    private String ganador;

    /**
     * Archivo físico donde se almacenan los datos.
     */
    private File fl;

    /**
     * Stream de acceso aleatorio para leer/escribir en el archivo.
     */
    private RandomAccessFile archivo;

    /**
     * Construye una instancia que opera sobre el archivo dado.
     *
     * @param archivo2 Archivo de tipo File donde se guardarán los datos.
     * @throws FileNotFoundException Si el archivo especificado no existe o no
     * puede abrirse.
     */
    public ConexionArchivoAleatorio(File archivo2) throws FileNotFoundException {
        this.fl = archivo2;
        this.archivo = new RandomAccessFile(fl, "rw");
    }

    /**
     * Escribe en el archivo el número de ronda y la información del ganador.
     * Cada llamada añade al final del archivo dos cadenas en formato UTF.
     *
     * @param ronda Cadena que representa el número o identificador de la ronda.
     * @param info Cadena con el nombre o datos del ganador.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void escribirArchivoAleatorio(String ronda, String info) throws IOException {
        archivo.writeUTF(ronda);
        archivo.writeUTF(info);
    }

    /**
     * Obtiene el número de ronda almacenado en esta instancia.
     *
     * @return Cadena con el número de ronda.
     */
    public String getNumeroRonda() {
        return numeroRonda;
    }

    /**
     * Asigna el número de ronda a esta instancia.
     *
     * @param numeroRonda Cadena que representa el número de ronda.
     */
    public void setNumeroRonda(String numeroRonda) {
        this.numeroRonda = numeroRonda;
    }

    /**
     * Obtiene el nombre o identificación del ganador.
     *
     * @return Cadena con el ganador.
     */
    public String getGanador() {
        return ganador;
    }

    /**
     * Asigna el nombre o identificación del ganador.
     *
     * @param ganador Cadena con el nombre del ganador.
     */
    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    /**
     * Obtiene el objeto File asociado a esta instancia.
     *
     * @return Objeto File que representa el archivo físico.
     */
    public File getFl() {
        return fl;
    }

    /**
     * Asigna el objeto File donde se almacenarán los datos.
     *
     * @param fl Objeto File a asociar.
     */
    public void setFl(File fl) {
        this.fl = fl;
    }

    /**
     * Obtiene el RandomAccessFile utilizado para lectura/escritura.
     *
     * @return Stream de acceso aleatorio al archivo.
     */
    public RandomAccessFile getArchivo() {
        return archivo;
    }

    /**
     * Asigna un nuevo RandomAccessFile para lectura/escritura.
     *
     * @param archivo Stream de acceso aleatorio a asignar.
     */
    public void setArchivo(RandomAccessFile archivo) {
        this.archivo = archivo;
    }
}
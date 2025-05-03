package edu.progAvUD.taller2.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * La clase ConexionPropiedades facilita la lectura de archivos de propiedades
 * en formato key-value mediante un FileInputStream.
 * Proporciona métodos para cargar y obtener dichas propiedades.
 *
 * @author Andres Felipe
 */
public class ConexionPropiedades {

    /**
     * Stream de entrada para el archivo de propiedades.
     */
    private FileInputStream fileInPropiedades;

    /**
     * Construye una instancia inicializando el FileInputStream
     * con el archivo de propiedades proporcionado.
     *
     * @param archivo Archivo de tipo File que contiene las propiedades.
     * @throws FileNotFoundException Si el archivo no existe o no puede abrirse.
     */
    public ConexionPropiedades(File archivo) throws FileNotFoundException {
        this.fileInPropiedades = new FileInputStream(archivo);
    }
    
    /**
     * Carga las propiedades desde el stream y lo cierra al finalizar.
     *
     * @return Objeto Properties con las claves y valores leídos.
     * @throws IOException Si ocurre un error al leer o cerrar el archivo.
     */
    public Properties cargarPropiedades() throws IOException {
        Properties propiedades = new Properties();
        propiedades.load(fileInPropiedades);
        fileInPropiedades.close();
        return propiedades;
    }
    
}
package edu.progAvUD.taller2.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Andres Felipe
 */
public class ConexionPropiedades {

    private FileInputStream fileIn;

    public ConexionPropiedades(File archivo) throws Exception{
        this.fileIn = new FileInputStream(archivo);
    }
    
    public Properties cargarPropiedades() throws IOException{
        Properties propiedades = new Properties();
        propiedades.load(fileIn);
        fileIn.close();
        return propiedades;
    }
    
}

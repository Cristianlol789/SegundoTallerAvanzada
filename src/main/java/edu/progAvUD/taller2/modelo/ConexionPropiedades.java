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

    private FileInputStream fileInPropiedades;

    public ConexionPropiedades(File archivo) throws Exception{
        this.fileInPropiedades = new FileInputStream(archivo);
    }
    
    public Properties cargarPropiedades() throws IOException{
        Properties propiedades = new Properties();
        propiedades.load(fileInPropiedades);
        fileInPropiedades.close();
        return propiedades;
    }
    
}

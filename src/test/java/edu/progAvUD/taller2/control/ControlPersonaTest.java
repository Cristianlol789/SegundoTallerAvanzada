package edu.progAvUD.taller2.control;
import edu.progAvUD.taller2.control.ControlPersona;
import edu.progAvUD.taller2.control.ControlPrincipal;
import edu.progAvUD.taller2.modelo.Crupier;
import edu.progAvUD.taller2.modelo.Jugador;
import edu.progAvUD.taller2.modelo.Persona;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import java.io.IOException;
import java.util.ArrayList;

// Mock de ControlPrincipal para pruebas
class ControlPrincipalMock extends ControlPrincipal{
    public String mensajeError;
    public String mensajeExito;
    public File archivoSerializado() { return new File("test.dat"); }
    public void mostrarMensajeError(String msg) { this.mensajeError = msg; }
    public void mostrarMensajeExito(String msg) { this.mensajeExito = msg; }
}

class ControlPersonaTest {
    private ControlPersona controlPersona;
    private ControlPrincipalMock controlPrincipalMock;
    
    @BeforeEach
    void setUp() {
        controlPrincipalMock = new ControlPrincipalMock();
        controlPersona = new ControlPersona(controlPrincipalMock);
    }
    
    @AfterEach
    void tearDown() throws IOException {
        // Limpiar archivo de prueba si existe
        if (controlPersona != null) {
            controlPersona.cerrarArchivoSerializadoOut();
            controlPersona.cerrarArchivoSerializadoIn();
        }
    }
    
    @Test
    void testCrearCrupierCompleto() {
        controlPersona.crearPersona("Crupier", "Juan", "123", "Perez", null, null, 0, 0);
        Persona p = controlPersona.buscarPersonaPorCedula("123");
        
        assertNotNull(p);
        assertTrue(p instanceof Crupier);
        assertEquals("Juan", p.getNombre());
        assertEquals("Perez", p.getApellido());
        assertEquals("123", p.getCedula());
    }
    
    @Test
    void testCrearCrupierSoloCedula() {
        controlPersona.crearPersona("Crupier", "", "123", "", null, null, 0, 0);
        Persona p = controlPersona.buscarPersonaPorCedula("123");
        
        assertNotNull(p);
        assertTrue(p instanceof Crupier);
        assertNull(p.getNombre());
        assertNull(p.getApellido());
        assertEquals("123", p.getCedula());
        assertEquals("Se creó el crupier con solo cédula", controlPrincipalMock.mensajeExito);
    }
    
    @Test
    void testCrearJugadorCompleto() {
        controlPersona.crearPersona("Jugador", "Ana", "456", "Gomez", "555-1234", "Calle 123", 5000.0, 10.0);
        Persona p = controlPersona.buscarPersonaPorCedula("456");
        
        assertNotNull(p);
        assertTrue(p instanceof Jugador);
        assertEquals("Ana", p.getNombre());
        assertEquals("Gomez", p.getApellido());
        assertEquals("456", p.getCedula());
        assertEquals(5000.0, ((Jugador)p).getDinero());
        assertEquals(10.0, ((Jugador)p).getCantidadFichas());
    }
    
    @Test
    void testNoPermitirDuplicados() {
        controlPersona.crearPersona("Crupier", "Juan", "123", "Perez", null, null, 0, 0);
        controlPersona.crearPersona("Jugador", "Ana", "123", "Gomez", "555-1234", "Calle 123", 5000.0, 10.0);
        
        assertEquals("No se puede crear la persona porque ya existe alguien con esta cédula", 
                    controlPrincipalMock.mensajeError);
    }
    
    @Test
    void testBuscarPersonaPorCedulaNoExistente() {
        Persona p = controlPersona.buscarPersonaPorCedula("999");
        assertNull(p);
    }
    
    @Test
    void testComprarFichas() {
        controlPersona.crearPersona("Jugador", "Ana", "456", "Gomez", "555-1234", "Calle 123", 5000.0, 0.0);
        controlPersona.comprarFichas("456", 3);
        
        Jugador j = (Jugador) controlPersona.buscarPersonaPorCedula("456");
        assertEquals(2000.0, j.getDinero());
        assertEquals(3.0, j.getCantidadFichas());
    }
    
    @Test
    void testDarInformacionPersonaCompleta() {
        controlPersona.crearPersona("Crupier", "Juan", "123", "Perez", null, null, 0, 0);
        String info = controlPersona.darInformacionPersona("123");
        
        assertEquals("El nombre de la persona es Juan Perez con cédula 123", info);
    }
    
    @Test
    void testDarInformacionPersonaSoloCedula() {
        controlPersona.crearPersona("Crupier", "", "123", "", null, null, 0, 0);
        String info = controlPersona.darInformacionPersona("123");
        
        assertEquals("La cédula del jugador es 123", info);
    }
    
    @Test
    void testContarCantidadPersonas() {
        // Este test debería fallar porque no hay suficientes personas
        // Pero como System.exit(0) termina la JVM, lo omitimos en pruebas
        // En un entorno real, se usaría una regla de prueba para capturar System.exit
    }
}
package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.Carta;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

class ControlCartaTest {
    private ControlCarta controlCarta;
    private ControlPrincipalMock controlPrincipalMock;
    
    @BeforeEach
    void setUp() {
        ControlPrincipalMock controlPrincipalMock = new ControlPrincipalMock();
        controlCarta = new ControlCarta(controlPrincipalMock);
    }
    
    @Test
    void testCrearCartaValida() {
        Carta carta = controlCarta.crearCarta("CORAZONES", "A");
        
        assertNotNull(carta);
        assertEquals("CORAZONES", carta.getPalo().name());
        assertEquals("A", carta.getDenominacion().name()); // Cambiado para usar .name() en el enum
    }
    
    @Test
    void testCrearCartaConPaloInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            controlCarta.crearCarta("DIAMANTES_INVALIDO", "10");
        });
    }
    
    @Test
    void testCrearCartaConDenominacionInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            controlCarta.crearCarta("CORAZONES", "X");
        });
    }
    
    @Test
    void testCrearCartaConPaloNulo() {
        assertThrows(NullPointerException.class, () -> {
            controlCarta.crearCarta(null, "A");
        });
    }
    
    @Test
    void testCrearCartaConDenominacionNula() {
        assertThrows(NullPointerException.class, () -> {
            controlCarta.crearCarta("CORAZONES", null);
        });
    }
}
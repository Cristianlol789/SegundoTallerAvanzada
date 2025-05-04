package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.vista.PanelCarta;
import edu.progAvUD.taller2.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JPanel;

/**
 * Este es el controlEncargado de comunicarse con la ventanaPrincipal y el
 * contronPrincipal ademas es el encargado de tener el ActionListener de los
 * botones que se encuentran en la ventana
 *
 * @author Andres Felipe
 */
public class ControlGrafico implements ActionListener {

    private ControlPrincipal controlPrincipal;
    private VentanaPrincipal ventanaPrincipal;
    private int jugadorQuePrecionoComprar;

    /**
     * Este es el metodo constructor donde se crea el controlGrafico y ademas se
     * crea la ventana y se guarda la instancia del controlPrincipal
     *
     * @param controlprincipal es el parametro para poderse comunicar con el
     * controlPrincipal
     */
    public ControlGrafico(ControlPrincipal controlprincipal) {
        this.controlPrincipal = controlprincipal;
        this.ventanaPrincipal = new VentanaPrincipal(this);
        ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelPrincipal);
        ventanaPrincipal.panelPrincipal.jButtonJugar.setEnabled(false);

        ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.addActionListener(this);
        ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.addActionListener(this);
        ventanaPrincipal.panelPrincipal.jButtonJugar.addActionListener(this);

        ventanaPrincipal.panelMesa.jButtonRepartir.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonPedir.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonPlantarse.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonDoblar.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonDividir.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2.addActionListener(this);

        ventanaPrincipal.panelMesa.jButtonSeguroJugador1.addActionListener(this);
        ventanaPrincipal.panelMesa.jButtonSeguroJugador2.addActionListener(this);

        ventanaPrincipal.dialogComprarFichas.jButtonComprarFichas.addActionListener(this);
    }

    /**
     * Este es el metodo que se encarga de ver si sucede algo con algun boton
     * para luego delegar la funcion correspondiente a este suceso
     *
     * @param e este es el parametro para identificar el respectivo suceso
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores) {
            ventanaPrincipal.setVisible(false);
            controlPrincipal.crearConexionPropiedades();
            ventanaPrincipal.setVisible(true);
            controlPrincipal.cargarJugadores();
            ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.setEnabled(false);
            mostrarBotonJugar();
        }
        if (e.getSource() == ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier) {
            ventanaPrincipal.setVisible(false);
            controlPrincipal.crearConexionPropiedades();
            ventanaPrincipal.setVisible(true);
            controlPrincipal.cargarCrupier();
            ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.setEnabled(false);
            mostrarBotonJugar();
        }
        if (e.getSource() == ventanaPrincipal.panelPrincipal.jButtonJugar) {
            controlPrincipal.conteoJugadores();
            controlPrincipal.seleccionarJugadores();
            ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelMesa);
            ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(false);
            ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(false);
            ventanaPrincipal.panelMesa.jButtonRepartir.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(false);
            ventanaPrincipal.panelMesa.jLabelTextoCantidadApostada1.setVisible(false);
            ventanaPrincipal.panelMesa.jLabelTextoCantidadApostada2.setVisible(false);
            ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador1.setVisible(false);
            ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador2.setVisible(false);

        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1) {
            String cedulaJugador1 = controlPrincipal.darCedulaJugadoresEnPartida("Jugador1");

            jugadorQuePrecionoComprar = 1;
            if (0 == controlPrincipal.getCantidadFichasJugadorPorCedula(cedulaJugador1)) {
                ventanaPrincipal.dialogComprarFichas.jLabelCantidadDinero.setText("$" + controlPrincipal.getCantidadDineroJugadorPorCedula(cedulaJugador1));
                ventanaPrincipal.dialogComprarFichas.setVisible(true);
            } else {
                int cantidadAApostar = (int) ventanaPrincipal.panelMesa.jSpinnerCantidadFichasApostar1.getValue();
                if (controlPrincipal.verificarFichasAApostar(cedulaJugador1, cantidadAApostar)) {
                    controlPrincipal.setFichasApostadasJugador1(cantidadAApostar);

                    String cedula = controlPrincipal.darCedulaJugadoresEnPartida("Jugador1");
                    double fichasJugador = controlPrincipal.getCantidadFichasJugadorPorCedula(cedula);
                    double fichasTotales = fichasJugador - cantidadAApostar;
                    controlPrincipal.setFichasJugadorPorCedula(cedula, fichasTotales);

                    ventanaPrincipal.panelMesa.jSpinnerCantidadFichasApostar1.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabelTextoApostar1.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabelCantidadFichasJugador1.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabeltTextoCantidadFichasJugador1.setVisible(false);
                    ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1.setVisible(false);

                    ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador1.setText(cantidadAApostar + "");
                    ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador1.setVisible(true);
                    ventanaPrincipal.panelMesa.jLabelTextoCantidadApostada1.setVisible(true);

                    mostrarMensajeExito("El jugador 1 ha apostado " + cantidadAApostar + " fichas");

                    if (!ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1.isVisible() && !ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2.isVisible()) {
                        ventanaPrincipal.panelMesa.jButtonRepartir.setEnabled(true);
                    }

                } else {
                    mostrarMensajeError("No se tiene la cantidad de fichas para apostar");
                }
            }
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2) {
            String cedulaJugador2 = controlPrincipal.darCedulaJugadoresEnPartida("Jugador2");
            jugadorQuePrecionoComprar = 2;
            if (0 == controlPrincipal.getCantidadFichasJugadorPorCedula(cedulaJugador2)) {
                ventanaPrincipal.dialogComprarFichas.jLabelCantidadDinero.setText("$" + controlPrincipal.getCantidadDineroJugadorPorCedula(cedulaJugador2));
                ventanaPrincipal.dialogComprarFichas.setVisible(true);
            } else {
                int cantidadAApostar = (int) ventanaPrincipal.panelMesa.jSpinnerCantidadFichasApostar2.getValue();
                if (controlPrincipal.verificarFichasAApostar(cedulaJugador2, cantidadAApostar)) {
                    controlPrincipal.setFichasApostadasJugador2(cantidadAApostar);

                    String cedula = controlPrincipal.darCedulaJugadoresEnPartida("Jugador2");
                    double fichasJugador = controlPrincipal.getCantidadFichasJugadorPorCedula(cedula);
                    double fichasTotales = fichasJugador - cantidadAApostar;
                    controlPrincipal.setFichasJugadorPorCedula(cedula, fichasTotales);

                    ventanaPrincipal.panelMesa.jSpinnerCantidadFichasApostar2.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabelTextoApostar2.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabelCantidadFichasJugador2.setVisible(false);
                    ventanaPrincipal.panelMesa.jLabeltTextoCantidadFichasJugador2.setVisible(false);
                    ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2.setVisible(false);

                    ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador2.setText(cantidadAApostar + "");
                    ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador2.setVisible(true);
                    ventanaPrincipal.panelMesa.jLabelTextoCantidadApostada2.setVisible(true);

                    mostrarMensajeExito("El jugador 2 ha apostado " + cantidadAApostar + " fichas");

                    if (!ventanaPrincipal.panelMesa.jButtonApostarFichasJugador1.isVisible() && !ventanaPrincipal.panelMesa.jButtonApostarFichasJugador2.isVisible()) {
                        ventanaPrincipal.panelMesa.jButtonRepartir.setEnabled(true);
                    }
                } else {
                    mostrarMensajeError("No se tiene la cantidad de fichas para apostar");
                }
            }
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonDividir) {
            if ("Jugador1".equals(controlPrincipal.getTurnoJugador())) {
                ventanaPrincipal.dividirPanelCartasJugador1();
                controlPrincipal.dividirMazoJugador("Jugador1");
                ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(false);
                controlPrincipal.setTurnoMazoJugador1("Jugador1");
                ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador 1 Mazo 1");
            } else if ("Jugador2".equals(controlPrincipal.getTurnoJugador())) {
                ventanaPrincipal.dividirPanelCartasJugador2();
                controlPrincipal.dividirMazoJugador("Jugador2");
                ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(false);
                controlPrincipal.setTurnoMazoJugador2("Jugador2");
                ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador 2 Mazo 1");

            }
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonDoblar) {
            controlPrincipal.doblarApuesta();
            ventanaPrincipal.mostrarMensajeExito("Se ha doblado la apuesta");
            String turnoJugador = controlPrincipal.getTurnoJugador();
            if (turnoJugador.equals("Jugador1")) {
                controlPrincipal.setTurnoJugador("Jugador2");
                ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador2");
            } else if (turnoJugador.equals("Jugador2")) {
                controlPrincipal.setTurnoJugador("Crupier");
                ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(false);
                ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(false);
                ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(false);
                ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Crupier");
                controlPrincipal.darTurnoCrupier();
            } else if (turnoJugador.equals("Crupier")) {
                controlPrincipal.setTurnoJugador("Jugador1");
                ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador1");
            }
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonPedir) {
            String turnoJugador = controlPrincipal.getTurnoJugador();
            if (!controlPrincipal.sumarCantidadCartasJugadorActivo(turnoJugador)) {
                controlPrincipal.darCartas(turnoJugador, controlPrincipal.getTurnoMazoJugador1(), controlPrincipal.getTurnoMazoJugador2());
            } else {
                if (turnoJugador.equals("Jugador1")) {
                    if ("".equals(controlPrincipal.getTurnoMazoJugador1())) {
                        controlPrincipal.setTurnoJugador("Jugador2");
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador2");

                        double fichasapostadas = 0;
                        if ("Jugador1".equals(controlPrincipal.getTurnoJugador())) {
                            fichasapostadas = controlPrincipal.getFichasApostadasJugador1();
                        } else if ("Jugador2".equals(controlPrincipal.getTurnoJugador())) {
                            fichasapostadas = controlPrincipal.getFichasApostadasJugador2();
                        }

                        if (controlPrincipal.verificarDoblarApueta(controlPrincipal.getTurnoJugador(), fichasapostadas)) {
                            ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(true);
                        }
                    } else if ("Jugador1".equals(controlPrincipal.getTurnoMazoJugador1())) {
                        controlPrincipal.setTurnoMazoJugador1("Jugador1NuevoMazo");
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador1 Mazo 2");

                    } else if ("Jugador1NuevoMazo".equals(controlPrincipal.getTurnoMazoJugador1())) {
                        controlPrincipal.setTurnoMazoJugador1("");
                        controlPrincipal.setTurnoJugador("Jugador2");
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador2");
                    }

                } else if (turnoJugador.equals("Jugador2")) {
                    if ("".equals(controlPrincipal.getTurnoMazoJugador2())) {
                        controlPrincipal.setTurnoJugador("Crupier");
                        ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(false);
                        ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(false);
                        ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(false);
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Crupier");
                        controlPrincipal.darTurnoCrupier();
                    } else if ("Jugador2".equals(controlPrincipal.getTurnoMazoJugador2())) {
                        controlPrincipal.setTurnoMazoJugador2("Jugador2NuevoMazo");
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador2 Mazo 2");

                    } else if ("Jugador2NuevoMazo".equals(controlPrincipal.getTurnoMazoJugador2())) {
                        controlPrincipal.setTurnoMazoJugador2("");
                        controlPrincipal.setTurnoJugador("Crupier");
                        ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(false);
                        ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(false);
                        ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(false);
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Crupier");
                        controlPrincipal.darTurnoCrupier();
                    }
                } else if (turnoJugador.equals("Crupier")) {
                    controlPrincipal.setTurnoJugador("Jugador1");
                    ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador1");

                    double fichasapostadas = 0;
                    if ("Jugador1".equals(controlPrincipal.getTurnoJugador())) {
                        fichasapostadas = controlPrincipal.getFichasApostadasJugador1();
                    } else if ("Jugador2".equals(controlPrincipal.getTurnoJugador())) {
                        fichasapostadas = controlPrincipal.getFichasApostadasJugador2();
                    }

                    if (controlPrincipal.verificarDoblarApueta(controlPrincipal.getTurnoJugador(), fichasapostadas)) {
                        ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(true);
                    }

                    if (controlPrincipal.verificarCartasIguales(controlPrincipal.getTurnoJugador())) {
                        ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(true);
                    }
                }
            }

            if (controlPrincipal.sumarCantidadCartasJugadorActivo(turnoJugador)) {
                if (turnoJugador.equals("Jugador1")) {
                    if ("".equals(controlPrincipal.getTurnoMazoJugador1())) {
                        controlPrincipal.setTurnoJugador("Jugador2");
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador2");
                    } else if ("Jugador1".equals(controlPrincipal.getTurnoMazoJugador1())) {
                        controlPrincipal.setTurnoMazoJugador1("Jugador1NuevoMazo");
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador1 Mazo 2");
                    } else if ("Jugador1NuevoMazo".equals(controlPrincipal.getTurnoMazoJugador1())) {
                        controlPrincipal.setTurnoMazoJugador1("");
                        controlPrincipal.setTurnoJugador("Jugador2");
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador2");
                    }
                } else if (turnoJugador.equals("Jugador2")) {
                    if ("".equals(controlPrincipal.getTurnoMazoJugador2())) {
                        controlPrincipal.setTurnoJugador("Crupier");
                        ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(false);
                        ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(false);
                        ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(false);
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Crupier");
                        controlPrincipal.darTurnoCrupier();
                        controlPrincipal.darTurnoCrupier();
                    } else if ("Jugador2".equals(controlPrincipal.getTurnoMazoJugador2())) {
                        controlPrincipal.setTurnoMazoJugador2("Jugador2NuevoMazo");
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador2 Mazo 2");
                    } else if ("Jugador2NuevoMazo".equals(controlPrincipal.getTurnoMazoJugador2())) {
                        controlPrincipal.setTurnoMazoJugador2("");
                        controlPrincipal.setTurnoJugador("Crupier");
                        ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(false);
                        ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(false);
                        ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(false);
                        ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Crupier");
                        controlPrincipal.darTurnoCrupier();
                    }
                } else if (turnoJugador.equals("Crupier")) {
                    controlPrincipal.setTurnoJugador("Jugador1");
                    ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador1");
                }
            }

            //////////////////////////////////////////////////////
            if (controlPrincipal.verificarBotonSeguro()) {
                if ("Jugador1".equals(controlPrincipal.getTurnoJugador())) {
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setEnabled(true);
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setEnabled(false);
                } else if ("Jugador2".equals(controlPrincipal.getTurnoJugador())) {
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setEnabled(false);
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setEnabled(true);
                } else if ("Crupier".equals(controlPrincipal.getTurnoJugador())) {
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(false);
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(false);
                }
            }
            if (controlPrincipal.verificarCartasIguales(controlPrincipal.getTurnoJugador())) {
                ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(true);
            }
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonPlantarse) {
            String turnoJugador = controlPrincipal.getTurnoJugador();
            if (turnoJugador.equals("Jugador1")) {
                if ("".equals(controlPrincipal.getTurnoMazoJugador1())) {
                    controlPrincipal.setTurnoJugador("Jugador2");
                    ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador2");

                    double fichasapostadas = 0;
                    if ("Jugador1".equals(controlPrincipal.getTurnoJugador())) {
                        fichasapostadas = controlPrincipal.getFichasApostadasJugador1();
                    } else if ("Jugador2".equals(controlPrincipal.getTurnoJugador())) {
                        fichasapostadas = controlPrincipal.getFichasApostadasJugador2();
                    }

                    if (controlPrincipal.verificarDoblarApueta(controlPrincipal.getTurnoJugador(), fichasapostadas)) {
                        ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(true);
                    }
                } else if ("Jugador1".equals(controlPrincipal.getTurnoMazoJugador1())) {
                    controlPrincipal.setTurnoMazoJugador1("Jugador1NuevoMazo");
                    ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador1 Mazo 2");

                } else if ("Jugador1NuevoMazo".equals(controlPrincipal.getTurnoMazoJugador1())) {
                    controlPrincipal.setTurnoMazoJugador1("");
                    controlPrincipal.setTurnoJugador("Jugador2");
                    ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador2");
                }

            } else if (turnoJugador.equals("Jugador2")) {
                if ("".equals(controlPrincipal.getTurnoMazoJugador2())) {
                    controlPrincipal.setTurnoJugador("Crupier");
                    ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Crupier");
                    controlPrincipal.darTurnoCrupier();
                } else if ("Jugador2".equals(controlPrincipal.getTurnoMazoJugador2())) {
                    controlPrincipal.setTurnoMazoJugador2("Jugador1NuevoMazo");
                    ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Jugador2 Mazo 2");

                } else if ("Jugador2NuevoMazo".equals(controlPrincipal.getTurnoMazoJugador2())) {
                    controlPrincipal.setTurnoMazoJugador2("");
                    controlPrincipal.setTurnoJugador("Crupier");
                    ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Crupier");
                    controlPrincipal.darTurnoCrupier();
                }
            } else if (turnoJugador.equals("Crupier")) {
                controlPrincipal.setTurnoJugador("Jugador1");
                ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText("Crupier");
                double fichasapostadas = 0;
                if ("Jugador1".equals(controlPrincipal.getTurnoJugador())) {
                    fichasapostadas = controlPrincipal.getFichasApostadasJugador1();
                } else if ("Jugador2".equals(controlPrincipal.getTurnoJugador())) {
                    fichasapostadas = controlPrincipal.getFichasApostadasJugador2();
                }

                if (controlPrincipal.verificarDoblarApueta(controlPrincipal.getTurnoJugador(), fichasapostadas)) {
                    ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(true);
                }

            }


            /////////////////////////////////////////////////////////////////
            if (controlPrincipal.verificarBotonSeguro()) {
                if ("Jugador1".equals(controlPrincipal.getTurnoJugador())) {
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setEnabled(true);
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setEnabled(false);
                } else if ("Jugador2".equals(controlPrincipal.getTurnoJugador())) {
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setEnabled(false);
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setEnabled(true);
                } else if ("Crupier".equals(controlPrincipal.getTurnoJugador())) {
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(false);
                    ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(false);
                }
            }
            if (controlPrincipal.verificarCartasIguales(controlPrincipal.getTurnoJugador())) {
                ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(true);
            }
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonRepartir) {
            controlPrincipal.crearMazo();

            for (int i = 1; i <= 2; i++) {
                controlPrincipal.darCartas("Jugador1", controlPrincipal.getTurnoMazoJugador1(), controlPrincipal.getTurnoMazoJugador2());
                controlPrincipal.darCartas("Jugador2", controlPrincipal.getTurnoMazoJugador1(), controlPrincipal.getTurnoMazoJugador2());
                controlPrincipal.darCartas("Crupier", controlPrincipal.getTurnoMazoJugador1(), controlPrincipal.getTurnoMazoJugador2());
            }
            ventanaPrincipal.panelMesa.jButtonRepartir.setEnabled(false);
            ventanaPrincipal.panelMesa.jButtonPlantarse.setEnabled(true);
            ventanaPrincipal.panelMesa.jButtonPedir.setEnabled(true);

            controlPrincipal.setTurnoJugador("Jugador1");
            ventanaPrincipal.panelMesa.jLabelTurnoJugador.setText(controlPrincipal.getTurnoJugador());

            double fichasapostadas = 0;
            if ("Jugador1".equals(controlPrincipal.getTurnoJugador())) {
                fichasapostadas = controlPrincipal.getFichasApostadasJugador1();
            } else if ("Jugador2".equals(controlPrincipal.getTurnoJugador())) {
                fichasapostadas = controlPrincipal.getFichasApostadasJugador2();
            }

            if (controlPrincipal.verificarDoblarApueta(controlPrincipal.getTurnoJugador(), fichasapostadas)) {
                ventanaPrincipal.panelMesa.jButtonDoblar.setEnabled(true);
            }

            if (controlPrincipal.verificarCartasIguales(controlPrincipal.getTurnoJugador())) {
                ventanaPrincipal.panelMesa.jButtonDividir.setEnabled(true);
            }

            if (controlPrincipal.verificarBotonSeguro()) {
                ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(true);
                ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(true);
                ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setEnabled(false);
            }
        }
        if (e.getSource() == ventanaPrincipal.dialogComprarFichas.jButtonComprarFichas) {
            if (jugadorQuePrecionoComprar == 1) {
                int cantidadCompra = (int) ventanaPrincipal.dialogComprarFichas.jSpinnerFichasAComprar.getValue();
                if (controlPrincipal.verificarFichasAComprar(controlPrincipal.darCedulaJugadoresEnPartida("Jugador1"), cantidadCompra)) {
                    mostrarMensajeExito("Se han comprado correctamente");
                    ventanaPrincipal.dialogComprarFichas.dispose();
                } else {
                    mostrarMensajeError("No tiene suficientes fondos");
                }
            } else if (jugadorQuePrecionoComprar == 2) {
                int cantidadCompra = (int) ventanaPrincipal.dialogComprarFichas.jSpinnerFichasAComprar.getValue();
                if (controlPrincipal.verificarFichasAComprar(controlPrincipal.darCedulaJugadoresEnPartida("Jugador2"), cantidadCompra)) {
                    mostrarMensajeExito("Se han comprado correctamente");
                    ventanaPrincipal.dialogComprarFichas.dispose();
                } else {
                    mostrarMensajeError("No tiene suficientes fondos");
                }
            }
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonSeguroJugador1) {
            int cantidadSeguro = ventanaPrincipal.mostrarJOptionPanelSeguro();
            if (cantidadSeguro != (-1)) {
                if (!controlPrincipal.verificarRealizarSeguro(cantidadSeguro)) {
                    ventanaPrincipal.mostrarMensajeError("""
                                                         No se ha podido realizar el seguro por alguna de las siguientes razones: 
                                                         - No cuenta con las suficientes fichas 
                                                         - El seguro supera la mitad de la apuesta realizada""");
                }

                if (controlPrincipal.verificarBotonSeguro()) {
                    if ("Jugador1".equals(controlPrincipal.getTurnoJugador())) {
                        ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(false);
                    } else if ("Jugador2".equals(controlPrincipal.getTurnoJugador())) {
                        ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(false);
                    } else if ("Crupier".equals(controlPrincipal.getTurnoJugador())) {
                        ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(false);
                        ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(false);
                    }
                }
            }
        }
        if (e.getSource() == ventanaPrincipal.panelMesa.jButtonSeguroJugador2) {
            int cantidadSeguro = ventanaPrincipal.mostrarJOptionPanelSeguro();
            if (cantidadSeguro != (-1)) {
                if (!controlPrincipal.verificarRealizarSeguro(cantidadSeguro)) {
                    ventanaPrincipal.mostrarMensajeError("""
                                                         No se ha podido realizar el seguro por alguna de las siguientes razones: 
                                                         - No cuenta con las suficientes fichas 
                                                         - El seguro supera la mitad de la apuesta realizada""");
                }
                if (controlPrincipal.verificarBotonSeguro()) {
                    if ("Jugador1".equals(controlPrincipal.getTurnoJugador())) {
                        ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(false);
                    } else if ("Jugador2".equals(controlPrincipal.getTurnoJugador())) {
                        ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(false);
                    } else if ("Crupier".equals(controlPrincipal.getTurnoJugador())) {
                        ventanaPrincipal.panelMesa.jButtonSeguroJugador1.setVisible(false);
                        ventanaPrincipal.panelMesa.jButtonSeguroJugador2.setVisible(false);
                    }
                }
            }
        }
    }

    /**
     * Este metodo lo que hace es mostrar la carta que esta oculta del crupier
     */
    public void mostrarCartaOculta() {
        ventanaPrincipal.mostrarCartaOculta();
    }

    /**
     * Este metodo es el encarga de mostrar las cartas de cada jugador
     *
     * @param palo es la figura que debera llevar la carta
     * @param denominacion es la cantidad que vale la carta
     * @param duenoCarta es la persona a la que le toco la carta
     */
    public void mostrarCarta(String palo, String denominacion, String duenoCarta, String turnoMazoJugador1, String turnoMazoJugador2) {
        if (denominacion == null) {
            return; // Salir si la denominación es nula
        }

        PanelCarta carta = ventanaPrincipal.crearCarta(denominacionAbreviada(denominacion), palo);
        JPanel panelDestino = obtenerPanelDestino(duenoCarta, turnoMazoJugador1, turnoMazoJugador2);

        if (panelDestino != null) {
            panelDestino.add(carta);
        }

        // Actualizar la vista
        ventanaPrincipal.panelMesa.jPanelCartasJugador1.revalidate();
        ventanaPrincipal.panelMesa.jPanelCartasJugador1.repaint();
    }

    private String denominacionAbreviada(String denominacion) {
        switch (denominacion) {
            case "DOS":
                return "2";
            case "TRES":
                return "3";
            case "CUATRO":
                return "4";
            case "CINCO":
                return "5";
            case "SEIS":
                return "6";
            case "SIETE":
                return "7";
            case "OCHO":
                return "8";
            case "NUEVE":
                return "9";
            case "DIEZ":
                return "10";
            case "J":
                return "J";
            case "Q":
                return "Q";
            case "K":
                return "K";
            case "A":
                return "A";
            default:
                return null; // Manejar caso no válido
        }
    }

    private JPanel obtenerPanelDestino(String duenoCarta, String turnoMazoJugador1, String turnoMazoJugador2) {
        switch (duenoCarta) {
            case "Jugador1":
                if ("".equals(turnoMazoJugador1)) {
                    return ventanaPrincipal.panelMesa.jPanelCartasJugador1; // Cambiado a jPanelCartasJugador2
                } else if ("Jugador1".equals(turnoMazoJugador1)) {
                    return ventanaPrincipal.panel1Jugador1; // O el panel correspondiente para el nuevo mazo
                } else if ("Jugador1NuevoMazo".equals(turnoMazoJugador1)) {
                    return ventanaPrincipal.panel2Jugador1; // O el panel correspondiente para el nuevo mazo
                }
                break;
            case "Jugador2":
                if ("".equals(turnoMazoJugador2)) {
                    return ventanaPrincipal.panelMesa.jPanelCartasJugador2; // Cambiado a jPanelCartasJugador2
                } else if ("Jugador2".equals(turnoMazoJugador2)) {
                    return ventanaPrincipal.panel1Jugador2; // O el panel correspondiente para el nuevo mazo
                } else if ("Jugador2NuevoMazo".equals(turnoMazoJugador2)) {
                    return ventanaPrincipal.panel2Jugador2; // O el panel correspondiente para el nuevo mazo
                }
                break;
            case "Crupier":
                return ventanaPrincipal.panelMesa.jPanelCartasCrupier;
            default:
                return null; // Manejar caso no válido
        }
        return null; // Retorno por defecto si no se cumple ninguna condición
    }

    /**
     * Este metodo tapa la segunda carta del crupier a los jugadores
     */
    public void ocultarCartaOculta() {
        ventanaPrincipal.cartaOculta.setVisible(false);
    }

    /**
     * Desbloquea el boton jugar una vez ya se hayan cargado las personas
     */
    public void mostrarBotonJugar() {
        if (!ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesCrupier.isEnabled() && !ventanaPrincipal.panelPrincipal.jButtonCargarPropiedadesJugadores.isEnabled()) {
            ventanaPrincipal.panelPrincipal.jButtonJugar.setEnabled(true);
        }
    }

    /**
     * Este metodo se encarga de cargar en la ventana la informacion de cada
     * jugador
     *
     * @param cedulaJugador1 es la referencia para buscar el jugador
     * @param cantidadFichasJugador1 es la cantidad de ficha que tiene
     * disponibles para la apuesta
     */
    public void mostraDatosJugador1(String cedulaJugador1, double cantidadFichasJugador1) {
        ventanaPrincipal.panelMesa.jLabelCedulaJugador1.setText(cedulaJugador1);
        ventanaPrincipal.panelMesa.jLabelCantidadFichasJugador1.setText(cantidadFichasJugador1 + "");

    }

    /**
     * Este metodo se encarga de cargar en la ventana la informacion de cada
     * jugador
     *
     * @param cedulaJugador2 es la referencia para buscar el jugador
     * @param cantidadFichasJugador2 es la cantidad de ficha que tiene
     * disponibles para la apuesta
     */
    public void mostraDatosJugador2(String cedulaJugador2, double cantidadFichasJugador2) {
        ventanaPrincipal.panelMesa.jLabelCedulaJugador2.setText(cedulaJugador2);
        ventanaPrincipal.panelMesa.jLabelCantidadFichasJugador2.setText(cantidadFichasJugador2 + "");
    }

    /**
     * Este metodo actualiza la ventana para que se vea la cantidad apostada por
     * jugador
     *
     * @param cantidadFichasJugador1 es la cantidad apostada por el jugador 1
     * @param cantidadFichasJugador2 es la cantidad apostada por el jugador 2
     */
    public void actulizarFichasApostadas(double cantidadFichasJugador1, double cantidadFichasJugador2) {
        ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador1.setText(cantidadFichasJugador1 + "");
        ventanaPrincipal.panelMesa.jLabelCantidadFichasApostadasJugador2.setText(cantidadFichasJugador2 + "");
    }

    public int verificarEstadoJOptionPane(int resultado, Object valorSpinner) {
        int valorSpinnerEntero = (int) valorSpinner;
        if (resultado == 0) {
            return valorSpinnerEntero;
        }
        return -1;
    }

    /**
     * Este es el metodo que muestra los errores que suceden durante la
     * ejecucion o los errores que este cometiendo el usuario en el momento
     *
     * @param mensaje es el mensaje a mostrar
     */
    public void mostrarMensajeError(String mensaje) {
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }

    /**
     * Este es el metodo que muestra los aciertos durante la ejecucion, ademas
     * confirma que si se haya echo bien un proceso como cargar personas
     *
     * @param mensaje es el mensaje a mostrar
     */
    public void mostrarMensajeExito(String mensaje) {
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }

    /**
     * Es el metodo que le pide a la ventana un archivo
     *
     * @return el archivo que es necesario
     */
    public File pedirArchivo() {
        return ventanaPrincipal.pedirArchivo();
    }

    /**
     * Es el metodo que le pide a la ventana un archivo
     *
     * @return el archivo que es necesario
     */
    public File pedirArchivoSerializado() throws IOException, NullPointerException {
        return ventanaPrincipal.pedirArchivoPersonaSerializado();
    }

    /**
     * Es el metodo que le pide a la ventana un archivo
     *
     * @return el archivo que es necesario
     */
    public File pedirArchivoAleatorio() throws NullPointerException, IOException {
        return ventanaPrincipal.pedirArchivoAleatorio();
    }
    
    public File buscarArchivoCrupier() throws FileNotFoundException{
        return ventanaPrincipal.pedirArchivoCrupier();
    }
}

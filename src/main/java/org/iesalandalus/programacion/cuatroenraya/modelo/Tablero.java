package org.iesalandalus.programacion.cuatroenraya.modelo;

import javax.naming.OperationNotSupportedException;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
    }

    private boolean columnaVacia(int columna){
        for (int filaActual = 0; filaActual < FILAS; filaActual++) {
            if (casillas[filaActual][columna].estaOcupada()) {
                return false;
            }
        }
        return true;

    }

    public boolean estaVacio() {

        for (int columnaActual = 0; columnaActual < COLUMNAS; columnaActual++) {
            columnaVacia(columnaActual);
            if (columnaVacia(columnaActual)){
                return true;
            }
        }
        return false;
    }

}

package org.iesalandalus.programacion.cuatroenraya.modelo;

import javax.naming.OperationNotSupportedException;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        for (int filas = 0; filas < FILAS; filas++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                casillas[filas][columna] = new Casilla();
            }
        }
    }

    private boolean columnaVacia(int columna) {
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
            if (columnaVacia(columnaActual)) {
                return true;
            }
        }
        return false;
    }

    public boolean columnaLlena(int columna) {
        for (int filaActual = 0; filaActual < COLUMNAS - 1; filaActual++) {
            if (!casillas[filaActual][columna].estaOcupada()) {
                return false;
            }

        }

        return true;
    }

    public boolean estaLleno() {

        for (int columnaActual = 0; columnaActual < COLUMNAS; columnaActual++) {
            columnaLlena(columnaActual);
            if (columnaLlena(columnaActual)) {
                return true;
            }
        }
        return false;
    }

    private void comprobarFicha(Ficha ficha) {
        if (ficha == null) {
            throw new NullPointerException("La ficha no puede ser nula.");
        }
        if (ficha != Ficha.AZUL && ficha != Ficha.VERDE) {
            throw new IllegalArgumentException("FICHA DEBE SER AZUL O VERDE");
        }
    }

    private void comprobarColumna(int columna) throws OperationNotSupportedException {
        if (columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFilaVacia(int columna) {
        for (int filaActual = 0; filaActual < FILAS; filaActual++) {
            if (!casillas[filaActual][columna].estaOcupada()) {
                return filaActual;
            }
        }
        return -1;
    }

    private boolean objetivoAlcanzado(int fichaIgualesConsecutivas) {

        return fichaIgualesConsecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private boolean comprobarHorizontal(int fila, Ficha ficha) {
        int fichaIgualesConsecutivas = 0;
        for (int i = 0; i < COLUMNAS && !objetivoAlcanzado(fichaIgualesConsecutivas); i++) {
            if (casillas[fila][i].estaOcupada() && casillas[fila][i].getFicha().equals(ficha)) {
                fichaIgualesConsecutivas++;
            } else {
                fichaIgualesConsecutivas = 0;
            }
        }
        return objetivoAlcanzado(fichaIgualesConsecutivas);

    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int fichaIgualesConsecutivas = 0;
        for (int i = 0; i < FILAS && !objetivoAlcanzado(fichaIgualesConsecutivas); i++) {
            Ficha colorActual = casillas[i][columna].getFicha();
            if (colorActual == ficha) {
                fichaIgualesConsecutivas++;
            } else {
                fichaIgualesConsecutivas = 0;
            }
            if (fichaIgualesConsecutivas >= 4) {
                return true;
            }
        }
        return objetivoAlcanzado(fichaIgualesConsecutivas);
    }

    private int menor(int fila, int columna) {
        return Math.max(fila, columna);
    }

    public boolean comprobarDiagonalNE(int filaSemilla, int columnaSemilla, Ficha ficha) {
        int desplazamiento = menor(filaSemilla, columnaSemilla);
        int filaInicial = desplazamiento - filaSemilla;
        int columnaInicial = desplazamiento - columnaSemilla;
        int fichaIgualesConsecutivas = 0;

        for (int i = filaInicial, j = columnaInicial; i < FILAS && j < COLUMNAS; i++, j++) {
            if (casillas[i][j].getFicha() == ficha) {
                fichaIgualesConsecutivas++;

            } else {
                fichaIgualesConsecutivas = 0;
            }
            if (objetivoAlcanzado(fichaIgualesConsecutivas)) {
                return true;
            }
        }
        return objetivoAlcanzado(fichaIgualesConsecutivas);
    }

    public boolean comprobarDiagonalNO(int fila, int columna, Ficha ficha) {
        int desplazamiento = fila - COLUMNAS - 1 - columna;
        int filaInicial = fila - desplazamiento;
        int columnaInicial = columna + desplazamiento;
        int fichaIgualesConsecutivas = 0;

        for (int i = filaInicial, j = columnaInicial; i < FILAS && j > 0; i++, j--) {
            if (casillas[i][j].getFicha() == ficha) {
                fichaIgualesConsecutivas++;
                if (fichaIgualesConsecutivas >= 4) {
                    return objetivoAlcanzado(fichaIgualesConsecutivas);
                }
            } else {
                fichaIgualesConsecutivas = 0;
            }
        }
        return objetivoAlcanzado(fichaIgualesConsecutivas);
    }

    private boolean comprobarTirada(int fila, int columna) {
        Ficha ficha = casillas[fila][columna].getFicha();
        return comprobarDiagonalNO(fila, columna, ficha) || comprobarDiagonalNE(fila, columna, ficha) || comprobarHorizontal(fila, ficha) || comprobarVertical(columna, ficha);
    }

    public boolean introducirFicha(int columna, Ficha ficha) throws IllegalArgumentException, OperationNotSupportedException {

        comprobarFicha(ficha);
        comprobarColumna(columna);

        if (columnaLlena(columna)) {
            throw new OperationNotSupportedException("Columna llena.");
        }

        int fila = getPrimeraFilaVacia(columna);
        casillas[fila][columna].setFicha(ficha);

        return comprobarTirada(fila, columna);
    }

    /* values() devuelve un array de los valores de un enum
     * Palo[] palos = Palo.values()
     * Para devolver el array???
     * para generar una carta de un palo random:
     * Palo palo = palo[generator.next.int(palos.lenght)]*/
    @Override
    public String toString() {
        StringBuilder tablero = new StringBuilder();
        for (int i = FILAS - 1; i >= 0; i--) {

            tablero.append("|");
            for (int j = 0; j < COLUMNAS; j++) {
                if (casillas[i][j] != null && casillas[i][j].estaOcupada()) {
                    tablero.append(casillas[i][j].toString());
                } else {
                    tablero.append(" ");
                }
            }
            tablero.append("|\n");
        }
        tablero.append(" -------\n");

        return tablero.toString();
    }
}



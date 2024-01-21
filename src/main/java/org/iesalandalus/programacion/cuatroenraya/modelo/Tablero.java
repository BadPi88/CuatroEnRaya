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

    private boolean columnaVacia(int columna) {
        for (int filaActual = 0; filaActual < FILAS; filaActual++) {
            System.out.println("Numero Fila" + filaActual);
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
        for (int filaActual = 0; filaActual < COLUMNAS; filaActual++) {
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
            throw new NullPointerException("FICHA NO PUEDE SER NULA");
        }

    }

    private void comprobarColumna(int columna) throws OperationNotSupportedException {
        if (columna > COLUMNAS) {
            throw new OperationNotSupportedException("Numero de columna mayor que el permitido");

        } else if (columna < 0) {
            throw new OperationNotSupportedException("Numero de columna menor que el permitido");
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
        for (int i = 0; i < COLUMNAS; i++) {
            Ficha colorActual = casillas[fila][i].getFicha();
            if (colorActual == ficha) {
                fichaIgualesConsecutivas++;
            } else {
                fichaIgualesConsecutivas = 0;
            }
        }
        return objetivoAlcanzado(fichaIgualesConsecutivas);

    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int fichaIgualesConsecutivas = 0;
        for (int i = 0; i < COLUMNAS; i++) {
            Ficha colorActual = casillas[i][columna].getFicha();
            if (colorActual == ficha) {
                fichaIgualesConsecutivas++;
            } else {
                fichaIgualesConsecutivas = 0;
            }
        }
        return objetivoAlcanzado(fichaIgualesConsecutivas);
    }

    private int menor(int fila, int columna) {
        return Math.max(fila, columna);
    }

    public boolean comprobarDiagonalINE(int fila, int columna, Ficha ficha) {
        int desplazamiento = menor(fila, columna);
        int filaInicial = fila - desplazamiento;
        int columnaInicial = columna - desplazamiento;
        int fichaIgualesConsecutivas = 0;

        for (int i = filaInicial, j = columnaInicial; i < FILAS && j < COLUMNAS; i++, j++) {
            if (casillas[i][j].getFicha() == ficha) {
                fichaIgualesConsecutivas++;
                if (fichaIgualesConsecutivas == 4) {
                    return objetivoAlcanzado(fichaIgualesConsecutivas);
                }
            } else {
                fichaIgualesConsecutivas = 0;
            }
        }
        return objetivoAlcanzado(fichaIgualesConsecutivas);
    }

    public boolean comprobarDiagonalINO(int fila, int columna, Ficha ficha) {
        int desplazamiento = fila - COLUMNAS - 1 - columna;
        int filaInicial = fila - desplazamiento;
        int columnaInicial = columna + desplazamiento;
        int fichaIgualesConsecutivas = 0;

        for (int i = filaInicial, j = columnaInicial; i < FILAS && j > 0; i++, j--) {
            if (casillas[i][j].getFicha() == ficha) {
                fichaIgualesConsecutivas++;
                if (fichaIgualesConsecutivas == 4) {
                    return objetivoAlcanzado(fichaIgualesConsecutivas);
                }
            } else {
                fichaIgualesConsecutivas = 0;
            }
        }
        return objetivoAlcanzado(fichaIgualesConsecutivas);
    }

    private boolean comprobarTirada(int fila, int columna, Ficha ficha) {
        return comprobarDiagonalINO(fila, columna, ficha) || comprobarDiagonalINE(fila, columna, ficha) || comprobarHorizontal(fila, ficha) || comprobarVertical(columna, ficha);
    }

    public boolean introducirFicha(int columna, Ficha ficha) throws IllegalArgumentException, OperationNotSupportedException {


        if (columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException("Columna no válida. Debe estar entre 0 y " + (COLUMNAS - 1));
        }

        int fila = getPrimeraFilaVacia(columna);

        if (fila == -1) {
            throw new OperationNotSupportedException("La columna " + columna + " está llena. Elige otra columna.");
        }
        casillas[fila][columna].setFicha(ficha);

        return objetivoAlcanzado(fila);
    }

}



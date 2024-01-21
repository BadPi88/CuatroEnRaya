package org.iesalandalus.programacion.cuatroenraya.modelo;

import javax.naming.OperationNotSupportedException;

public class Casilla {
    private Ficha ficha;

    public void Casilla() {
        ficha = null;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) throws OperationNotSupportedException {
        if (ficha == null) {
            throw new NullPointerException("No se puede poner una ficha nula.");
        }
        if (estaOcupada()) {
            throw new OperationNotSupportedException("La casilla ya contiene una ficha.");
        }
        this.ficha = ficha;
    }

    public boolean estaOcupada() {

        return ficha != null;
    }

    @Override
    public String toString() {
        return String.format(ficha != null ? String.format("%s", ficha) : " ");

    }
}

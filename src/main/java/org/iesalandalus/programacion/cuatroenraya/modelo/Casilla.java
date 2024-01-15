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
        } else estaOcupada();
        this.ficha = ficha;
    }

    public boolean estaOcupada() throws OperationNotSupportedException {

        if (ficha == Ficha.VERDE) {
            throw new OperationNotSupportedException("La casilla ya contiene una ficha.");
        } else if (ficha == Ficha.AZUL) {
            throw new OperationNotSupportedException("La casilla ya contiene una ficha.");
        } else
            return true;
    }

    @Override
    public String toString() {
        return String.format(ficha != null ? String.format("%s", ficha) : " ");

    }
}

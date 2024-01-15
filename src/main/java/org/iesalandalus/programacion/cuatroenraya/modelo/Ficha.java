package org.iesalandalus.programacion.cuatroenraya.modelo;

public enum Ficha { VERDE,AZUL;

    @Override
    public String toString() {
        return String.valueOf(name().charAt(0));
    }
}

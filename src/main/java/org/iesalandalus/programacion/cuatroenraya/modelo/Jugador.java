package org.iesalandalus.programacion.cuatroenraya.modelo;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;

public record Jugador(String nombre, Ficha colorFichas) {

    public Jugador {
        validarNombre(nombre);
        validarColorFichas(colorFichas);
    }

    private String validarNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser nulo.");
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar en blanco.");
        }
        return nombre;
    }

    private Ficha validarColorFichas(Ficha ficha) {
        if (ficha == null) {
            throw new NullPointerException("El color de las fichas no puede ser nulo.");
        }
        return ficha;
    }

    @Override
    public String toString() {

        return String.format("%s (%s)", nombre, colorFichas);

    }
}

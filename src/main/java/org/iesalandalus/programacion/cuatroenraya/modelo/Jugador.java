package org.iesalandalus.programacion.cuatroenraya.modelo;

public record Jugador(String nombre, Ficha colorFichas) {
     public Jugador{

     }

     private String validarNombre(String nombre){
          return nombre;
     }
     private Ficha validarColorFichas(Ficha ficha){
          return ficha;
     }



}

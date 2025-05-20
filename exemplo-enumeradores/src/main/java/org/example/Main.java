package org.example;

public class Main {
    public static void main(String[] args) {

        Carta c1 = new Carta(ValorCarta.A,NaipeCarta.OUROS);
        Carta c2 = new Carta(ValorCarta.CINCO,NaipeCarta.ESPADAS);
        Carta c3 = new Carta(ValorCarta.J,NaipeCarta.PAUS);

        // Deixando livre, consigo atribuir qualquer valor em minha instância. Através de ENUM, consigo criar uma lista de constantes
//        Carta c4 = new Carta("99","Murilo");

        String naipeDesejado = "Copas";
        Integer simboloDesejado = 10;

        Carta cartaDesafio = new Carta(ValorCarta.porValor(simboloDesejado)
                , NaipeCarta.valueOf(naipeDesejado.toUpperCase()));
    }
}
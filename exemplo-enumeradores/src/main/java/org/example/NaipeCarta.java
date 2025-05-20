package org.example;

public enum NaipeCarta {
    COPAS,
    ESPADAS,
    OUROS,
    PAUS;

    public static NaipeCarta fromString(String naipe) {
        for (NaipeCarta n : NaipeCarta.values()) {
            if (n.name().equalsIgnoreCase(naipe)) {
                return n;
            }
        }
        throw new IllegalArgumentException("Naipe inválido: " + naipe);
    }
}

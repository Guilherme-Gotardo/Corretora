package org.example;

public enum ValorCarta {
    A(1),
    DOIS(2),
    TRES(3),
    QUATRO(4),
    CINCO(5),
    SEIS(6),
    SETE(7),
    OITO(8),
    NOVE(9),
    DEZ(10),
    J(11),
    Q(12),
    K(13);

    private Integer valor;

    ValorCarta(Integer valor) {
        this.valor = valor;
    }


    public Integer getValor() {
        return valor;
    }

    // Quando declaro um metodo com "Static" estou atribuindo que o método é referente a classe e não ao objeto da classe, possibilitando que eu chame o
    // método sem ter que instânciar com o objeto
    public static ValorCarta porValor(int valor) {
        for (ValorCarta v : values()) {
            if (v.getValor() == valor) {
                return v;
            }
        }
        return null;
    }
}

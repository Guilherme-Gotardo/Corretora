package org.example;

public class Carta {

    private ValorCarta valor;
    private NaipeCarta naipe;

    public Carta(ValorCarta valor, NaipeCarta naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }

    public ValorCarta getValor() {
        return valor;
    }

    public void setValor(ValorCarta valor) {
        this.valor = valor;
    }

    public NaipeCarta getNaipe() {
        return naipe;
    }

    public void setNaipe(NaipeCarta naipe) {
        this.naipe = naipe;
    }

    @Override
    public String toString() {
        return "Carta{" +
                "valor='" + valor + '\'' +
                ", naipe='" + naipe + '\'' +
                '}';
    }
}

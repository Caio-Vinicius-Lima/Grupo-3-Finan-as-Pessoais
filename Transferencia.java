
public class Transferencia {

    private Conta origem;
    private Conta destino;
    private double valor_Transferido;

    public Transferencia(Conta origem, Conta destino,
                         double valor_Transferido) {

        this.origem = origem;
        this.destino = destino;
        this.valor_Transferido = valor_Transferido;
    }

    public Conta getDestino() {
        return destino;
    }

    public Conta getOrigem() {
        return origem;
    }

    public double getValor_Transferido() {
        return valor_Transferido;
    }

    public boolean realizar_Transferencia() {

        if (valor_Transferido <= 0) {
            return false;
        }

        if (origem == destino) {
            return false;
        }

        if (!origem.saldo_Suficiente(valor_Transferido)) {
            return false;
        }


        return true;
    }
}

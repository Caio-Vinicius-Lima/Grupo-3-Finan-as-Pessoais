import java.time.LocalDate;
public class Transferencia extends Transacao {

    private Conta origem;
    private Conta destino;
    private double valor_Transferido;

    public Transferencia(int id, LocalDate data, double valor, String descricao, String categoria,Conta origem,Conta destino) {

        super(id,data,valor,descricao,categoria);
        this.destino = destino;
        this.origem = origem;
        this.valor_Transferido = valor;
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

    @Override
    public String getTipo() {
        return "Transferencia";
    }

    @Override
    public double aplicar(Conta conta) {
     if (conta == origem) {
        return -getValor();
     }
     if (conta == destino) {
      return getValor();
     }
    return 0;
}


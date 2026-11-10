import java.time.LocalDate;

public class Despesa extends Transacao {

    public Despesa(int id, LocalDate data, double valor, String descricao, String categoria) {
        super(id, data, valor, descricao, categoria);
    }

    @Override
    public String getTipo() {
        return "Despesa";
    }

    @Override
    public double aplicar(Conta, conta) {
        return -getValor();
    }
}

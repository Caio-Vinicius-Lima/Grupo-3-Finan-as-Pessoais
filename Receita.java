import java.time.LocalDate;

public class Receita extends Transacao {

    public Receita(int id, LocalDate data, double valor, String descricao, String categoria) {
        super(id, data, valor, descricao, categoria);
    }

    @Override
    public String getTipo() {
        return "Receita";
    }

    @Override
    public double aplicar(Conta conta) {
        return getValor();
    }
}
package ProgramaClasses;
import java.time.LocalDate;
import ProgramaExcecoes.*;
public class Receita extends Transacao {
     public Receita(int id, LocalDate data, double valor, String descricao, String categoria) throws ValorInvalidoException {
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

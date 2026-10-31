import java.util.List;
import java.util.ArrayList;

public class Conta {

    private String nome;
    private int saldo_Inicial;
    private List<Transacao> transacoes = new ArrayList<>();

    public Conta(String nome, int saldo_Inicial) {
        this.nome = nome;
        this.saldo_Inicial = saldo_Inicial;
    }

    public List<Transacao> getTransacoes() {
        return this.transacoes;
    }
}
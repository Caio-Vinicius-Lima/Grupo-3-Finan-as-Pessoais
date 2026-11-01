
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

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public double getSaldoAtual() {
        double saldo = saldo_Inicial;
        int i = 0;

        while (i < transacoes.size()) {

            Transacao transacao = transacoes.get(i);

            if (transacao.getTipo().equals("Entrada")) {
                saldo += transacao.getValor();
            }
            else if (transacao.getTipo().equals("Saida")) {
                saldo -= transacao.getValor();
            }

            i++;
        }

        return saldo;
    }

    public boolean saldo_Suficiente(double valor) {
        return getSaldoAtual() >= valor;
    }

    public String getNome() {
        return nome;
    }
}

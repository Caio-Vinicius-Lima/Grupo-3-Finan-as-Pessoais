import java.util.ArrayList;
import java.util.List;
import java.time.YearMonth;

public class Conta {
    private String nome;
    private double saldo_inicial;
    private List<Transacao> transacao = new ArrayList<>();

public Conta(String nome,double saldo_inicial){
    this.nome = nome;
    this.saldo_inicial = saldo_inicial;
}

    public List<Transacao> getTransacao() {
        return new ArrayList<>(transacao);
    }

    public void recuperarTransacao(){
        transacao = ContaCSV.Recuperar(this,this);
    }

    public void adicionarTransacao(Transacao t) {
        transacao.add(t);
    }

    public void removerTransacao(int index){
        transacao.remove(index - 1);
    }

    public void alterarTransacao(int index,Transacao t){
        transacao.set(index - 1,t);
    }

    public void substituirTransacoes(List<Transacao> t){
        transacao.clear();
        transacao.addAll(t);
    }

    public void MostrarTransacoes(){

        if (transacao.size() <= 0) {
            System.out.println("Não há transações para ver");
            return;
        }

        for(int i = 0; i < transacao.size();i++){
            System.out.println("Transação " + (i+1) + ": " + "Id --> " + transacao.get(i).getId() + " | Data --> " + transacao.get(i).getData() + " | valor --> " + transacao.get(i).getValor() + " | descrição --> " + transacao.get(i).getDescricao() + " | categoria --> " + transacao.get(i).getCategoria());
        }
    }

    public double getSaldoAtual() {
        double saldo = saldo_inicial;
        for (int i = 0; i < transacao.size(); i++) {
            saldo += transacao.get(i).aplicar(this);
        }
        return saldo;
    }

    public double getSaldoAtual2(YearMonth ate) {
        double saldo = saldo_inicial;
        for (int i = 0; i < transacao.size(); i++) {
        Transacao f = transacao.get(i);
        if (!YearMonth.from(f.getData()).isAfter(ate)) {
            saldo += f.aplicar(this);
        }
    }
    return saldo;
    }

    public boolean saldo_Suficiente(double valor) {
        return getSaldoAtual() >= valor;
    }
}

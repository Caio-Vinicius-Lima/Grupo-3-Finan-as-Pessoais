import java.util.ArrayList;
import java.util.List;

public class Conta {
    private String nome;
    private double saldo_inicial;
    private List<Transacao> transacao = new ArrayList<>();

public Conta(String nome,double saldo_inicial){
    this.nome = nome;
    this.saldo_inicial = saldo_inicial;
}

    public List<Transacao> getTransacao() {
        return this.transacao;
    }

    public void adicionarTransacao(Transacao t) {
        transacao.add(t);
    }

    public double getSaldoAtual() {
        double saldo = saldo_inicial;
        for (int i = 0; i < transacao.size(); i++) {
            saldo += transacao.get(i).aplicar(this);
        }
        return saldo;
    }

    public boolean saldo_Suficiente(double valor) {
        return getSaldoAtual() >= valor;
    }

    public void RemoverLinhaInteira(int linhaEspecifica){
    
       /* if (transacao.get(LinhaEspecifica).getTipo().equals("Receita")) {
                saldo += t.getValor();
            }
            else if (t.getTipo().equals("Despesa")) {
                saldo -= t.getValor();
            } else {
                saldo += ;
            }*/

        transacao.remove(linhaEspecifica);
    }

    public void MostrarLinha(int linha){
        System.out.println(transacao.get(linha).getId());
        System.out.println(transacao.get(linha).getData());
        System.out.println(transacao.get(linha).getValor());
        System.out.println(transacao.get(linha).getDescricao());
        System.out.println(transacao.get(linha).getCategoria());
        /*for(int a = 0;a < transacao.size();a++){
            System.out.println("Linha " + a + " : " + transacao.get(a).getId() + " " + transacao.get(a).getData() + " " + transacao.get(a).getValor() + " " + transacao.get(a).getDescricao() + " " + transacao.get(a).getCategoria());
        }*/
    }
}

import java.util.ArrayList;
import java.util.List;
import java.time.YearMonth;

public class Conta {
    private String nome;
    private double saldo_inicial;
    private List<Transacao> transacao = new ArrayList<>();

//"Id" int | "data" LocalDate | "saldo" double | "descrisao" String | "Categoria" String --> filtro de topicos da descrição?

public Conta(String nome,double saldo_inicial){
    this.nome = nome;
    this.saldo_inicial = saldo_inicial;
}

    public List<Transacao> getTransacao() {
        return new ArrayList<>(transacao);
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
}

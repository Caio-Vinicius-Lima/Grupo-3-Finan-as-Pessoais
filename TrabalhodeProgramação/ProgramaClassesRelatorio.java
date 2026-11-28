package ProgramaClasses;
import java.time.YearMonth;
import java.util.List;
public class Relatorio {

    private YearMonth mes;
    private String Categoria;
    private double saldoInicial;
    private double saldo;
    private Conta conta;

    public Relatorio(Conta conta,String Categoria,YearMonth mes,double saldoInicial){
        this.mes = mes;
        this.Categoria = Categoria;
        this.saldoInicial = saldoInicial;
        this.conta = conta;
    }

    public void total(YearMonth inicio,YearMonth fim){
        List<Transacao> t = conta.getTransacao();
        if (t.size() <= 0) {
            System.out.println("Não há transações para ver");
            return;
        }
        int reverterInicio = inicio.getMonthValue();
        double saldo = saldoInicial;
        System.out.println("=========== Relatorio Temporal ===========");
        for(int i = 0;i < t.size();i++){
        Transacao p = t.get(i);
        YearMonth m = YearMonth.from(p.getData());

        if (!m.isBefore(inicio) && !m.isAfter(fim)) {
            saldo += p.aplicar(conta);
            System.out.println("Transação " + (i+1) + ": " + "Id --> " + p.getId() + " | Data --> " + p.getData() + " | valor --> " + p.getValor() + " | descrição --> " + p.getDescricao() + " | categoria --> " + p.getCategoria());
        }}
        System.out.println("o saldo nesse periodo foi de " + saldo + "\n");
    }

    public void total(YearMonth mes){
        List<Transacao> O = conta.getTransacao();
        if (O.size() <= 0) {
            System.out.println("Não há transações para ver");
            return;
        }
        double b = conta.getSaldoAtual2(mes.minusMonths(1));
        for(int a = 0; a < O.size(); a++){
        Transacao k = O.get(a);
            if (YearMonth.from(k.getData()).equals(mes)) {
              b += k.aplicar(conta);
             System.out.println("Transação " + (a+1) + ": " + "Id --> " + k.getId() + " | Data --> " + k.getData() + " | valor --> " + k.getValor() + " | descrição --> " + k.getDescricao() + " | categoria --> " + k.getCategoria());
        }
    }
        System.out.println("o saldo nesse periodo foi de " + b + "\n");
    }

    public void total(YearMonth mes,String categoria){
        List<Transacao> F = conta.getTransacao();
        if (F.size() <= 0) {
            System.out.println("Não há transações para ver");
            return;
        }
        double c = conta.getSaldoAtual2(mes.minusMonths(1));   // saldo antes do mês
        for(int l = 0; l < F.size(); l++){
            Transacao d = F.get(l);
            if (YearMonth.from(d.getData()).equals(mes) && d.getCategoria().equals(categoria)) {
                c += d.aplicar(conta);
                System.out.println("Transação " + l + ": " + "Id --> " + d.getId() + " | Data --> " + d.getData() + " | valor --> " + d.getValor() + " | descrição --> " + d.getDescricao() + " | categoria --> " + d.getCategoria());
         }
      }
     System.out.println("o saldo nesse periodo foi de " + c + "\n");
     }   
}

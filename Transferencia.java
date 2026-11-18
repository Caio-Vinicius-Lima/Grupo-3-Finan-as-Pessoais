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
    
     public void realizar_Transferencia() throws ValorInvalidoException, ContaNaoEncontradaException, SaldoInsuficienteException {
        
        if (valor_Transferido <= 0) {
            throw new ValorInvalidoException("O valor da transferência deve ser maior que zero.");
        }
        
        if (origem == null || destino == null || origem == destino) {
            throw new ContaNaoEncontradaException("As contas de origem e destino devem existir e ser diferentes.");
        }
        
        if (!origem.saldo_Suficiente(valor_Transferido)) {
            throw new SaldoInsuficienteException("A conta não tem saldo suficiente para esta operação.");
        }

        origem.adicionarTransacao(this);
        destino.adicionarTransacao(this);
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
}




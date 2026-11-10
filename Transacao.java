
import java.time.LocalDate;

public abstract class Transacao {

    private int id;
    private LocalDate data;
    private double valor;
    private String descricao;
    private String categoria;

    public Transacao(int id, LocalDate data, double valor,
                     String descricao, String categoria) {

        this.id = id;
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public abstract double aplicar(Conta conta);

    public abstract String getTipo();
}

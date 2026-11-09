import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Extrato {

    private Conta conta;

    public Extrato(Conta conta) {
        this.conta = conta;
    }

    public Conta getConta() {
        return conta;
    }

    public List<Transacao> obterTransacoes() {
        return conta.getTransacoes();
    }


    public List<Transacao> filtrarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        List<Transacao> filtradas = new ArrayList<>();
        for (Transacao t : conta.getTransacoes()) {
            if ((t.getData().isEqual(dataInicio) || t.getData().isAfter(dataInicio)) &&
                    (t.getData().isEqual(dataFim) || t.getData().isBefore(dataFim))) {
                filtradas.add(t);
            }
        }
        return filtradas;
    }


    public List<Transacao> filtrarPorCategoria(String categoria) {
        List<Transacao> filtradas = new ArrayList<>();
        for (Transacao t : conta.getTransacoes()) {
            if (t.getCategoria().equalsIgnoreCase(categoria)) {
                filtradas.add(t);
            }
        }
        return filtradas;
    }
    public void exportar(List<Transacao> lista, String arquivo) {
        try (FileWriter writer = new FileWriter(arquivo)) {

            writer.append("ID,Data,Tipo,Valor,Descricao,Categoria\n");

            for (Transacao t : lista) {
                writer.append(String.valueOf(t.getId())).append(",")
                        .append(t.getData().toString()).append(",")
                        .append(t.getTipo()).append(",")
                        .append(String.valueOf(t.getValor())).append(",")
                        .append(t.getDescricao()).append(",")
                        .append(t.getCategoria()).append("\n");
            }

            System.out.println("Extrato exportado com sucesso para: " + arquivo);
        } catch (IOException e) {
            System.err.println("Erro ao exportar extrato para CSV: " + e.getMessage());
        }
    }

    public void exportar(String arquivo) {
        exportar(conta.getTransacoes(), arquivo);
    }
}
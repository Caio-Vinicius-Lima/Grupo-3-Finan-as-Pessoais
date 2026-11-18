public class Categoria {

    private final String[] arrayTopicos = {
        "Alimentação",
        "Transporte",
        "Dívida",
        "Salário",
        "Auxílio",
        "Venda",
        "Outros"
    };

    public String getTopico(int index) {
       return arrayTopicos[index];
    }
}

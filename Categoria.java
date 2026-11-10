public class Categoria {

    private final String[] arrayTopicos = {
        "Alimentacao",
        "Transporte",
        "Divida",
        "Salario",
        "Auxilio",
        "Venda",
        "Outros"
    };

    public String getTopico(int index) {
       return arrayTopicos[index];
    }
}

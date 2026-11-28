package ProgramaClasses;
public class Categoria {

    private static final String[] arrayTopicos = {
        "Alimentação",
        "Transporte",
        "Dívida",
        "Salário",
        "Auxílio",
        "Venda",
        "Outros"
    };

    public static String getTopico(int index) {
       return arrayTopicos[index];
    }
}

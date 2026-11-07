public class categorias {
    
    private double id;

    private final String[] arrayTopicos = {
        "Alimentacao",
        "Transporte",
        "Imposto",
        "Salario",
        "Outros"
    };

    public String getTopico(int index) {
       return arrayTopicos[index];
    }

    public double getId() {
        return id;
    }       
}
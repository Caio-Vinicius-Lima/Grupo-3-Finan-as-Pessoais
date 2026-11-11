import java.util.Scanner;
import java.time.LocalDate;
public class Main {

    static boolean loop = true;
    static String Descricao,categoria,nome;
    static int index;
    static double valor;
    static LocalDate data;
    static Scanner input = new Scanner(System.in);
    static Conta conta1,conta2;
    static int id = (int) (Math.random() * 1000000000);

    public static void interfaceterminal(){
        System.out.println("============================ MENU ===============================");
        System.out.println("Conta atual: " + nome);
        System.out.println("\n--> Insira a primeira palavra da operação desejada:\n");
        System.out.println("1. Transferir "); // transferencia mesmo
        System.out.println("2. Relatorio"); // soma das despesas e receitas em um periodo de tempo
        System.out.println("3. Despesa"); // basicametne perder saldo diretamente da bunda
        System.out.println("4. Receita"); // Basicamente adicionar saldo diretamente da bunda
        System.out.println("5. Saldo");
        System.out.println(6. Encerrar");
        System.out.println("=================================================================");
    }

    public static void ColetaDados(){
        System.out.print("--> Qual é o motivo para ocorrer essa transacao?\n");
        input.nextLine();
        Descricao = input.nextLine();
        System.out.println("--> Em qual topico você deseja inserir? (index)");
        index = input.nextInt();
        Categoria array = new Categoria();
        categoria = array.getTopico(index);
    }

    public static void Operacao(String operacao){
        switch(operacao){
             case "Transferir":
                System.out.println("--> Quanto que voce deseja transferir?");
                double transferencia = input.nextDouble();
                ColetaDados();
                Transferencia p = new Transferencia(id,LocalDate.now(),transferencia,("Transferencia: " + Descricao),categoria,conta1,conta2);
                conta1.adicionarTransacao(p);
                conta2.adicionarTransacao(p);
                System.out.println("Foi movido um total de: " + transferencia + " reais");
                System.out.println("Atualmente a conta1 possui " + conta1.getSaldoAtual() + " reais e a conta2 possui " + conta2.getSaldoAtual() + " reais");
                break;

             case "Relatorio":
               
                // aqui precisa do relatorio
                break;

             case "Despesa":
                System.out.println("--> Quanto que ser depositado?");
                double despesa = input.nextDouble();
                ColetaDados();
                Despesa d = new Despesa(id,LocalDate.now(),despesa,("Despesa: " + Descricao),categoria);
                conta1.adicionarTransacao(d);
                System.out.println("Foi removido um total de " + despesa + " reais do seu saldo" );
                System.out.println("seu saldo atual é de " + conta1.getSaldoAtual() + " reais");
                // aqui precisa da despesa
                break;

             case "Receita":
                System.out.println("--> Quanto que será recebido?");
                double receita = input.nextDouble();
                ColetaDados();
                Receita r = new Receita(id,LocalDate.now(),receita,("Receita: " + Descricao),categoria);
                System.out.println("--> Quanto que ser depositado?");
                conta1.adicionarTransacao(r);
                System.out.println("Voce recebeu um total de " + receita + " reais do seu saldo" );
                System.out.println("seu saldo atual é de " + conta1.getSaldoAtual() + " reais");
                // aqui precisa da receita
                break;

             case "Saldo":
                System.out.println("seu saldo da " + nome + " é de " + conta1.getSaldoAtual() + " reais e a conta2 é de " + conta2.getSaldoAtual());
                break;

             case "Encerrar":
                loop = false;
                break;

             default:
                System.out.println("Essa opção não existe tente novamente");
                break;
      }
    }

    public static void main(String[] args){

        System.out.println("============================= Cadastro =============================");
        System.out.println("     Digite primeiro o seu nome e o seu saldo bancario inicial");
        nome = input.nextLine();
        double saldoinicial = input.nextDouble();
        System.out.println("");
        conta1 = new Conta(nome,saldoinicial);
        conta2 = new Conta("Conta2",0);
        
        while(loop){
        interfaceterminal();
        input.nextLine();
        String digita = input.nextLine();
        Operacao(digita);
    }
}
}

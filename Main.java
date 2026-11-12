import java.util.Scanner;
import java.time.LocalDate;
import java.time.YearMonth;
public class Main {

    static boolean loop = true;
    static String Descricao,categoria,nome;
    static int index,maisAno = 0;
    static double valor,saldoinicial,maisMes = 0;
    static LocalDate data;
    static Scanner input = new Scanner(System.in);
    static Conta conta1,conta2;
    static int id = (int) (Math.random() * 1000000000),dia = (int) (Math.random() * 27 + 1);

    public static void interfaceterminal(){
        System.out.println("============================ MENU ===============================");
        System.out.println("Conta atual: " + nome);
        System.out.println("\n--> Insira a primeira palavra da operação desejada:\n");
        System.out.println("1.  Transferir "); 
        System.out.println("2. Relatorio"); 
        System.out.println("3. Despesa");
        System.out.println("4. Receita"); 
        System.out.println("5. Saldo");
        System.out.println("6. Encerrar");
        System.out.println("=================================================================");
    }

    public static void ColetaDados(){
        System.out.print("--> Qual é o motivo para ocorrer essa transacao?\n");
        input.nextLine();
        Descricao = input.nextLine();
        System.out.println("--> Em qual topico especifica você deseja inserir? (index de 1 a 7)");
        index = input.nextInt();
        Categoria array = new Categoria();
        categoria = array.getTopico(index);
    }

     public static void AvancarTempo(){
      int x = (int) (maisMes);
      data = LocalDate.of((2026 + maisAno),(x + 1),dia);

      if(maisMes == 12){
         maisAno++;
         maisMes = 0;
      }   
   }

    public static void Operacao(String operacao){
        switch(operacao){
             case "Transferir":
                System.out.println("--> Quanto que voce deseja transferir?");
                double transferencia = input.nextDouble();
                ColetaDados();
                Transferencia p = new Transferencia(id,data,transferencia,("Transferencia: " + Descricao),categoria,conta1,conta2);
                conta1.adicionarTransacao(p);
                conta2.adicionarTransacao(p);
                System.out.println("Foi movido um total de: " + transferencia + " reais");
                System.out.println("Atualmente a conta1 possui " + conta1.getSaldoAtual() + " reais e a conta2 possui " + conta2.getSaldoAtual() + " reais");
                maisMes += 0.5;
                break;

             case "Relatorio":
               System.out.println("--> Antes de escolher os tipos de relatorio,preencha esses dados");
               System.out.println("--> Se voce deseja usar o Relatorio Filtrado,o topico especifico sera selecionado nesta parte (index de 1 a 7)");
                index = input.nextInt();
                Categoria array = new Categoria();
                categoria = array.getTopico(index);
               System.out.println("--> Você deseja qual tipo de aleatorio? (digite 1 a 3)");
               System.out.println("(Temporal --> 1) (Filtrada --> = 2) (Mensal --> = 3)");
               int g = input.nextInt();
               YearMonth mes = YearMonth.from(data);
               Relatorio j = new Relatorio(conta1,categoria,mes,saldoinicial);
               switch(g){
                  case 1:
                      System.out.println("--> De quais meses,respectivamente,inicio e fim,voce deseja fazer uma relatorio especifico (1 a 12)");
                     int inicio = input.nextInt();
                     int fim = input.nextInt();
                     YearMonth inicio2 = YearMonth.of(2026 + maisAno,inicio);
                     YearMonth fim2 = YearMonth.of(2026 + maisAno,fim);
                     j.total(inicio2,fim2);
                     break;
                  case 2:
                     System.out.println("--> Selecione um mes para ver o relatorio mensal (1 a 12)");
                     inicio = input.nextInt();
                     mes = YearMonth.of(2026 + maisAno,inicio);
                     j.total(mes,categoria);
                     break;
                  case 3:
                     System.out.println("--> Selecione um mes para ver o relatorio mensal (1 a 12)");
                     inicio = input.nextInt();
                     mes = YearMonth.of(2026 + maisAno,inicio);
                     j.total(mes);
                     break;
                  default:
                     loop = false;
                     break;
               }
                break;

             case "Despesa":
                System.out.println("--> Quanto que ser depositado?");
                double despesa = input.nextDouble();
                ColetaDados();
                Despesa d = new Despesa(id,data,despesa,("Despesa: " + Descricao),categoria);
                conta1.adicionarTransacao(d);
                System.out.println("Foi removido um total de " + despesa + " reais do seu saldo" );
                System.out.println("seu saldo atual é de " + conta1.getSaldoAtual() + " reais");
                maisMes += 0.5;
                break;

             case "Receita":
                System.out.println("--> Quanto que será recebido?");
                double receita = input.nextDouble();
                ColetaDados();
                Receita r = new Receita(id,data,receita,("Receita: " + Descricao),categoria);
                System.out.println("--> Quanto que ser depositado?");
                conta1.adicionarTransacao(r);
                System.out.println("Voce recebeu um total de " + receita + " reais do seu saldo" );
                System.out.println("seu saldo atual é de " + conta1.getSaldoAtual() + " reais");
                maisMes += 0.5;
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
        saldoinicial = input.nextDouble();
        System.out.println("");
        conta1 = new Conta(nome,saldoinicial);
        conta2 = new Conta("Conta2",0);
        
        while(loop){
        AvancarTempo();
        interfaceterminal();
        input.nextLine();
        String digita = input.nextLine();
        Operacao(digita);
    }
}
}

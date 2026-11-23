import java.time.LocalDate;
import java.time.YearMonth;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;

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
        System.out.print("\n");
        System.out.println("============================ MENU ===============================");
        System.out.println("Conta atual: " + nome + "      Data: " + data);
        System.out.println("\n--> Insira o numero que corresponde a operação desejada:\n");
        System.out.println("1. Transferir ");
        System.out.println("2. Relatório");
        System.out.println("3. Despesa"); 
        System.out.println("4. Receita"); 
        System.out.println("5. Ver Saldo e Todas as Transações");
        System.out.println("6. Editar Transações");
        System.out.println("7. Apagar a Conta (Arquivo)");
        System.out.println("8. Encerrar");
        System.out.println("=================================================================");
    }

    public static void ColetaDados(){
      try{
        input.nextLine();
        System.out.println("--> Qual é o motivo para ocorrer essa transacao?");
        Descricao = input.nextLine();
        System.out.println("--> Em qual tópico específico você deseja inserir? (número de 0 a 6)");
        System.out.println("(Alimentação --> 0) | (Transporte --> 1) | (Divída --> 2) | (Salário --> 3) ");
        System.out.println("          (Auxílio --> 4) | (Venda --> 5) | (Outros --> 6)");
        index = input.nextInt();
      } catch (InputMismatchException | IllegalArgumentException | ArrayIndexOutOfBoundsException e){
         System.err.println("[ERRO]: a variável index aceita apenas inteiro de 0 a 6 para o array");
         System.err.println("A transação foi cancelada");
      }
      categoria = Categoria.getTopico(index);
    }

     public static void AvancarTempo(){
      int x = (int) (maisMes);
      data = LocalDate.of((2026 + maisAno),(x + 1),dia);

      if(maisMes == 12){
         maisAno++;
         maisMes = 0;
      }   
   }

    public static void Operacao(int operacao){
        switch(operacao){
             case 1: 
               id = (int) (Math.random() * 1000000000);
               System.out.println("--> Insira um valor que você deseja transferir:");
               double transferencia = input.nextDouble();
               ColetaDados();      
               Transferencia p = new Transferencia(id,data,transferencia,Descricao,categoria,conta1,conta2);
               try {
               p.realizar_Transferencia();
               ContaCSV.Adicionar(p);
               System.out.println("Transferência Bem Sucedida! Foi movido um total de " + transferencia + " reais\n");
               System.out.println("Atualmente a conta1 possui " + conta1.getSaldoAtual() + " reais e a ContaSecundária possui " + conta2.getSaldoAtual() + " reais");
               maisMes += 0.5;
                } catch(ValorInvalidoException | ContaNaoEncontradaException | SaldoInsuficienteException e){
                  System.err.println("[ERRO] " + e.getMessage());
                  System.err.println("Nenhum dado desta transação foi armazenado");
                }
                break;

             case 2:
               System.out.println("--> Antes de escolher os tipos de relatório,preencha esses dados.");
               System.out.println("--> Se você deseja usar o Relatório Filtrado, o tópico específico será selecionado nesta parte \n");
               System.out.println("(Alimentação --> 0) | (Transporte --> 1) | (Divída --> 2) | (Salário --> 3) ");
               System.out.println("         (Auxílio --> 4) | (Venda --> 5) | (Outros --> 6)");
               while(true){
                  try{
                     index = input.nextInt();
                     categoria = Categoria.getTopico(index);
                     break;
                  } catch(ArrayIndexOutOfBoundsException e){
                     System.err.println("[ERRO] a varíavel index aceita apenas inteiro de 0 a 6 para o array");
                     System.err.println("Por ora,a transação foi cancelada,mas desde que voce digite no intervalo correto,ela vai continuar");
               }}
               System.out.println("--> Você deseja qual tipo de relatorio? (digite 1 a 3)");
               System.out.println("(Temporal --> 1) | (Filtrada --> 2) | (Mensal -- 3)");
               int g = input.nextInt();
               YearMonth mes = YearMonth.from(data);
               Relatorio j = new Relatorio(conta1,categoria,mes,saldoinicial);
               switch(g){
                  case 1:
                     while(true){
                     try{
                        System.out.println("--> De quais meses,respectivamente,inicio e fim,voce deseja fazer uma relatório específico (1 a 12)");
                        int inicio = input.nextInt();
                        int fim = input.nextInt();
                        YearMonth inicio2 = YearMonth.of(2026 + maisAno,inicio);
                        YearMonth fim2 = YearMonth.of(2026 + maisAno,fim);
                        j.total(inicio2,fim2);
                        break;
                     } catch(InputMismatchException e){
                        System.err.println("[ERRO] Relatório não sera fornecido agora,desde que você insira uma data com valores inteiros");
                        input.nextLine();
                     }}
                     break;
                  case 2:
                     System.out.println("--> Selecione um mês para ver o relatorio mensal (1 a 12)");
                     while(true){
                     try {
                        int inicio = input.nextInt();
                        mes = YearMonth.of(2026 + maisAno,inicio);
                        j.total(mes,categoria);
                        break;
                        } catch (InputMismatchException e){
                           System.err.println("[ERRO] Relatório não sera fornecido agora,desde que você insira uma data com valores inteiros");
                           input.nextLine();
                     }}
                     break;
                  case 3:
                     try{
                        System.out.println("--> Selecione um mês para ver o relatório mensal (1 a 12)");
                        int inicio = input.nextInt();
                        if(inicio > 12 || inicio <= 0){
                           throw new IllegalArgumentException();
                        }
                        mes = YearMonth.of(2026 + maisAno,inicio);
                        j.total(mes);
                        break;
                     } catch(IllegalArgumentException e){
                        System.err.println("[ERRO] Relatório não será fornecido agora,desde que você insira uma data com valores inteiros");
                        input.nextLine();
                     }
                     break;
                  default:
                     System.out.println("Essa opção não existe tente novamente, escolhendo os valores certos para executar a operação");
                     break;
                  }
                  break;

            case 3:
               id = (int) (Math.random() * 1000000000);
               System.out.println("--> Quanto que ser depositado?");
               double despesa = input.nextDouble();
               try{
                 if(despesa <= 0){
                  throw new ValorInvalidoException("O valor da transferência deve ser maior que zero.");
                } else if (despesa > conta1.getSaldoAtual()) {
                  throw new SaldoInsuficienteException("Você não tem a quantidade necessária para depositar esse dinheiro.");
                }
                ColetaDados();
                Despesa d = new Despesa(id,data,despesa,Descricao,categoria);
                conta1.adicionarTransacao(d);
                ContaCSV.Adicionar(d);
                System.out.println("Foi removido um total de " + despesa + " reais do seu saldo" );
                System.out.println("seu saldo atual é de " + conta1.getSaldoAtual() + " reais");
                maisMes += 0.5;
                } catch(ValorInvalidoException | SaldoInsuficienteException e){
                  System.err.println("[ERRO] Despesa não registada: " + e.getMessage());
                }
                break;

             case 4:
               id = (int) (Math.random() * 1000000000);
               System.out.println("--> Quanto que será recebido?");
               double receita = input.nextDouble();
                try{
                  if(receita <= 0){
                  throw new ValorInvalidoException("O valor da receita tem de ser maior que zero.");
                  }
                  ColetaDados();
                  Receita r = new Receita(id,data,receita,Descricao,categoria);
                  ContaCSV.Adicionar(r);
                  conta1.adicionarTransacao(r);
                  System.out.println("Voce recebeu um total de " + receita + " reais do seu saldo" );
                  System.out.println("seu saldo atual é de " + conta1.getSaldoAtual() + " reais");
                  maisMes += 0.5;
                }
                catch(ValorInvalidoException e){
                  System.err.println("[ERRO] Receita não registada: " + e.getMessage());
                }
                break;

             case 5:
               conta1.MostrarTransacoes();
               System.out.println("Seu saldo da " + nome + " é de " + conta1.getSaldoAtual() + " reais e a conta2 é de " + conta2.getSaldoAtual());
               break;

             case 6:
               try{
                  ContaCSV.Existencia();
                  System.out.println("--> Qual opção deseja fazer?");
                  System.out.println("(1 --> Remover Transação) | (2 --> Adicionar Transação");
                  int z = input.nextInt();
                  switch(z){
                     case 1:
                        ContaCSV.RemoverTransacaoArquivo(conta1);
                     break;

                     case 2:
                        ContaCSV.AlterarTransacaoArquivo(conta1,conta2);
                     break;

                     default:
                        System.out.println("Não existe essa ação,por ora essa operação vai ser cancelada");
                     break;
                  }} catch(ArquivoDadosException | IOException e){
                     System.err.println("[ERRO] " + e.getMessage() + ",tente novamente");
                  }
                  break;

             case 7:
               try{
                  System.out.println("--> Tem certeza mesmo? você vai perder o cadastro e todas as transações armazenadas até agora.");
                  System.out.println(" (Digite - true - para apagar) | (Digite - false - para cancelar a operação)");
                  input.nextLine();
                  String parse = input.nextLine();
                  if(parse.equals("true")){
                  ContaCSV.ApagarArquivo();
                  loop = false;
                  System.out.println("Reiniciando o aplicativo...");
                  } else {
                     break;
                  }
               } catch (ArquivoDadosException | IOException e) {
               System.err.println("[ERRO] " + e.getMessage());
               }
               break;
             case 8:
               loop = false;
               break;
             default:
                System.out.println("Essa opção não existe tente novamente");
                break;
      }
    }

    public static void main(String[] args){
        try{
            ContaCSV.Existencia();
            ContaCSV.LerCadastro();

            if (ContaCSV.getNomeCadastro() != null) {
            nome = ContaCSV.getNomeCadastro();
            saldoinicial = ContaCSV.getSaldoCadastro();
         }} catch(ArquivoDadosException e){
            System.err.println(e.getMessage());
            System.err.println("Criando Arquivo novamente...");
            ContaCSV.CriarArquivo();
        }  

        if(nome == null && saldoinicial == 0){
         System.out.println("============================= Cadastro =============================");
         while(true){
            System.out.println("--> Digite primeiro o seu nome");
            nome = input.nextLine();

            if (nome.matches(".*\\d.*")) {
               System.out.println("[ERRO] Não insira números neste campo. Digite apenas texto.");
            } else{
               break;
            }}

            while(true){
            try{
               System.out.println("--> e agora o seu saldo bancário inicial");
               saldoinicial = input.nextDouble();
               if(saldoinicial <= 0 ){
                  throw new ValorInvalidoException(" a variável saldo inicial não aceita números negativos/nulos nesta operação");
               }
               break;
            } catch(ValorInvalidoException e){
               System.err.println("[ERRO] " + e.getMessage() + ",tente novamente");
            } catch(InputMismatchException e){
               System.err.println("[ERRO] você digitou uma string para um valor numérico. Por favor,digite apenas valores inteiros");
               input.nextLine();
            }}
            ContaCSV.Cadastrar(nome,saldoinicial);
         }

         conta1 = new Conta(nome,saldoinicial);
         conta2 = new Conta("ContaSecundária",0);
         conta1.recuperarTransacao();

        while(loop){
         AvancarTempo();
         interfaceterminal();
         try{
            int digita = input.nextInt();
            Operacao(digita);
         } catch(InputMismatchException e){
         System.err.println("[ERRO] apenas os numeros de 1 a 7 são validos nesta operação");
         input.nextLine();
        }
   }}
   }

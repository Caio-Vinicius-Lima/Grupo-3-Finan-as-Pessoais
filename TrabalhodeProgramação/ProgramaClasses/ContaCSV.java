package ProgramaClasses;

import ProgramaExcecoes.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContaCSV{

    private static final String ArquivoArrayList = System.getProperty("user.dir") + File.separator + "ProgramaClasses";
    private static final File Arquivo = new File(ArquivoArrayList, "ArquivoArrayList.csv");
    private static String linha,nome;
    private static double saldo;
    private static int maisAno,maisMes;
    private static LocalDate data;
    private static boolean primeiraLinha = true;

    public static void Adicionar(Transacao t) throws ArquivoDadosException {
        try{
            if (!Arquivo.isFile()) {
                throw new ArquivoDadosException("[ERRO] o arquivo não existe"); 
            }

            FileWriter escrever = new FileWriter(Arquivo,StandardCharsets.UTF_8,true);
            escrever.write(t.getId() + ";" + t.getData() + ";" + t.getValor() + ";" + t.getDescricao() + ";" + t.getCategoria() + "\n");
            escrever.flush();
            escrever.close();
        } catch(IOException e){
            throw new ArquivoDadosException("[ERRO] " + e.getMessage());  
        }
    }

    public static List<Transacao> Recuperar(Conta c1,Conta c2) throws ValorInvalidoException,ArquivoDadosException {
        List<Transacao> transacao = new ArrayList<>();

        try{
            BufferedReader ler = new BufferedReader(new FileReader(Arquivo));
            primeiraLinha = true;
           
            while((linha = ler.readLine()) != null){
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
            
            String[] partes = linha.split(";");
            int id = Integer.parseInt(partes[0]);
            LocalDate data = LocalDate.parse(partes[1]);

            double valor = Double.parseDouble(partes[2]);
            String descricao = partes[3];
            String categoria = partes[4];
            Transacao t;
            String k = descricao.substring(0, descricao.indexOf(":"));

            switch(k){
                case "Despesa":
                    t = new Despesa(id,data,valor,descricao,categoria);
                    break;
                case "Transferencia":
                    t = new Transferencia(id,data,valor,descricao,categoria,c1,c2);
                    break;
                case "Receita":
                    t = new Receita(id,data,valor,descricao,categoria);
                    break;
                default:
                    System.out.println("Tipo não identificado na linha: " + linha);
                    continue;
            }
            transacao.add(t);
        }
            ler.close();
        } catch (IOException e) {
            throw new ArquivoDadosException("[ERRO] " + e.getMessage()); 
        }
        return transacao;
    }

    public static void LerCadastro() throws ArquivoDadosException {
        nome = null;
        saldo = 0;
        try{
            if (!Arquivo.isFile()) {
                throw new ArquivoDadosException("[ERRO] o arquivo não existe"); 
            }
            BufferedReader ler = new BufferedReader(new FileReader(Arquivo));
            String primeira = ler.readLine();
                if (primeira == null){
                    return;  
                }
            String Lerlinha[] = primeira.split(";");
            nome = Lerlinha[5];
            saldo = Double.valueOf(Lerlinha[6]);
            data = LocalDate.parse(Lerlinha[7]);
            maisMes = Integer.parseInt(Lerlinha[8]);
            maisAno = Integer.parseInt(Lerlinha[9]);

        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            saldo = 0;
            nome = null;
        }
    }

    public static String getNomeCadastro(){
        return nome;
    }

    public static double getSaldoCadastro(){
        return saldo;
    }

    public static int getMaisMes(){
        return maisMes;
    }

    public static int getMaisAno(){
        return maisAno;
    }

    public static LocalDate getData(){
        return data;
    }

    public static void CriarArquivo(){
        try{
            Files.createDirectories(Arquivo.toPath().getParent());

            if (Files.notExists(Arquivo.toPath())) {
                Files.createFile(Arquivo.toPath());
             }
        } catch(IOException e){
            System.err.println("[ERRO] " + e.getMessage());
    }}

    public static void AtualizarData(String nome, double saldo, LocalDate data, int mes, int ano) throws ArquivoDadosException {
        try{
            if (!Arquivo.isFile()) {  
                throw new ArquivoDadosException("[ERRO] o arquivo não existe"); 
            }

            List<String> linhas = new ArrayList(Files.readAllLines(Arquivo.toPath(), StandardCharsets.UTF_8));
            String novaLinha = "Id;data;valor;descricao;categoria;" + nome + ";" + saldo + ";" + data + ";" + mes + ";" + ano;

            linhas.set(0, novaLinha);
            Files.write(Arquivo.toPath(),linhas,StandardCharsets.UTF_8);   
        } catch(IOException e){
            throw new ArquivoDadosException("[ERRO] " + e.getMessage());  
        }
    }

    public static void Cadastrar(String nome,double saldo,LocalDate data,int mes,int ano) throws ValorInvalidoException,ArquivoDadosException {
            if (!Arquivo.isFile()) {
                throw new ArquivoDadosException("[ERRO] o arquivo não existe"); 
            }

            if(saldo <= 0){
                throw new ValorInvalidoException(" [ERRO] o saldo inicial não aceita números negativos nem nulos nesta operação");
            }
        try{
            if(data == null){
                data = LocalDate.now();
            }

            BufferedReader ler = new BufferedReader(new FileReader(Arquivo));
            List<String> linhas = new ArrayList<>();
            while ((linha = ler.readLine()) != null) {
               linhas.add(linha);
            }
            ler.close();

            String cadastro = "Id;data;valor;descricao;categoria;" + nome + ";" + saldo + ";" + data + ";" + mes + ";" + ano;
            if (linhas.isEmpty()) {
                linhas.add(cadastro);
            } else {
                linhas.set(0, cadastro);
            }

            FileWriter escrever = new FileWriter(Arquivo, StandardCharsets.UTF_8,false);
            for (int i = 0; i < linhas.size(); i++) {
                escrever.write(linhas.get(i) + "\n");
                }
            escrever.close();
            } catch(IOException e){
                throw new ArquivoDadosException("[ERRO] " + e.getMessage()); 
            }}


    public static void ApagarArquivo() throws ArquivoDadosException{
        
        try{
            boolean deletar = Files.deleteIfExists(Arquivo.toPath());
            if(deletar == false){
                throw new ArquivoDadosException("O arquivo não está nesta pasta ou nem existe");
            } else{
                System.out.println("Arquivo apagado com sucesso");
        }} catch(IOException e){
            throw new ArquivoDadosException("[ERRO] " + e.getMessage()); 
        }
    }

    public static void RemoverTransacaoArquivo(Conta conta) throws ArquivoDadosException {
    try{

        if (!Arquivo.isFile()) {
            throw new ArquivoDadosException("[ERRO] o arquivo não existe ou está em outra pasta"); 
        }

        Scanner input = new Scanner(System.in);
        int a = 0;
        List<String> linhas = new ArrayList(Files.readAllLines(Arquivo.toPath(), StandardCharsets.UTF_8)); 
        if (linhas.size() <= 1) {
            System.out.println("Não há transações para remover");
            return;
        }

        conta.MostrarTransacoes();
        System.out.println("--> Digite o número da Transação que você quer remover (Digite de 1 a " + (linhas.size() - 1) + ")"); 
        int indice = input.nextInt();

        if( indice < 1 || indice > (linhas.size() - 1)){
            System.out.println("[ERRO] Transação inexistente ");
            return;
        }

        linhas.remove(indice);
        conta.removerTransacao(indice);
        System.out.println("Transação " + indice + " removida com sucesso!");
        Files.write(Arquivo.toPath(), linhas, StandardCharsets.UTF_8);  
    } catch(InputMismatchException | IOException e ){
        System.out.println("[ERRO] ");
    }}

    public static void AlterarTransacaoArquivo(Conta conta1,Conta conta2) throws ArquivoDadosException,ValorInvalidoException,SaldoInsuficienteException{  
        try{
            Scanner input = new Scanner(System.in);
            int k = 0;
            List<String> linhas = new ArrayList(Files.readAllLines(Arquivo.toPath(), StandardCharsets.UTF_8));  

        if (linhas.size() - 1 < 1) {
            System.out.println("Não há transações para alterar");
            return;
        }

        conta1.MostrarTransacoes();

        System.out.println("--> Digite o número da Transação que você quer alterar: (Digite de 1 a " + (linhas.size() - 1) + ")"); 
        int indice = input.nextInt();

        if (indice < 1 || indice > (linhas.size() - 1)) { 
            System.out.println("[ERRO] Transação inexistente ");
            return;
        }

        int id = (int) (Math.random() * 1000000000);

        System.out.println("--> Agora digite o tipo de transição que voce deseja por: ");
        System.out.println(" (1 --> Transferência | 2 --> Receita | 3 --> Despesa)");
        while(true){
            k = input.nextInt();
            if (k < 1 || k > 3){
                System.out.println("Essa opção não existe,tente novametne e digite o tipo pelo intervalo estabelecido");
            } else{
                break;
            }
        }

        System.out.println("--> Digite o valor da transação");
        double valor = input.nextDouble();

        if ((!conta1.saldo_Suficiente(valor)) && (k % 2 == 1)) {
            throw new SaldoInsuficienteException("A conta não tem saldo suficiente para esta operação");
        }

        System.out.println("--> Digite o motivo para ocorrer essa transação");
        input.nextLine();
        String descricao = input.nextLine();

        System.out.println("--> Digite a data: (Digite nos intervalos de Mês: 1 a 12 | Ano: 2020 a 2026)");
        int dia = (int) (Math.random() * 27 + 1);
        int mes = input.nextInt();
        int ano = input.nextInt();

        if(mes < 1 || mes > 12 || ano < 2020 || ano > 2026){
            throw new IllegalArgumentException("Uma das informações fornecida está fora do intervalo esclarecido acima");
        }

        LocalDate data = LocalDate.of(ano,mes,dia); 

        System.out.println("--> Em qual tópico específico você deseja inserir? (número de 0 a 6)");
        System.out.println("(Alimentação --> 0) | (Transporte --> 1) | (Divída --> 2) | (Salário --> 3) ");
        System.out.println("          (Auxílio --> 4) | (Venda --> 5) | (Outros --> 6)");
        int index = input.nextInt();
        String categoria = Categoria.getTopico(index);

        Transacao t;
        switch(k){
            case 1:
                t = new Transferencia(id,data,valor,descricao,categoria,conta1,conta2);
                break;
            case 2:
                t = new Receita(id,data,valor,descricao,categoria);
                break;
            case 3:
                t = new Despesa(id,data,valor,descricao,categoria);
                break;
            default:
                throw new InputMismatchException("Você digitou uma operação que não existe,a operação foi cancelada e nenhuma transação foi alterada");
        }
        
        linhas.set(indice,formatarLinhaCSV(t));
        Files.write(Arquivo.toPath(), linhas, StandardCharsets.UTF_8); 
        conta1.alterarTransacao(indice,t);
        sincronizarContas(conta1, conta2);

    } catch(ArrayIndexOutOfBoundsException | InputMismatchException | IOException e){
        System.err.println(e.getMessage());
        System.err.println("a operação foi cancelada e nenhuma transação foi alterada");
    }}

    private static String formatarLinhaCSV(Transacao t) {
        return t.getId() + ";" + t.getData() + ";" + t.getValor() + ";" + t.getDescricao() + ";" + t.getCategoria();
    }

    public static void sincronizarContas(Conta conta1, Conta conta2) throws ValorInvalidoException,ArquivoDadosException { 

        List<Transacao> atualizada = Recuperar(conta1, conta2);
        conta1.substituirTransacoes(atualizada);
        if (conta2 != null) {
            conta2.substituirTransacoes(new ArrayList<>());
            for (int i = 0; i < atualizada.size(); i++) {
                Transacao t = atualizada.get(i);
                if (t instanceof Transferencia) {
                    conta2.adicionarTransacao(t);
                }
            }
        }
    }
}


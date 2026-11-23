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

    private static final String ArquivoArrayList = System.getProperty("user.dir");
    private static final File Arquivo = new File(ArquivoArrayList, "ArquivoArraylist.csv");
    private static String linha,nome;
    private static double saldo;
    private static boolean primeiraLinha = true;

    public static void Adicionar(Transacao t){
       
        try{
            FileWriter escrever = new FileWriter(Arquivo,StandardCharsets.UTF_8,true);
            escrever.write(t.getId() + ";" + t.getData() + ";" + t.getValor() + ";" + t.getDescricao() + ";" + t.getCategoria() + "\n");
            escrever.flush();
            escrever.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static List<Transacao> Recuperar(Conta c1,Conta c2){
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
            e.printStackTrace();
        }
        return transacao;
    }

    public static void LerCadastro(){
        nome = null;
        saldo = 0;
        
        try{
            BufferedReader ler = new BufferedReader(new FileReader(Arquivo));
            String primeira = ler.readLine();
            if (primeira == null){
                return;  
            }
            String Lerlinha[] = primeira.split(";");
            nome = Lerlinha[5];
            saldo = Double.valueOf(Lerlinha[6]);
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

    public static void Existencia() throws ArquivoDadosException{
        if (!Arquivo.isFile()) {
        throw new ArquivoDadosException("[ERRO] o arquivo não existe"); 
    }}

    public static void CriarArquivo(){
        try{
        Path caminho = Arquivo.toPath();
        Files.createDirectories(caminho.getParent());
        if (Files.notExists(caminho)) {
            Files.createFile(caminho);
        }} catch(IOException e){
            System.err.println("[ERRO] " + e.getMessage());
        }}

    public static void Cadastrar(String nome,double saldo) {
            try{
            BufferedReader ler = new BufferedReader(new FileReader(Arquivo));
            List<String> linhas = new ArrayList<>();
            while ((linha = ler.readLine()) != null) {
               linhas.add(linha);
            }
            ler.close();

            String cadastro = "Id;data;valor;descricao;categoria;"+ nome + ";" + saldo;
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
                System.err.println("[ERRO] " + e.getMessage());
            }}


    public static void ApagarArquivo() throws ArquivoDadosException,IOException {
        
        boolean deletar = Files.deleteIfExists(Arquivo.toPath());
        if(deletar == false){
            throw new ArquivoDadosException("O arquivo não está nesta pasta ou nem existe");
        } else{
            System.out.println("Arquivo apagado com sucesso");
        }
    }

    public static void RemoverTransacaoArquivo(Conta conta) {
    try{
        Scanner input = new Scanner(System.in);
        int a = 0;
        List<String> linhas = new ArrayList(Files.readAllLines(Path.of("ArquivoArrayList.csv"), StandardCharsets.UTF_8));

        if (linhas.size() <= 0) {
            System.out.println("Não há transações para remover");
            return;
        }

        for(int b = 1; b <= (linhas.size() - 1);b++){
            a++;
            String[] partes = linhas.get(b).split(";");
            int id = Integer.parseInt(partes[0]);
            LocalDate data = LocalDate.parse(partes[1]);
            double valor = Double.parseDouble(partes[2]);
            String descricao = partes[3];
            String categoria = partes[4];
            System.out.println("Transação " + b + ": " + "Id --> " + id + " | Data --> " + data + " | valor --> " + valor + " | descrição --> " + descricao + " | categoria --> " + categoria);
        }
            System.out.println("--> Digite o número da Transação que você quer remover (Digite de 1 a " + a); 
           
            int indice = input.nextInt();
            if( indice < 0 || indice > a){
                throw new ArrayIndexOutOfBoundsException();
            }
            linhas.remove(indice);
            System.out.println("Transação " + indice + " removida com sucesso!");
            Files.write(Path.of("ArquivoArrayList.csv"), linhas, StandardCharsets.UTF_8);
    } catch(NumberFormatException | ArrayIndexOutOfBoundsException | IOException e ){
        System.out.println("Por favor,digite uma operação válida entre o intervalo dito acima");
    }}

    public static void AlterarTransacaoArquivo(Conta conta1,Conta conta2) {
        try{
        Scanner input = new Scanner(System.in);
        List<String> linhas = new ArrayList(Files.readAllLines(Path.of("ArquivoArrayList.csv"), StandardCharsets.UTF_8));

        if (linhas.size() - 1 < 1) {
            System.out.println("Não há transações para remover");
            return;
        }

        for(int b = 1; b <= (linhas.size() - 1);b++){
            String[] partes = linhas.get(b).split(";");
            int id = Integer.parseInt(partes[0]);
            LocalDate data = LocalDate.parse(partes[1]);
            double valor = Double.parseDouble(partes[2]);
            String descricao = partes[3];
            String categoria = partes[4];
            System.out.println("Transação " + b + ": " + "Id --> " + id + " | Data --> " + data + " | valor --> " + valor + " | descrição --> " + descricao + " | categoria --> " + categoria);
        }

        System.out.println("--> Digite o número da Transação que você quer alterar: (Digite de 1 a " + linhas.size()); 
        int indice = input.nextInt();

        if (indice < 1 || indice > linhas.size()) {
            System.out.println("[ERRO] Transação inexistente.");
            return;
        }

        int id = (int) (Math.random() * 1000000000);

        System.out.println("--> Digite o valor da transação");
        double valor = input.nextDouble();

        if(valor <= 0){
            throw new ValorInvalidoException("O valor da transação não pode ser 0 ou menor que isso");
        }

        if (!conta1.saldo_Suficiente(valor)) {
            throw new SaldoInsuficienteException("A conta não tem saldo suficiente para esta operação.");
        }

        System.out.println("--> Digite o motivo para ocorrer essa transação");
        input.nextLine();
        String descricao = input.nextLine();

        System.out.println("--> Digite a data: (Digite nos intervalos de Dia 1 a 28 | Mês 1 a 12 | Ano 2020 a 2026)");
        int dia = input.nextInt();
        int mes = input.nextInt();
        int ano = input.nextInt();
        if(dia < 1 || dia > 28 || mes < 1 || mes > 12 || ano < 2020 || ano > 2026){
            throw new InputMismatchException("Uma das informações foi. fornecida fora do itnervalo estabeelcido");
        }

        LocalDate data = LocalDate.of(dia,mes,ano); 

        System.out.println("--> Em qual tópico específico você deseja inserir? (número de 0 a 6)");
        System.out.println("(Alimentação --> 0) | (Transporte --> 1) | (Divída --> 2) | (Salário --> 3) ");
        System.out.println("          (Auxílio --> 4) | (Venda --> 5) | (Outros --> 6)");
        int index = input.nextInt();
        String categoria = Categoria.getTopico(index);

        if(conta1.getSaldoAtual() < 0){
            throw new SaldoInsuficienteException("O valor imposto deixa o saldo negativo");
        }

        System.out.println("--> Agora digite o tipo de transição que voce deseja por");
        System.out.println(" (1 --> Transferência | 2 --> Receita | 3 --> Despesa)");
        int k = input.nextInt();
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
                System.out.println("Por favor,insira um valor que está dentro do intervalo estabelecido acima");
                break;
        }

        // eu vou parar por aqui tenho 2 prova pra estuda 
        String conteudo = Files.readString(Path.of("ArquivoArrayList.csv"),StandardCharsets.UTF_8);
        String LinhaAntiga = linhas.get(indice);
        //Stirng LinhaNova = linhas
        //String atualizado = conteudo.replace(LinhaAntiga,LinhaNova);

    } catch(ArrayIndexOutOfBoundsException | ValorInvalidoException | InputMismatchException | SaldoInsuficienteException | IOException e){
        System.err.println(e.getMessage());
    }}
}

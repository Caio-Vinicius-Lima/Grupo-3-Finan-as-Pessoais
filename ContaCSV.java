import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

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
            Transacao indetificartipo;
            String k = descricao.substring(0, descricao.indexOf(":"));

            switch(k){
                case "Despesa":
                    indetificartipo = new Despesa(id,data,valor,descricao,categoria);
                    break;
                case "Transferencia":
                    indetificartipo = new Transferencia(id,data,valor,descricao,categoria,c1,c2);
                    break;
                case "Receita":
                    indetificartipo = new Receita(id,data,valor,descricao,categoria);
                    break;
                default:
                    System.out.println("Tipo não identificado na linha: " + linha);
                    continue;  
            }
            transacao.add(indetificartipo);
        //System.out.println("Id: " + id + " | data: " + data + " | valor: " + valor + " | descrição: " + descricao + "| categoria: " + categoria);
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
    if (!Arquivo.exists()){ 
        return;
    }    
        try{
            BufferedReader ler = new BufferedReader(new FileReader(Arquivo));
            String primeira = ler.readLine();
            if (primeira == null) return;  
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

    public static void Existencia() throws ArquivoDadosException,IOException{
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

        /*public static void RemoverTransacao(){
        List<String> linhas = Files.readAllLines(Path.of("ArquivoArrayList,csv"), StandardCharsets.UTF_8);
        // descobri que existe um File mais moderno e muito mais fácil de mexer do que eesse
        for(int indice = 0; indice < linhas.size(); linhas++){
            
        }*/

    public static void ApagarArquivo() throws ArquivoDadosException,IOException {
        
        boolean deletar = Files.deleteIfExists(Arquivo.toPath());
        if(deletar == false){
            throw new ArquivoDadosException("O arquivo não está nesta pasta ou nem existe");
        } else{
            System.out.println("Arquivo apagado com sucesso");
        }
    }
}

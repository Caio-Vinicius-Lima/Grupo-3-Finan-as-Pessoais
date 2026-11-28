package ProgramaExcecoes;	

public class ContaNaoEncontradaException extends IllegalArgumentException {
    public ContaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}

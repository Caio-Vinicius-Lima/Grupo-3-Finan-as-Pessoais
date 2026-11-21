import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;

public class TelaInicial extends JFrame {

    private static final Color COR_FUNDO = new Color(7, 7, 7);
    private static final Color COR_CARD = new Color(19, 8, 9);
    private static final Color COR_BORDA_SUTIL = new Color(66, 22, 26);
    private static final Color COR_BOTAO = new Color(112, 20, 27);
    private static final Color COR_BOTAO_HOVER = new Color(138, 26, 35);
    private static final Color COR_FAIXA_ESQUERDA = new Color(218, 74, 84);
    private static final Color COR_BARRA_TITULO = new Color(165, 24, 34);
    private static final Color COR_TEXTO_SECUNDARIO = new Color(168, 118, 122);
    private static final Color COR_TEXTO_VERMELHO = new Color(224, 90, 100);
    private static final Color COR_PLACEHOLDER = new Color(105, 105, 105);

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel containerTelas = new JPanel(cardLayout);

    private Conta conta1 = new Conta("Usuário", 0.0);
    private Conta conta2 = new Conta("Conta Secundária", 0.0);
    private final NumberFormat moedaBR = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));


    private JLabel lblSaldoMovimentacoes;
    private JLabel lblMesRelatorio;
    private JLabel lblGanhosRelatorio;
    private JLabel lblGastosRelatorio;
    private JLabel lblTransferidoRelatorio;
    private JLabel lblSaldoRelatorio;
    private JPanel painelCategoriasRelatorio;

    public TelaInicial() {
        setTitle("Sistema de Finanças Pessoais");
        setSize(1024, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout());


        JPanel linhaTopo = new JPanel();
        linhaTopo.setBackground(COR_BARRA_TITULO);
        linhaTopo.setPreferredSize(new Dimension(getWidth(), 2));
        add(linhaTopo, BorderLayout.NORTH);


        containerTelas.setBackground(COR_FUNDO);
        containerTelas.add(criarTelaMenu(), "MENU");
        containerTelas.add(criarTelaCadastro(), "CADASTRO");
        containerTelas.add(criarTelaMovimentacoes(), "MOVIMENTACOES");
        containerTelas.add(criarTelaRelatorio(), "RELATORIO");

        add(containerTelas, BorderLayout.CENTER);
        cardLayout.show(containerTelas, "MENU");
    }


    private JPanel criarTelaMenu() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_FUNDO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0);
        painel.add(criarLogoFinancas(), gbc);

        BotaoMenu btnCadastro = new BotaoMenu("C A D A S T R O", true, 480, 52);
        BotaoMenu btnMovimentacoes = new BotaoMenu("M O V I M E N T A Ç Ã O", true, 480, 52);
        BotaoMenu btnRelatorio = new BotaoMenu("R E L A T Ó R I O", true, 480, 52);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 18, 0);
        painel.add(btnCadastro, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 18, 0);
        painel.add(btnMovimentacoes, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        painel.add(btnRelatorio, gbc);


        btnCadastro.addActionListener(e -> cardLayout.show(containerTelas, "CADASTRO"));

        btnMovimentacoes.addActionListener(e -> {
            atualizarDadosTelas();
            cardLayout.show(containerTelas, "MOVIMENTACOES");
        });

        btnRelatorio.addActionListener(e -> {
            atualizarDadosTelas();
            cardLayout.show(containerTelas, "RELATORIO");
        });

        return painel;
    }


    private JPanel criarTelaCadastro() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_FUNDO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;


        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 35, 0);
        painel.add(criarLogoFinancas(), gbc);


        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(COR_FUNDO);
        conteudo.setPreferredSize(new Dimension(460, 330));

        conteudo.add(criarCabecalhoSecao("NOVO USUÁRIO", "Cadastro"));
        conteudo.add(Box.createVerticalStrut(32));


        JLabel lblNome = criarLabelCampo("NOME COMPLETO");
        JTextField txtNome = criarCampoTextoComPlaceholder("Digite seu nome");
        conteudo.add(lblNome);
        conteudo.add(Box.createVerticalStrut(8));
        conteudo.add(txtNome);
        conteudo.add(Box.createVerticalStrut(26));


        JLabel lblSaldo = criarLabelCampo("SALDO INICIAL");
        JPanel linhaSaldo = new JPanel(new BorderLayout(8, 0));
        linhaSaldo.setBackground(COR_FUNDO);
        linhaSaldo.setMaximumSize(new Dimension(460, 36));
        linhaSaldo.setAlignmentX(Component.LEFT_ALIGNMENT);
        linhaSaldo.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, COR_BORDA_SUTIL),
                new EmptyBorder(4, 0, 6, 0)
        ));

        JLabel lblPrefixoRS = new JLabel("R$");
        lblPrefixoRS.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblPrefixoRS.setForeground(COR_TEXTO_SECUNDARIO);

        JTextField txtSaldo = criarCampoTextoComPlaceholder("0,00");
        txtSaldo.setBorder(null); // A borda inferior já está no painel linhaSaldo

        linhaSaldo.add(lblPrefixoRS, BorderLayout.WEST);
        linhaSaldo.add(txtSaldo, BorderLayout.CENTER);

        conteudo.add(lblSaldo);
        conteudo.add(Box.createVerticalStrut(8));
        conteudo.add(linhaSaldo);
        conteudo.add(Box.createVerticalStrut(36));

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 14, 0));
        painelBotoes.setBackground(COR_FUNDO);
        painelBotoes.setMaximumSize(new Dimension(460, 48));
        painelBotoes.setAlignmentX(Component.LEFT_ALIGNMENT);

        BotaoContorno btnVoltar = new BotaoContorno("VOLTAR");
        BotaoMenu btnCadastrar = new BotaoMenu("CADASTRAR", false, 220, 48);

        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnCadastrar);
        conteudo.add(painelBotoes);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        painel.add(conteudo, gbc);

        btnVoltar.addActionListener(e -> cardLayout.show(containerTelas, "MENU"));

        btnCadastrar.addActionListener(e -> {
            String nomeDigitado = txtNome.getText().trim();
            String saldoDigitado = txtSaldo.getText().trim().replace(",", ".");

            if (nomeDigitado.isEmpty() || nomeDigitado.equals("Digite seu nome")) {
                JOptionPane.showMessageDialog(this, "Por favor, digite o seu nome completo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double saldoInicial = (saldoDigitado.isEmpty() || saldoDigitado.equals("0.00"))
                        ? 0.0 : Double.parseDouble(saldoDigitado);

                if (saldoInicial < 0) {
                    throw new ValorInvalidoException("O saldo inicial não pode ser negativo.");
                }

                conta1 = new Conta(nomeDigitado, saldoInicial);
                conta2 = new Conta("Conta Secundária", 0.0);
                atualizarDadosTelas();

                JOptionPane.showMessageDialog(this, "Conta cadastrada com sucesso para " + nomeDigitado + "!");
                cardLayout.show(containerTelas, "MOVIMENTACOES");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um valor numérico válido para o saldo.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ValorInvalidoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return painel;
    }

    private JPanel criarTelaMovimentacoes() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_FUNDO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        painel.add(criarLogoFinancas(), gbc);

        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(COR_FUNDO);
        conteudo.setPreferredSize(new Dimension(460, 420));

        conteudo.add(criarCabecalhoSecao("ÁREA FINANCEIRA", "Movimentações"));
        conteudo.add(Box.createVerticalStrut(24));


        JPanel cardSaldo = new JPanel();
        cardSaldo.setLayout(new BoxLayout(cardSaldo, BoxLayout.Y_AXIS));
        cardSaldo.setBackground(COR_CARD);
        cardSaldo.setMaximumSize(new Dimension(460, 82));
        cardSaldo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardSaldo.setBorder(new CompoundBorder(
                new LineBorder(COR_BORDA_SUTIL, 1),
                new EmptyBorder(14, 18, 14, 18)
        ));

        JLabel lblTituloSaldo = new JLabel("SALDO ATUAL");
        lblTituloSaldo.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblTituloSaldo.setForeground(COR_TEXTO_SECUNDARIO);

        lblSaldoMovimentacoes = new JLabel("R$ 0,00");
        lblSaldoMovimentacoes.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblSaldoMovimentacoes.setForeground(Color.WHITE);

        cardSaldo.add(lblTituloSaldo);
        cardSaldo.add(Box.createVerticalStrut(6));
        cardSaldo.add(lblSaldoMovimentacoes);

        conteudo.add(cardSaldo);
        conteudo.add(Box.createVerticalStrut(22));


        BotaoMenu btnTransferir = new BotaoMenu("TRANSFERIR PARA OUTRA CONTA", true, 460, 48);
        BotaoMenu btnReceita = new BotaoMenu("INSERIR RECEITA", true, 460, 48);
        BotaoMenu btnDespesa = new BotaoMenu("INSERIR DESPESA", true, 460, 48);
        BotaoContorno btnVoltarMenu = new BotaoContorno("VOLTAR AO MENU");
        btnVoltarMenu.setMaximumSize(new Dimension(460, 44));
        btnVoltarMenu.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnTransferir.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnReceita.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnDespesa.setAlignmentX(Component.LEFT_ALIGNMENT);

        conteudo.add(btnTransferir);
        conteudo.add(Box.createVerticalStrut(12));
        conteudo.add(btnReceita);
        conteudo.add(Box.createVerticalStrut(12));
        conteudo.add(btnDespesa);
        conteudo.add(Box.createVerticalStrut(16));
        conteudo.add(btnVoltarMenu);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        painel.add(conteudo, gbc);

        btnTransferir.addActionListener(e -> abrirDialogoTransacao("TRANSFERENCIA"));
        btnReceita.addActionListener(e -> abrirDialogoTransacao("RECEITA"));
        btnDespesa.addActionListener(e -> abrirDialogoTransacao("DESPESA"));
        btnVoltarMenu.addActionListener(e -> cardLayout.show(containerTelas, "MENU"));

        return painel;
    }


    private JPanel criarTelaRelatorio() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_FUNDO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 22, 0);
        painel.add(criarLogoFinancas(), gbc);

        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(COR_FUNDO);
        conteudo.setPreferredSize(new Dimension(460, 470));

        conteudo.add(criarCabecalhoSecao("RESUMO MENSAL", "Relatório"));
        conteudo.add(Box.createVerticalStrut(6));

        lblMesRelatorio = new JLabel(formatarMesAtual());
        lblMesRelatorio.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblMesRelatorio.setForeground(COR_TEXTO_SECUNDARIO);
        lblMesRelatorio.setAlignmentX(Component.LEFT_ALIGNMENT);
        conteudo.add(lblMesRelatorio);
        conteudo.add(Box.createVerticalStrut(18));


        JPanel gradeCards = new JPanel(new GridLayout(2, 2, 12, 12));
        gradeCards.setBackground(COR_FUNDO);
        gradeCards.setMaximumSize(new Dimension(460, 152));
        gradeCards.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblGanhosRelatorio = new JLabel("R$ 0,00");
        lblGastosRelatorio = new JLabel("R$ 0,00");
        lblTransferidoRelatorio = new JLabel("R$ 0,00");
        lblSaldoRelatorio = new JLabel("R$ 0,00");

        gradeCards.add(criarCardResumo("GANHOS", lblGanhosRelatorio, COR_CARD, COR_BORDA_SUTIL, Color.WHITE));
        gradeCards.add(criarCardResumo("GASTOS", lblGastosRelatorio, COR_CARD, COR_BORDA_SUTIL, COR_TEXTO_VERMELHO));
        gradeCards.add(criarCardResumo("TRANSFERIDO", lblTransferidoRelatorio, COR_CARD, COR_BORDA_SUTIL, Color.WHITE));
        gradeCards.add(criarCardResumo("SALDO", lblSaldoRelatorio, COR_BOTAO, COR_BOTAO, Color.WHITE));

        conteudo.add(gradeCards);
        conteudo.add(Box.createVerticalStrut(22));

        JLabel lblCatTitulo = new JLabel("GASTOS POR CATEGORIA");
        lblCatTitulo.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblCatTitulo.setForeground(Color.WHITE);
        lblCatTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        conteudo.add(lblCatTitulo);
        conteudo.add(Box.createVerticalStrut(10));

        painelCategoriasRelatorio = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(COR_BORDA_SUTIL);
                float[] dash = {4f, 4f};
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f));
                g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2.dispose();
            }
        };
        painelCategoriasRelatorio.setBackground(COR_CARD);
        painelCategoriasRelatorio.setMaximumSize(new Dimension(460, 62));
        painelCategoriasRelatorio.setPreferredSize(new Dimension(460, 62));
        painelCategoriasRelatorio.setAlignmentX(Component.LEFT_ALIGNMENT);

        conteudo.add(painelCategoriasRelatorio);
        conteudo.add(Box.createVerticalStrut(24));


        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 12, 0));
        painelBotoes.setBackground(COR_FUNDO);
        painelBotoes.setMaximumSize(new Dimension(460, 46));
        painelBotoes.setAlignmentX(Component.LEFT_ALIGNMENT);

        BotaoContorno btnMenu = new BotaoContorno("MENU");
        BotaoMenu btnNovaMov = new BotaoMenu("NOVA MOVIMENTAÇÃO", false, 260, 46);

        painelBotoes.add(btnMenu);
        painelBotoes.add(btnNovaMov);
        conteudo.add(painelBotoes);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        painel.add(conteudo, gbc);

        btnMenu.addActionListener(e -> cardLayout.show(containerTelas, "MENU"));
        btnNovaMov.addActionListener(e -> {
            atualizarDadosTelas();
            cardLayout.show(containerTelas, "MOVIMENTACOES");
        });

        return painel;
    }

    private void abrirDialogoTransacao(String tipo) {
        String titulo = switch (tipo) {
            case "RECEITA" -> "Inserir Receita";
            case "DESPESA" -> "Inserir Despesa";
            default -> "Transferir para Outra Conta";
        };

        JTextField txtValor = new JTextField();
        JTextField txtDescricao = new JTextField();
        String[] categorias = {"Alimentacao", "Transporte", "Divida", "Salario", "Auxilio", "Venda", "Outros"};
        JComboBox<String> comboCategoria = new JComboBox<>(categorias);

        JPanel form = new JPanel(new GridLayout(0, 1, 6, 6));
        form.add(new JLabel("Valor (R$):"));
        form.add(txtValor);
        form.add(new JLabel("Descrição:"));
        form.add(txtDescricao);
        form.add(new JLabel("Categoria:"));
        form.add(comboCategoria);

        int opcao = JOptionPane.showConfirmDialog(this, form, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcao != JOptionPane.OK_OPTION) return;

        try {
            double valor = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
            String descricao = txtDescricao.getText().trim().isEmpty() ? titulo : txtDescricao.getText().trim();
            String categoria = (String) comboCategoria.getSelectedItem();
            int id = (int) (Math.random() * 1000000);
            LocalDate hoje = LocalDate.now();

            if (valor <= 0) {
                throw new ValorInvalidoException("O valor da operação deve ser maior que zero.");
            }

            if (tipo.equals("RECEITA")) {
                Receita r = new Receita(id, hoje, valor, descricao, categoria);
                conta1.adicionarTransacao(r);
                JOptionPane.showMessageDialog(this, "Receita registrada com sucesso!");

            } else if (tipo.equals("DESPESA")) {
                if (!conta1.saldo_Suficiente(valor)) {
                    throw new SaldoInsuficienteException("Saldo insuficiente para registrar esta despesa.");
                }
                Despesa d = new Despesa(id, hoje, valor, descricao, categoria);
                conta1.adicionarTransacao(d);
                JOptionPane.showMessageDialog(this, "Despesa registrada com sucesso!");

            } else if (tipo.equals("TRANSFERENCIA")) {
                Transferencia t = new Transferencia(id, hoje, valor, descricao, categoria, conta1, conta2);
                t.realizar_Transferencia();
                JOptionPane.showMessageDialog(this, "Transferência realizada com sucesso!");
            }

            atualizarDadosTelas();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um valor numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ValorInvalidoException | SaldoInsuficienteException | ContaNaoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Operação Recusada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarDadosTelas() {
        double saldoAtual = conta1.getSaldoAtual();
        lblSaldoMovimentacoes.setText(moedaBR.format(saldoAtual));
        lblSaldoRelatorio.setText(moedaBR.format(saldoAtual));

        YearMonth mesAtual = YearMonth.now();
        lblMesRelatorio.setText(formatarMesAtual());

        double ganhos = 0.0;
        double gastos = 0.0;
        double transferido = 0.0;
        Map<String, Double> gastosPorCategoria = new LinkedHashMap<>();

        for (Transacao t : conta1.getTransacao()) {
            if (YearMonth.from(t.getData()).equals(mesAtual)) {
                if (t instanceof Receita) {
                    ganhos += t.getValor();
                } else if (t instanceof Despesa) {
                    gastos += t.getValor();
                    gastosPorCategoria.merge(t.getCategoria(), t.getValor(), Double::sum);
                } else if (t instanceof Transferencia) {
                    transferido += t.getValor();
                }
            }
        }

        lblGanhosRelatorio.setText(moedaBR.format(ganhos));
        lblGastosRelatorio.setText(moedaBR.format(gastos));
        lblTransferidoRelatorio.setText(moedaBR.format(transferido));

        painelCategoriasRelatorio.removeAll();
        if (gastosPorCategoria.isEmpty()) {
            JLabel lblVazio = new JLabel("Nenhuma despesa registrada neste mês.", SwingConstants.CENTER);
            lblVazio.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblVazio.setForeground(COR_PLACEHOLDER);
            painelCategoriasRelatorio.add(lblVazio, BorderLayout.CENTER);
        } else {
            StringBuilder resumo = new StringBuilder("<html><center>");
            gastosPorCategoria.forEach((cat, val) ->
                    resumo.append("<b>").append(cat).append(":</b> ").append(moedaBR.format(val)).append(" &nbsp;&nbsp; ")
            );
            resumo.append("</center></html>");
            JLabel lblResumoCat = new JLabel(resumo.toString(), SwingConstants.CENTER);
            lblResumoCat.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblResumoCat.setForeground(Color.WHITE);
            painelCategoriasRelatorio.add(lblResumoCat, BorderLayout.CENTER);
        }
        painelCategoriasRelatorio.revalidate();
        painelCategoriasRelatorio.repaint();
    }


    private JLabel criarLogoFinancas() {
        JLabel lblLogo = new JLabel("F I N A N Ç A S");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 21));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBorder(new CompoundBorder(
                new MatteBorder(0, 3, 0, 0, COR_BARRA_TITULO),
                new EmptyBorder(2, 12, 2, 0)
        ));
        return lblLogo;
    }

    private JPanel criarCabecalhoSecao(String subtituloSuperior, String tituloPrincipal) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(COR_FUNDO);
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setBorder(new CompoundBorder(
                new MatteBorder(0, 3, 0, 0, COR_BARRA_TITULO),
                new EmptyBorder(0, 14, 2, 0)
        ));

        JLabel lblSub = new JLabel(subtituloSuperior);
        lblSub.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblSub.setForeground(COR_TEXTO_VERMELHO);

        JLabel lblTit = new JLabel(tituloPrincipal);
        lblTit.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTit.setForeground(Color.WHITE);

        box.add(lblSub);
        box.add(Box.createVerticalStrut(4));
        box.add(lblTit);
        return box;
    }

    private JLabel criarLabelCampo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(COR_TEXTO_SECUNDARIO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField criarCampoTextoComPlaceholder(String placeholder) {
        JTextField campo = new JTextField(placeholder);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setBackground(COR_FUNDO);
        campo.setForeground(COR_PLACEHOLDER);
        campo.setCaretColor(Color.WHITE);
        campo.setMaximumSize(new Dimension(460, 34));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, COR_BORDA_SUTIL),
                new EmptyBorder(4, 0, 6, 0)
        ));

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(COR_PLACEHOLDER);
                }
            }
        });
        return campo;
    }

    private JPanel criarCardResumo(String titulo, JLabel lblValor, Color bg, Color borda, Color corValor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bg);
        card.setBorder(new CompoundBorder(
                new LineBorder(borda, 1),
                new EmptyBorder(12, 16, 12, 16)
        ));

        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblTit.setForeground(COR_TEXTO_SECUNDARIO);

        lblValor.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblValor.setForeground(corValor);

        card.add(lblTit);
        card.add(Box.createVerticalStrut(6));
        card.add(lblValor);
        return card;
    }

    private String formatarMesAtual() {
        YearMonth ym = YearMonth.now();
        String mes = ym.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
        mes = mes.substring(0, 1).toUpperCase() + mes.substring(1);
        return mes + " De " + ym.getYear();
    }


    private static class BotaoMenu extends JButton {
        private final boolean comFaixaEsquerda;
        private boolean hover = false;

        public BotaoMenu(String texto, boolean comFaixaEsquerda, int largura, int altura) {
            super(texto);
            this.comFaixaEsquerda = comFaixaEsquerda;
            setPreferredSize(new Dimension(largura, altura));
            setMaximumSize(new Dimension(largura, altura));
            setFont(new Font("SansSerif", Font.BOLD, 11));
            setForeground(Color.WHITE);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override
                public void mouseExited(MouseEvent e) { hover = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(hover ? COR_BOTAO_HOVER : COR_BOTAO);
            g2.fillRect(0, 0, getWidth(), getHeight());

            if (comFaixaEsquerda) {
                g2.setColor(COR_FAIXA_ESQUERDA);
                g2.fillRect(0, 0, 5, getHeight());
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }


    private static class BotaoContorno extends JButton {
        private boolean hover = false;

        public BotaoContorno(String texto) {
            super(texto);
            setFont(new Font("SansSerif", Font.BOLD, 11));
            setForeground(Color.WHITE);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override
                public void mouseExited(MouseEvent e) { hover = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(hover ? new Color(26, 10, 12) : COR_FUNDO);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(COR_BORDA_SUTIL);
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaInicial extends JFrame {

    private static final Color COR_FUNDO = new Color(5, 5, 5);
    private static final Color COR_BOTAO = new Color(112, 20, 27);
    private static final Color COR_BOTAO_HOVER = new Color(138, 26, 35);
    private static final Color COR_BORDA_BOTAO = new Color(218, 74, 84);
    private static final Color COR_BARRA_TITULO = new Color(165, 24, 34);

    public TelaInicial() {
        setTitle("Sistema de Finanças Pessoais");
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(COR_FUNDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblTitulo = new JLabel("F I N A N Ç A S");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(new CompoundBorder(
                new MatteBorder(0, 4, 0, 0, COR_BARRA_TITULO),
                new EmptyBorder(2, 14, 2, 0)
        ));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 45, 0);
        painelPrincipal.add(lblTitulo, gbc);
        BotaoMenu btnCadastro = new BotaoMenu("C A D A S T R O");
        BotaoMenu btnMovimentacoes = new BotaoMenu("T R A N S F E R Ê N C I A S ,   R E C E I T A S   E   D E S P E S A S");
        BotaoMenu btnRelatorio = new BotaoMenu("R E L A T Ó R I O");
        btnCadastro.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnMovimentacoes.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnRelatorio.setFont(new Font("SansSerif", Font.BOLD, 13));

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 18, 0);
        painelPrincipal.add(btnCadastro, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 18, 0);
        painelPrincipal.add(btnMovimentacoes, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        painelPrincipal.add(btnRelatorio, gbc);

        btnCadastro.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrindo tela de Cadastro de Conta...");
        });

        btnMovimentacoes.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrindo tela de Transferências, Receitas e Despesas...");
        });

        btnRelatorio.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrindo tela de Relatório...");
        });

        add(painelPrincipal);
    }

    private static class BotaoMenu extends JButton {
        private boolean mouseEmCima = false;

        public BotaoMenu(String texto) {
            super(texto);
            setPreferredSize(new Dimension(470, 52));
            setForeground(Color.WHITE);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    mouseEmCima = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mouseEmCima = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(mouseEmCima ? COR_BOTAO_HOVER : COR_BOTAO);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(COR_BORDA_BOTAO);
            g2.fillRect(0, 0, 5, getHeight());

            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaInicial().setVisible(true);
        });
    }
}
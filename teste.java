Object[][] novaMatriz =
        new Object[transacoes.length + 1][5];


        for (int i = 0; i < transacoes.length; i++) {
novaMatriz[i] = transacoes[i];w
        }


int linha = transacoes.length;

novaMatriz[linha][0] = id;
novaMatriz[linha][1] = data;
novaMatriz[linha][2] = valor;
novaMatriz[linha][3] = descricao;
novaMatriz[linha][4] = categoria;


transacoes = novaMatriz;
    }
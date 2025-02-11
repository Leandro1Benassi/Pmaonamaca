package Sistema;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
public class Principal {
    private static List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
        new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
        new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
        new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
        new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
        new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
        new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
        new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
        new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
        new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
        new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
    ));

    public static void main(String[] args) {
        // Criando o JFrame (Janela)
        JFrame frame = new JFrame("Sistema Comercial controle funcionários");
        frame.setSize(700, 700);  // Tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());  // Define o layout da janela

        // Centralizar a janela no meio da tela
        frame.setLocationRelativeTo(null);

        // Criando um JPanel para o menu e configurando o layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.CYAN);  // Cor de fundo do painel

        // Criando o menu com texto colorido
        JTextArea menuArea = new JTextArea(
                "1 - Listar Funcionários\n" +
                "2 - Aumentar Salário em 10%\n" +
                "3 - Agrupar por Função\n" +
                "4 - Listar Funcionários por Função\n" +
                "5 - Remover João\n" +
                "6 - Funcionários Aniversariantes (Outubro e Dezembro)\n" +
                "7 - Funcionário com a Maior Idade\n" +
                "8 - Listar Funcionários por Ordem Alfabética\n" +
                "9 - Total dos Salários\n" +
                "10 - Salários Mínimos que Cada Funcionário Ganha\n" +
                "11 - Sair\n"
        );
        menuArea.setBackground(Color.BLACK);  // Cor do fundo do menu
        menuArea.setForeground(Color.CYAN); // Cor do texto
        menuArea.setFont(new Font("Arial", Font.PLAIN, 20));  // Tamanho da fonte
        menuArea.setEditable(false);  // Impede que o texto seja editado
        panel.add(new JScrollPane(menuArea), BorderLayout.CENTER);  // Exibe o menu dentro do painel

        // Criando o campo de entrada (JTextField) para capturar a opção escolhida
        JTextField inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));  // Fonte para o campo de entrada

        // Criando o botão para enviar a opção escolhida
        JButton submitButton = new JButton("ENTRAR");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16)); // Fonte maior e em negrito
        submitButton.setBackground(Color.BLACK);  // Fundo chamativo (preto)
        submitButton.setForeground(Color.WHITE); // Texto branco para contraste
        submitButton.setFocusPainted(false); // Remove a borda azul ao clicar
        submitButton.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2)); // Borda chamativa

        // Criando um painel para o input e o botão
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.CYAN);  // Cor de fundo do painel de entrada
        inputPanel.add(new JLabel("Digite a opção:"));  // Rótulo para indicar onde digitar
        inputPanel.add(inputField);
        inputPanel.add(submitButton);

        // Adicionando os componentes ao frame
        frame.add(panel, BorderLayout.CENTER);  // Exibe o menu
        frame.add(inputPanel, BorderLayout.SOUTH);  // Adiciona o campo de entrada e o botão na parte inferior

        // Tornando a janela visível
        frame.setVisible(true);

        // Ação do botão (captura a entrada e faz a escolha)
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String escolha = inputField.getText().trim();  // Captura a entrada do usuário
                inputField.setText("");  // Limpa o campo de entrada após a escolha

                if (escolha.equals("11")) {
                    System.exit(0);  // Fecha o programa se escolher a opção 11
                }

                // Chamada das funções conforme a escolha
                switch (escolha) {
                    case "1":
                        listarFuncionarios();
                        break;
                    case "2":
                        aumentarSalarios();
                        break;
                    case "3":
                        agruparPorFuncao();
                        break;
                    case "4":
                        listarPorFuncao();
                        break;
                    case "5":
                        removerJoao();
                        break;
                    case "6":
                        listarAniversariantes();
                        break;
                    case "7":
                        imprimirFuncionarioMaiorIdade();
                        break;
                    case "8":
                        listarFuncionariosPorOrdemAlfabetica();
                        break;
                    case "9":
                        imprimirTotalSalarios();
                        break;
                    case "10":
                        imprimirSalariosMinimos();
                        break;
                    default:
                        JOptionPane.showMessageDialog(frame, "Opção inválida!"); // Mensagem de erro
                }
            }
        });
    }
    private static void listarFuncionarios() {
        String[] colunas = {"Nome", "Nascimento", "Salário", "Função"};
        
        // Criando modelo de tabela
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Funcionario f : funcionarios) {
            Object[] linha = {
                f.getNome(),
                f.getDataNascimento().format(formatter),
                String.format("R$ %,.2f", f.getSalario()),
                f.getFuncao()
            };
            modelo.addRow(linha);
        }
        
        JTable tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        JOptionPane.showMessageDialog(null, scrollPane, "Lista de Funcionários", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void aumentarSalarios() {
        // Definindo as colunas da tabela
        String[] colunas = {"Nome", "Salário Anterior", "Aumento", "Novo Salário"};
        
        // Criando o modelo de tabela
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        
        // Percorrendo todos os funcionários e calculando o aumento
        for (Funcionario f : funcionarios) {
            // Salário anterior
            BigDecimal salarioAnterior = f.getSalario();
            
            // Calculando o aumento de 10%
            BigDecimal aumento = salarioAnterior.multiply(new BigDecimal("0.10"));
            BigDecimal salarioNovo = salarioAnterior.add(aumento);
            
            // Atualizando o salário do funcionário
            f.aumentarSalario(new BigDecimal("0.10"));

            // Adicionando os dados à tabela
            Object[] linha = {
                f.getNome(),
                String.format("R$ %.2f", salarioAnterior),
                String.format("R$ %.2f", aumento),
                String.format("R$ %.2f", salarioNovo)
            };
            modelo.addRow(linha);
        }
        
        // Criando a JTable com o modelo e exibindo em um JScrollPane
        JTable tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        JOptionPane.showMessageDialog(null, "Aumento de Salários com sucesso!");
        // Exibe a tabela na interface gráfica
        JOptionPane.showMessageDialog(null, scrollPane, "Aumento de Salários", JOptionPane.INFORMATION_MESSAGE);
    }

    
    private static void agruparPorFuncao() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
        JOptionPane.showMessageDialog(null, "Funcionários agrupados por função!");
    }
    
    private static void listarPorFuncao() {
        // Agrupar os funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
        
        // Definir as colunas da tabela
        String[] colunas = {"Função", "Nome", "Nascimento", "Salário"};
        
        // Criar o modelo de tabela
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Preencher a tabela com os dados agrupados
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            lista.forEach(f -> {
                Object[] linha = {
                    funcao,  // Exibe a função do funcionário
                    f.getNome(),
                    f.getDataNascimento().format(formatter),
                    String.format("R$ %,.2f", f.getSalario())
                };
                modelo.addRow(linha);  // Adiciona a linha à tabela
            });
        });
        
        // Criar a JTable com o modelo e exibir em um JScrollPane
        JTable tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        // Exibe a tabela na interface gráfica
        JOptionPane.showMessageDialog(null, scrollPane, "Funcionários Agrupados por Função", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void listarAniversariantes() {
        // Filtra os funcionários que fazem aniversário em outubro (10) ou dezembro (12)
        List<Funcionario> aniversariantes = funcionarios.stream()
                .filter(f -> {
                    // Extraímos o mês de nascimento diretamente da data
                    int mesNascimento = f.getDataNascimento().getMonthValue();
                    return mesNascimento == 10 || mesNascimento == 12;  // Verificando se o mês é 10 (outubro) ou 12 (dezembro)
                })
                .collect(Collectors.toList());
        
        // Verifica se há aniversariantes
        if (aniversariantes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum funcionário faz aniversário em outubro ou dezembro.");
            return;
        }
        
        // Definir as colunas da tabela
        String[] colunas = {"Nome", "Nascimento", "Função", "Salário"};
        
        // Criar o modelo de tabela
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Preencher a tabela com os aniversariantes
        aniversariantes.forEach(f -> {
            Object[] linha = {
                f.getNome(),
                f.getDataNascimento().format(formatter),
                f.getFuncao(),
                String.format("R$ %,.2f", f.getSalario())
            };
            modelo.addRow(linha);
        });
        
        // Criar a JTable com o modelo e exibir em um JScrollPane
        JTable tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        // Exibe a tabela na interface gráfica
        JOptionPane.showMessageDialog(null, scrollPane, "Funcionários Aniversariantes (Outubro e Dezembro)", JOptionPane.INFORMATION_MESSAGE);
    }


    // Imprimir o funcionário com a maior idade (nome e idade)
    private static void imprimirFuncionarioMaiorIdade() {
        // Encontrar o funcionário com a maior idade
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparingInt(f -> f.getDataNascimento().getYear()))
                .orElse(null);
        
        if (maisVelho != null) {
            int idade = LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear();
            String[] colunas = {"Nome", "Idade"};
            DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
            Object[] linha = {
                maisVelho.getNome(),
                idade
            };
            modelo.addRow(linha);
            
            JTable tabela = new JTable(modelo);
            JScrollPane scrollPane = new JScrollPane(tabela);
            JOptionPane.showMessageDialog(null, scrollPane, "Funcionário com a Maior Idade", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Imprimir a lista de funcionários por ordem alfabética
    private static void listarFuncionariosPorOrdemAlfabetica() {
        // Ordenar os funcionários por nome
        List<Funcionario> funcionariosOrdenados = funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
        
        // Definir as colunas da tabela
        String[] colunas = {"Nome", "Nascimento", "Função", "Salário"};
        
        // Criar o modelo de tabela
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Preencher a tabela com os funcionários ordenados
        funcionariosOrdenados.forEach(f -> {
            Object[] linha = {
                f.getNome(),
                f.getDataNascimento().format(formatter),
                f.getFuncao(),
                String.format("R$ %,.2f", f.getSalario())
            };
            modelo.addRow(linha);
        });
        
        // Criar a JTable com o modelo e exibir em um JScrollPane
        JTable tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        // Exibe a tabela na interface gráfica
        JOptionPane.showMessageDialog(null, scrollPane, "Funcionários Ordenados por Nome", JOptionPane.INFORMATION_MESSAGE);
    }

    // Imprimir o total dos salários dos funcionários
    private static void imprimirTotalSalarios() {
        // Calcular o total dos salários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        String[] colunas = {"Total de Salários"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        Object[] linha = {
            String.format("R$ %,.2f", totalSalarios)
        };
        modelo.addRow(linha);
        
        JTable tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        JOptionPane.showMessageDialog(null, scrollPane, "Total dos Salários dos Funcionários", JOptionPane.INFORMATION_MESSAGE);
    }

    // Imprimir quantos salários mínimos ganha cada funcionário
    private static void imprimirSalariosMinimos() {
    	 // Salário mínimo
        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        // Definir as colunas da tabela
        String[] colunas = {"Nome", "Nascimento", "Função", "Salário", "Salários Mínimos"};
        
        // Criar o modelo de tabela
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Preencher a tabela com os dados dos funcionários
        funcionarios.forEach(f -> {
            // Calcular o número de salários mínimos (arredondando para baixo)
            int salariosMinimos = f.getSalario().divide(salarioMinimo, RoundingMode.FLOOR).intValue();
            
            Object[] linha = {
                f.getNome(),
                f.getDataNascimento().format(formatter),
                f.getFuncao(),
                String.format("R$ %,.2f", f.getSalario()),
                salariosMinimos
            };
            modelo.addRow(linha);
        });
        
        // Criar a JTable com o modelo e exibir em um JScrollPane
        JTable tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        // Exibe a tabela na interface gráfica
        JOptionPane.showMessageDialog(null, scrollPane, "Funcionários e Salários Mínimos", JOptionPane.INFORMATION_MESSAGE);
    }
    


    private static void removerJoao() {
        funcionarios.removeIf(f -> f.getNome().equals("João"));
        JOptionPane.showMessageDialog(null, "Funcionário João removido!");
    }
}
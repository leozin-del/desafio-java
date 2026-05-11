package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    // Constantes para formatação de data
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final NumberFormat MONEY_FORMAT =
            NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

    public static void main(String[] args) {
        // Inicializa a lista de funcionários com os dados fornecidos
        List<Funcionario> funcionarios = inicializarFuncionarios();

        // 3.1 - Inserir todos os funcionários (já feito na inicialização)
        System.out.println("=== LISTA DE FUNCIONÁRIOS ===");
        imprimirFuncionarios(funcionarios);

        // 3.2 - Remover o funcionário chamado João
        // Usamos removeIf para remover baseado em uma condição
        funcionarios.removeIf(funcionario ->
                funcionario.getNome().equalsIgnoreCase("João"));

        // 3.3 - Imprimir funcionários após remoção
        System.out.println("\n=== FUNCIONÁRIOS APÓS REMOVER JOÃO ===");
        imprimirFuncionarios(funcionarios);

        // 3.4 - Aumentar salário de todos em 10%
        // Criamos um método separado para isso
        aumentarSalarios(funcionarios, new BigDecimal("1.10"));
        System.out.println("\n=== SALÁRIOS COM 10% DE AUMENTO ===");
        imprimirFuncionarios(funcionarios);

        // 3.5 - Agrupar funcionários por função
        // Usamos Collectors.groupingBy para criar um mapa onde a chave é a função
        Map<String, List<Funcionario>> funcionariosPorFuncao =
                funcionarios.stream()
                        .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\n=== FUNCIONÁRIOS AGRUPADOS POR FUNÇÃO ===");
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        System.out.println("\n=== ANIVERSARIANTES DOS MESES 10 E 12 ===");
        funcionarios.stream()
                .filter(funcionario -> {
                    int mes = funcionario.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .forEach(Main::imprimirFuncionario);

        // 3.9 - Encontrar o funcionário mais velho
        imprimirFuncionarioMaisVelho(funcionarios);

        // 3.10 - Imprimir nomes em ordem alfabética
        System.out.println("\n=== FUNCIONÁRIOS EM ORDEM ALFABÉTICA ===");
        // Ordenamos usando Comparator e imprimimos apenas os nomes
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(funcionario ->
                        System.out.println(funcionario.getNome()));

        // 3.11 - Calcular o total dos salários
        // Usamos map para pegar os salários e reduce para somar
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\n=== TOTAL DOS SALÁRIOS ===");
        System.out.println(MONEY_FORMAT.format(totalSalarios));

        // 3.12 - Calcular quantos salários mínimos cada um ganha
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n=== SALÁRIOS MÍNIMOS POR FUNCIONÁRIO ===");
        imprimirSalariosMinimos(funcionarios, salarioMinimo);
    }

    /**
     * Método para inicializar a lista de funcionários com os dados do desafio.
     * Cada funcionário é criado com nome, data de nascimento, salário e função.
     */
    private static List<Funcionario> inicializarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18),
                new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2),
                new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14),
                new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5),
                new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19),
                new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31),
                new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8),
                new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24),
                new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2),
                new BigDecimal("2799.93"), "Gerente"));
        return funcionarios;
    }


    private static void aumentarSalarios(List<Funcionario> funcionarios, BigDecimal percentual) {
        // Para cada funcionário, calcula o novo salário multiplicando pelo percentual
        for (Funcionario funcionario : funcionarios) {
            BigDecimal novoSalario = funcionario.getSalario().multiply(percentual);
            funcionario.setSalario(novoSalario);
        }
    }


    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        // Chama o método de imprimir um funcionário para cada um da lista
        for (Funcionario funcionario : funcionarios) {
            imprimirFuncionario(funcionario);
        }
    }


    private static void imprimirFuncionario(Funcionario funcionario) {
        System.out.println(
                "Nome: " + funcionario.getNome()
                        + " | Data Nascimento: " +
                        funcionario.getDataNascimento().format(DATE_FORMAT)
                        + " | Salário: " +
                        MONEY_FORMAT.format(funcionario.getSalario())
                        + " | Função: " +
                        funcionario.getFuncao()
        );
    }


    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        // Para cada entrada no mapa (função e lista), imprime a função e depois os funcionários
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("\nFunção: " + entry.getKey());
            for (Funcionario funcionario : entry.getValue()) {
                imprimirFuncionario(funcionario);
            }
        }
    }


    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        // Encontra o funcionário com a data de nascimento mais antiga
        Funcionario maisVelho = null;
        for (Funcionario funcionario : funcionarios) {
            if (maisVelho == null || funcionario.getDataNascimento().isBefore(maisVelho.getDataNascimento())) {
                maisVelho = funcionario;
            }
        }
        if (maisVelho != null) {
            int idade = Period.between(
                    maisVelho.getDataNascimento(),
                    LocalDate.now()
            ).getYears();
            System.out.println("\n=== FUNCIONÁRIO MAIS VELHO ===");
            System.out.println("Nome: " + maisVelho.getNome());
            System.out.println("Idade: " + idade);
        }
    }


    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        // Para cada funcionário, divide o salário pelo mínimo e arredonda
        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantidadeSalarios =
                    funcionario.getSalario()
                            .divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(
                    funcionario.getNome() + " ganha " +
                            quantidadeSalarios + " salários mínimos"
            );
        }
    }
}

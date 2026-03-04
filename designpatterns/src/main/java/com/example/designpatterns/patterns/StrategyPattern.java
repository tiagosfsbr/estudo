package com.example.designpatterns.patterns;

/**
 * PADRÃO STRATEGY
 * 
 * O padrão Strategy define uma família de algoritmos, encapsula cada um deles
 * e os torna intercambiáveis. Permite que o algoritmo varie independentemente
 * dos clientes que o utilizam.
 * 
 * Componentes principais:
 * - Strategy: interface que define o contrato dos algoritmos
 * - ConcreteStrategy: implementações concretas dos algoritmos
 * - Context: classe que usa a strategy
 * 
 * Casos de uso:
 * - Diferentes formas de pagamento (débito, crédito, boleto) ✓
 * - Algoritmos de ordenação diferentes
 * - Estratégias de cálculo de preço (com desconto, sem desconto, etc)
 */

// ===== INTERFACE STRATEGY =====
interface EstrategiaPagemento {
    /**
     * Define o contrato para estratégias de pagamento
     * 
     * @param valor o valor a ser pago
     */
    void processar(double valor);
    
    /**
     * Retorna o nome da estratégia de pagamento
     */
    String obterNome();
}

// ===== CONCRETE STRATEGIES =====

/**
 * Estratégia de pagamento por débito
 * 
 * Características:
 * - Débita diretamente da conta bancária
 * - Sem juros
 * - Processamento instantâneo
 */
class PagamentoDebito implements EstrategiaPagemento {
    private String numeroConta;
    private String agencia;

    public PagamentoDebito(String agencia, String numeroConta) {
        this.agencia = agencia;
        this.numeroConta = numeroConta;
    }

    @Override
    public void processar(double valor) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     PAGAMENTO POR DÉBITO              ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Agência: " + String.format("%-27s", agencia) + "║");
        System.out.println("║ Conta: " + String.format("%-29s", numeroConta) + "║");
        System.out.println("║ Valor: R$ " + String.format("%-26.2f", valor) + "║");
        System.out.println("║ Juros: 0%                              ║");
        System.out.println("║ Status: ✓ Processamento instantâneo   ║");
        System.out.println("║                                        ║");
        System.out.println("║ ✓ Pagamento aprovado!                 ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    @Override
    public String obterNome() {
        return "Débito Bancário";
    }
}

/**
 * Estratégia de pagamento por crédito
 * 
 * Características:
 * - Débita do limite do cartão de crédito
 * - Pode ter juros se não pagar na data de vencimento
 * - Oferece proteção do consumidor
 */
class PagamentoCredito implements EstrategiaPagemento {
    private String numeroCarte;
    private String bandeira;
    private double taxaJuro;

    public PagamentoCredito(String numeroCarte, String bandeira) {
        this.numeroCarte = numeroCarte;
        this.bandeira = bandeira;
        this.taxaJuro = 2.5; // Taxa média de 2.5% ao mês
    }

    @Override
    public void processar(double valor) {
        double juros = valor * (taxaJuro / 100);
        double total = valor + juros;

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     PAGAMENTO POR CRÉDITO             ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Bandeira: " + String.format("%-27s", bandeira) + "║");
        System.out.println("║ Cartão: ****-****-****-" + numeroCarte.substring(12) + "  ║");
        System.out.println("║ Valor: R$ " + String.format("%-26.2f", valor) + "║");
        System.out.println("║ Juros (" + String.format("%.1f", taxaJuro) + "%): R$ " + String.format("%-17.2f", juros) + "║");
        System.out.println("║ Total: R$ " + String.format("%-26.2f", total) + "║");
        System.out.println("║ Vencimento: próximas 30 dias           ║");
        System.out.println("║                                        ║");
        System.out.println("║ ✓ Pagamento aprovado!                 ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    @Override
    public String obterNome() {
        return "Cartão de Crédito " + bandeira;
    }
}

/**
 * Estratégia de pagamento por boleto
 * 
 * Características:
 * - Gera um código de boleto para pagamento em banco
 * - Sem juros
 * - Pode levar 1-2 dias úteis para ser processado
 */
class PagamentoBoleto implements EstrategiaPagemento {
    private String codigoBoleto;
    private String banco;

    public PagamentoBoleto(String banco) {
        this.banco = banco;
        this.codigoBoleto = gerarCodigoBoleto();
    }

    private String gerarCodigoBoleto() {
        long codigo = System.currentTimeMillis();
        return String.format("%032d", codigo % 9999999999999999L);
    }

    @Override
    public void processar(double valor) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     PAGAMENTO POR BOLETO              ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Banco: " + String.format("%-31s", banco) + "║");
        System.out.println("║ Código: " + String.format("%-30s", codigoBoleto) + "║");
        System.out.println("║ Valor: R$ " + String.format("%-26.2f", valor) + "║");
        System.out.println("║ Juros: 0%                              ║");
        System.out.println("║ Processamento: 1-2 dias úteis          ║");
        System.out.println("║                                        ║");
        System.out.println("║ ✓ Boleto gerado com sucesso!          ║");
        System.out.println("║ Imprima ou copie o código para pagar  ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    @Override
    public String obterNome() {
        return "Boleto Bancário - " + banco;
    }
}

// ===== CONTEXT =====

/**
 * Classe que utiliza a estratégia de pagamento
 * Representa um carrinho de compras ou processador de pagamentos
 */
class ProcessadorPagamento {
    private EstrategiaPagemento estrategia;
    private String pedidoID;

    public ProcessadorPagamento(String pedidoID) {
        this.pedidoID = pedidoID;
    }

    /**
     * Define qual estratégia de pagamento será utilizada
     */
    public void setEstrategiaPagemento(EstrategiaPagemento estrategia) {
        this.estrategia = estrategia;
        System.out.println("\n[SISTEMA] Forma de pagamento alterada para: " + estrategia.obterNome());
    }

    /**
     * Executa o pagamento usando a estratégia definida
     */
    public void executarPagemento(double valor) {
        if (estrategia == null) {
            System.out.println("[ERRO] Nenhuma estratégia de pagamento definida!");
            return;
        }
        System.out.println("\n[SISTEMA] Processando pagamento para Pedido #" + pedidoID);
        estrategia.processar(valor);
    }

    public String obterPedidoID() {
        return pedidoID;
    }
}

// ===== DEMONSTRAÇÃO =====
public class StrategyPattern {
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════");
        System.out.println("   === PADRÃO STRATEGY - FORMAS DE PAGAMENTO ===");
        System.out.println("════════════════════════════════════════════");

        // Criando o processador de pagamento
        ProcessadorPagamento processador = new ProcessadorPagamento("PED-2026-001");
        double valorPedido = 250.50;

        System.out.println("\n📦 Pendido #PED-2026-001");
        System.out.println("💰 Valor: R$ " + String.format("%.2f", valorPedido));

        // ===== TESTE 1: PAGAMENTO COM DÉBITO =====
        System.out.println("\n\n--- TESTE 1: PAGAMENTO COM DÉBITO ---");
        processador.setEstrategiaPagemento(new PagamentoDebito("0001", "123456-7"));
        processador.executarPagemento(valorPedido);

        // ===== TESTE 2: PAGAMENTO COM CRÉDITO =====
        System.out.println("\n\n--- TESTE 2: PAGAMENTO COM CRÉDITO ---");
        processador.setEstrategiaPagemento(new PagamentoCredito("4111111111111111", "VISA"));
        processador.executarPagemento(valorPedido);

        // ===== TESTE 3: PAGAMENTO COM BOLETO =====
        System.out.println("\n\n--- TESTE 3: PAGAMENTO COM BOLETO ---");
        processador.setEstrategiaPagemento(new PagamentoBoleto("Banco do Brasil"));
        processador.executarPagemento(valorPedido);

        // ===== TESTE 4: MUDANDO PARA OUTRA ESTRATÉGIA =====
        System.out.println("\n\n--- TESTE 4: CLIENTE MUDA PARA CRÉDITO ---");
        processador.setEstrategiaPagemento(new PagamentoCredito("5555555555554444", "Mastercard"));
        processador.executarPagemento(valorPedido);

        // ===== RESUMO =====
        System.out.println("\n\n════════════════════════════════════════════");
        System.out.println("   === RESUMO DO PADRÃO STRATEGY ===");
        System.out.println("════════════════════════════════════════════");
        System.out.println("✓ Diferentes estratégias de pagamento");
        System.out.println("✓ Fácil adicionar novas formas de pagamento");
        System.out.println("✓ Troca de estratégia em tempo de execução");
        System.out.println("✓ Código limpo e sem múltiplos if-else");
        System.out.println("✓ Cada estratégia encapsulada em sua própria classe");
    }
}

package com.example.designpatterns.patterns;

/**
 * PADRÃO SINGLETON
 * 
 * O padrão Singleton garante que uma classe tenha apenas uma única instância
 * e fornece um ponto de acesso global a essa instância.
 * 
 * Características principais:
 * - Construtor privado para evitar instanciação externa
 * - Método estático para obter a instância única
 * - Variável estática para armazenar a instância
 * 
 * Casos de uso:
 * - Gerenciadores de conexão com banco de dados
 * - Logger centralizado
 * - Configurações da aplicação
 * - Cache
 */
public class SingletonPattern {

    // Variável estática que armazena a única instância
    private static SingletonPattern instancia;

    /**
     * Construtor privado - impede a instanciação direta da classe
     */
    private SingletonPattern() {
        System.out.println("Singleton instanciado!");
    }

    /**
     * Método público que fornece acesso à única instância
     * 
     * @return A instância única do Singleton
     */
    public static SingletonPattern obterInstancia() {
        // Lazy initialization: cria a instância apenas quando necessário
        if (instancia == null) {
            instancia = new SingletonPattern();
        }
        return instancia;
    }

    public void exibirMensagem() {
        System.out.println("Mensagem do Singleton!");
    }

    // ===== DEMONSTRAÇÃO =====
    public static void main(String[] args) {
        System.out.println("=== PADRÃO SINGLETON ===\n");

        // Primeira chamada - cria a instância
        SingletonPattern singleton1 = SingletonPattern.obterInstancia();

        // Segunda chamada - retorna a mesma instância
        SingletonPattern singleton2 = SingletonPattern.obterInstancia();

        // Verifica se são a mesma instância
        System.out.println("singleton1 == singleton2: " + (singleton1 == singleton2));
        System.out.println("Mesmo objeto na memória: " + (singleton1.hashCode() == singleton2.hashCode()));

        singleton1.exibirMensagem();
    }
}

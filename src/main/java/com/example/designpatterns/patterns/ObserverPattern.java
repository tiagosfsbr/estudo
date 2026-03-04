package com.example.designpatterns.patterns;

import java.util.*;

/**
 * PADRÃO OBSERVER
 * 
 * O padrão Observer define uma dependência um-para-muitos entre objetos,
 * de forma que quando um objeto muda de estado, todos os seus dependentes
 * são notificados e atualizados automaticamente.
 * 
 * Componentes principais:
 * - Subject (Observable): objeto observado que notifica os observers
 * - Observer: interface para objetos que querem ser notificados
 * - ConcreteObserver: implementações que reagem às notificações
 * 
 * Casos de uso:
 * - Sistemas de eventos
 * - Atualização automática de views quando o modelo muda
 * - Chat aberto para múltiplos usuários
 * - Notificações em tempo real
 */

// ===== INTERFACE OBSERVER =====

/**
 * Interface que define como os observadores devem reagir às notificações
 */
interface Observador {
    /**
     * Método chamado quando o subject notifica
     * 
     * @param mensagem A mensagem da notificação
     */
    void atualizar(String mensagem);
}

// ===== SUBJECT (OBSERVABLE) =====

/**
 * Classe responsável por manter uma lista de observadores
 * e notificá-los quando seu estado muda
 */
class CanalNoticias {
    // Lista de observadores inscritos
    private List<Observador> observadores = new ArrayList<>();
    private String ultimaNoticia;

    /**
     * Adiciona um observador à lista
     */
    public void inscrever(Observador observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
            System.out.println("Nova inscrição: " + observador.getClass().getSimpleName());
        }
    }

    /**
     * Remove um observador da lista
     */
    public void desinscrever(Observador observador) {
        if (observadores.remove(observador)) {
            System.out.println("Desinscrito: " + observador.getClass().getSimpleName());
        }
    }

    /**
     * Notifica todos os observadores sobre uma nova notícia
     */
    public void publicarNoticia(String noticia) {
        System.out.println("\n[CANAL] Nova notícia publicada: " + noticia);
        this.ultimaNoticia = noticia;
        
        // Notifica todos os observadores inscritos
        notificarObservadores();
    }

    /**
     * Método privado que notifica todos os observadores
     */
    private void notificarObservadores() {
        System.out.println("[CANAL] Notificando " + observadores.size() + " observador(es)...\n");
        for (Observador observador : observadores) {
            observador.atualizar(ultimaNoticia);
        }
    }

    public String obterUltimaNoticia() {
        return ultimaNoticia;
    }
}

// ===== CONCRETE OBSERVERS =====

/**
 * Observador que exibe notícias no console
 */
class DisplayConsole implements Observador {
    private String nome;

    public DisplayConsole(String nome) {
        this.nome = nome;
    }

    @Override
    public void atualizar(String mensagem) {
        System.out.println("  [" + nome + "] Exibindo notícia: " + mensagem);
    }
}

/**
 * Observador que armazena notícias em um arquivo
 */
class ArmazenadorArquivos implements Observador {
    private String nome;

    public ArmazenadorArquivos(String nome) {
        this.nome = nome;
    }

    @Override
    public void atualizar(String mensagem) {
        System.out.println("  [" + nome + "] Salvando notícia em arquivo: " + mensagem);
    }
}

/**
 * Observador que envia notícias por email
 */
class NotificadorEmail implements Observador {
    private String emailDestinatario;

    public NotificadorEmail(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    @Override
    public void atualizar(String mensagem) {
        System.out.println("  [EMAIL] Enviando para " + emailDestinatario + ": " + mensagem);
    }
}

/**
 * Observador que envia notícias por SMS
 */
class NotificadorSMS implements Observador {
    private String numeroTelefone;

    public NotificadorSMS(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    @Override
    public void atualizar(String mensagem) {
        System.out.println("  [SMS] Enviando para " + numeroTelefone + ": " + mensagem);
    }
}

// ===== DEMONSTRAÇÃO =====
public class ObserverPattern {
    public static void main(String[] args) {
        System.out.println("=== PADRÃO OBSERVER ===");

        // Criando o subject (canal de notícias)
        CanalNoticias canal = new CanalNoticias();

        // Criando os observadores (subscribers)
        DisplayConsole telaPrincipal = new DisplayConsole("Tela Principal");
        ArmazenadorArquivos arquivos = new ArmazenadorArquivos("Banco de Dados");
        NotificadorEmail emailNotificador = new NotificadorEmail("usuario@email.com");
        NotificadorSMS smsNotificador = new NotificadorSMS("+55 11 99999-8888");

        // ===== TESTE 1: INSCRIÇÃO DE OBSERVADORES =====
        System.out.println("\n--- TESTE 1: INSCRIÇÃO DE OBSERVADORES ---\n");
        canal.inscrever(telaPrincipal);
        canal.inscrever(arquivos);
        canal.inscrever(emailNotificador);

        // ===== TESTE 2: PUBLICAÇÃO DE NOTÍCIA =====
        System.out.println("\n--- TESTE 2: PUBLICAÇÃO DE NOTÍCIA ---");
        canal.publicarNoticia("Novo produto lançado com sucesso!");

        // ===== TESTE 3: ADICIONANDO UM NOVO OBSERVADOR =====
        System.out.println("\n--- TESTE 3: ADICIONANDO NOVO OBSERVADOR ---\n");
        canal.inscrever(smsNotificador);

        // ===== TESTE 4: PUBLICAÇÃO COM NOVO OBSERVADOR =====
        System.out.println("\n--- TESTE 4: PUBLICAÇÃO COM NOVO OBSERVADOR ---");
        canal.publicarNoticia("Sistema de autenticação foi atualizado!");

        // ===== TESTE 5: DESINSCRITO DE UM OBSERVADOR =====
        System.out.println("\n--- TESTE 5: REMOVENDO UM OBSERVADOR ---\n");
        canal.desinscrever(emailNotificador);

        // ===== TESTE 6: PUBLICAÇÃO APÓS DESINSCRITO =====
        System.out.println("\n--- TESTE 6: PUBLICAÇÃO APÓS REMOVER OBSERVADOR ---");
        canal.publicarNoticia("Manutenção programada para hoje à noite!");

        // ===== RESUMO =====
        System.out.println("\n\n--- VANTAGENS DO PADRÃO OBSERVER ---");
        System.out.println("✓ Desacoplamento entre o Sistema e os Observadores");
        System.out.println("✓ Suporta broadcast communication (enviar para múltiplos listeners)");
        System.out.println("✓ Fácil adicionar/remover observadores em tempo de execução");
        System.out.println("✓ Reutilizável em múltiplos contextos");
    }
}

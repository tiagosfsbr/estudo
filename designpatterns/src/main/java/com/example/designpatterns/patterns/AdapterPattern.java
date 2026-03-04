package com.example.designpatterns.patterns;

/**
 * PADRÃO ADAPTER
 * 
 * O padrão Adapter permite que objetos com interfaces incompatíveis trabalhem juntos.
 * É usado quando você precisa fazer uma classe existente trabalhar com outra que tem
 * uma interface diferente.
 * 
 * Componentes principais:
 * - Target: interface esperada pelo cliente
 * - Adapter: classe que faz a adaptação entre Target e Adaptee
 * - Adaptee: classe existente com interface diferente que precisa ser adaptada
 * 
 * Casos de uso:
 * - Integração de bibliotecas legadas com código novo
 * - Conexão de sistemas com interfaces diferentes
 * - Conversão de formatos de dados
 */

// ===== INTERFACE TARGET (esperada) =====

/**
 * Interface que o cliente espera usar
 */
interface TomadasModernas {
    void fornecerEletricidade();
}

// ===== CLASSE ADAPTEE (interface incompatível) =====

/**
 * Classe que tem uma interface diferente da esperada
 */
class TomadasAntigas {
    public void enviarEletricidade() {
        System.out.println("[TOMADA ANTIGA] Enviando eletricidade no padrão antigo...");
    }
}

// ===== ADAPTER =====

/**
 * Adaptador que permite usar a tomada antiga com a interface moderna
 * Implementa a interface esperada (TomadasModernas) e usa a classe incompatível (TomadasAntigas)
 */
class AdaptadorTomada implements TomadasModernas {
    private TomadasAntigas tomadasAntigas;

    /**
     * Construtor que recebe a instância que precisa ser adaptada
     */
    public AdaptadorTomada(TomadasAntigas tomadasAntigas) {
        this.tomadasAntigas = tomadasAntigas;
    }

    /**
     * Implementa o método esperado (TomadasModernas)
     * chamando o método existente (TomadasAntigas)
     */
    @Override
    public void fornecerEletricidade() {
        System.out.println("[ADAPTADOR] Convertendo a interface...");
        // Converte a chamada de fornecerEletricidade() para enviarEletricidade()
        tomadasAntigas.enviarEletricidade();
        System.out.println("[ADAPTADOR] Interface adaptada com sucesso!\n");
    }
}

// ===== CLIENTE =====

/**
 * Classe que espera usar a interface TomadasModernas
 */
class Notebook {
    private TomadasModernas tomada;

    public Notebook(TomadasModernas tomada) {
        this.tomada = tomada;
    }

    public void recarregar() {
        System.out.println("[NOTEBOOK] Conectando à tomada...");
        tomada.fornecerEletricidade();
        System.out.println("[NOTEBOOK] Recarregamento concluído!\n");
    }
}

// ===== DEMONSTRAÇÃO =====
public class AdapterPattern {
    public static void main(String[] args) {
        System.out.println("=== PADRÃO ADAPTER ===\n");

        // Criando uma tomada antiga (incompatível)
        TomadasAntigas tomadasAntigas = new TomadasAntigas();

        // Adaptando a tomada antiga para a interface moderna
        TomadasModernas tomadasAdaptadas = new AdaptadorTomada(tomadasAntigas);

        // Usando o notebook com a tomada adaptada
        Notebook notebook = new Notebook(tomadasAdaptadas);

        System.out.println("--- TESTE: RECARGA DO NOTEBOOK COM TOMADA ADAPTADA ---");
        notebook.recarregar();

        System.out.println("--- RESULTADO ---");
        System.out.println("O Adapter permitiu que o Notebook (que espera TomadasModernas)");
        System.out.println("funcionasse com TomadasAntigas (interface incompatível)");
    }
}

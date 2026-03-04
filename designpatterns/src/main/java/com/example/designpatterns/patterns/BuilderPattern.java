package com.example.designpatterns.patterns;

/**
 * PADRÃO BUILDER
 * 
 * O padrão Builder separa a construção de um objeto complexo de sua representação,
 * permitindo que o mesmo processo de construção crie diferentes representações.
 * 
 * Componentes principais:
 * - Product: o objeto complexo a ser construído
 * - Builder: classe que define os passos para construir o objeto
 * - ConcreteBuilder: implementação concreta dos passos
 * - Director (opcional): coordena a construção
 * 
 * Casos de uso:
 * - Construção de objetos com muitos parâmetros
 * - Criação de objetos com configurações complexas
 * - Criar diferentes variações de um mesmo objeto
 * - Evitar construtores com muitos parâmetros
 */

// ===== PRODUTO =====

/**
 * Classe que representa um computador (objeto complexo)
 */
class Computador {
    // Componentes do computador
    private String processador;
    private String placaMae;
    private String memoriaRAM;
    private String armazenamento;
    private String placaVideo;
    private String fonteAlimentacao;

    /**
     * Construtor privado - apenas o Builder pode criar instâncias
     */
    private Computador(ComputadorBuilder builder) {
        this.processador = builder.processador;
        this.placaMae = builder.placaMae;
        this.memoriaRAM = builder.memoriaRAM;
        this.armazenamento = builder.armazenamento;
        this.placaVideo = builder.placaVideo;
        this.fonteAlimentacao = builder.fonteAlimentacao;
    }

    public void exibirEspecificacoes() {
        System.out.println("\n========== COMPUTADOR ==========");
        System.out.println("Processador: " + (processador != null ? processador : "Não configurado"));
        System.out.println("Placa Mãe: " + (placaMae != null ? placaMae : "Não configurada"));
        System.out.println("Memória RAM: " + (memoriaRAM != null ? memoriaRAM : "Não configurada"));
        System.out.println("Armazenamento: " + (armazenamento != null ? armazenamento : "Não configurado"));
        System.out.println("Placa de Vídeo: " + (placaVideo != null ? placaVideo : "Não configurada"));
        System.out.println("Fonte: " + (fonteAlimentacao != null ? fonteAlimentacao : "Não configurada"));
        System.out.println("=================================\n");
    }

    // ===== CLASSE BUILDER (Builder Pattern) =====

    /**
     * Classe Builder aninhada que constrói o Computador passo a passo
     */
    public static class ComputadorBuilder {
        // Atributos opcionais
        private String processador;
        private String placaMae;
        private String memoriaRAM;
        private String armazenamento;
        private String placaVideo;
        private String fonteAlimentacao;

        /**
         * Define o processador
         */
        public ComputadorBuilder comProcessador(String processador) {
            this.processador = processador;
            return this; // Retorna 'this' para encadeamento de métodos (fluent interface)
        }

        /**
         * Define a placa mãe
         */
        public ComputadorBuilder comPlacaMae(String placaMae) {
            this.placaMae = placaMae;
            return this;
        }

        /**
         * Define a memória RAM
         */
        public ComputadorBuilder comMemoriaRAM(String memoriaRAM) {
            this.memoriaRAM = memoriaRAM;
            return this;
        }

        /**
         * Define o armazenamento
         */
        public ComputadorBuilder comArmazenamento(String armazenamento) {
            this.armazenamento = armazenamento;
            return this;
        }

        /**
         * Define a placa de vídeo
         */
        public ComputadorBuilder comPlacaVideo(String placaVideo) {
            this.placaVideo = placaVideo;
            return this;
        }

        /**
         * Define a fonte de alimentação
         */
        public ComputadorBuilder comFonteAlimentacao(String fonteAlimentacao) {
            this.fonteAlimentacao = fonteAlimentacao;
            return this;
        }

        /**
         * Constrói e retorna o objeto Computador
         */
        public Computador construir() {
            return new Computador(this);
        }
    }
}

// ===== DEMONSTRAÇÃO =====
public class BuilderPattern {
    public static void main(String[] args) {
        System.out.println("=== PADRÃO BUILDER ===");

        // Construindo um computador para jogos
        System.out.println("\n--- TESTE 1: COMPUTADOR GAMER ---");
        Computador computerGamer = new Computador.ComputadorBuilder()
                .comProcessador("Intel i9-13900K")
                .comPlacaMae("ASUS ROG MAXIMUS Z790")
                .comMemoriaRAM("64GB DDR5")
                .comArmazenamento("2TB NVMe SSD")
                .comPlacaVideo("NVIDIA RTX 4090")
                .comFonteAlimentacao("1000W Gold")
                .construir();

        computerGamer.exibirEspecificacoes();

        // Construindo um computador básico para escritório
        System.out.println("\n--- TESTE 2: COMPUTADOR PARA ESCRITÓRIO ---");
        Computador computerEscritorio = new Computador.ComputadorBuilder()
                .comProcessador("Intel i5-13600K")
                .comPlacaMae("ASUS Prime B760")
                .comMemoriaRAM("16GB DDR4")
                .comArmazenamento("512GB SSD")
                .comFonteAlimentacao("600W Bronze")
                .construir();

        computerEscritorio.exibirEspecificacoes();

        // Construindo um computador com configuração mínima
        System.out.println("\n--- TESTE 3: COMPUTADOR COM CONFIGURAÇÃO MÍNIMA ---");
        Computador computerMinimo = new Computador.ComputadorBuilder()
                .comProcessador("Intel i3-13100")
                .comMemoriaRAM("8GB DDR4")
                .comArmazenamento("256GB SSD")
                .construir();

        computerMinimo.exibirEspecificacoes();

        System.out.println("\n--- VANTAGENS DO PADRÃO BUILDER ---");
        System.out.println("✓ Código mais legível e fluente");
        System.out.println("✓ Evita construtores com muitos parâmetros");
        System.out.println("✓ Permite criar objetos com diferentes combinações");
        System.out.println("✓ Facilita a adição de novos atributos opcionais");
    }
}

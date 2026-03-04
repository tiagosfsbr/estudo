/**
 * PADRÕES DE DESIGN - RESUMO E GUIA DE EXECUÇÃO
 * 
 * Este pacote contém 5 exemplos implementados dos padrões de design mais utilizados.
 * Cada classe possui comentários detalhados explicando como o padrão funciona.
 * 
 * ============================================================================
 * PADRÕES IMPLEMENTADOS:
 * ============================================================================
 * 
 * 1. SINGLETON ✓
 *    - Localização: SingletonPattern.java
 *    - Descrição: Garante que uma classe tenha apenas uma única instância
 *    - Exemplo: Gerenciador de banco de dados, Logger, Configurações
 *    - Executar: java SingletonPattern
 * 
 * 2. STRATEGY ✓
 *    - Localização: StrategyPattern.java
 *    - Descrição: Define uma família de algoritmos intercambiáveis
 *    - Exemplo: Diferentes formas de ordenação (Bubble Sort, Quick Sort)
 *    - Executar: java StrategyPattern
 * 
 * 3. ADAPTER ✓
 *    - Localização: AdapterPattern.java
 *    - Descrição: Permite que interfaces incompatíveis trabalhem juntas
 *    - Exemplo: Adaptação de tomadas antigas para interface moderna
 *    - Executar: java AdapterPattern
 * 
 * 4. BUILDER ✓
 *    - Localização: BuilderPattern.java
 *    - Descrição: Constrói objetos complexos passo a passo
 *    - Exemplo: Construção de diferentes configurações de computadores
 *    - Executar: java BuilderPattern
 * 
 * 5. OBSERVER ✓
 *    - Localização: ObserverPattern.java
 *    - Descrição: Define dependência um-para-muitos com notificação automática
 *    - Exemplo: Sistema de notícias que notifica múltiplos observadores
 *    - Executar: java ObserverPattern
 * 
 * ============================================================================
 * COMO EXECUTAR:
 * ============================================================================
 * 
 * Antes de executar, certifique-se de que está na pasta do projeto.
 * 
 * Opção 1 - Compilar e Executar Com Maven:
 *   mvn clean compile exec:java -Dexec.mainClass="com.example.designpatterns.patterns.SingletonPattern"
 *   mvn clean compile exec:java -Dexec.mainClass="com.example.designpatterns.patterns.StrategyPattern"
 *   mvn clean compile exec:java -Dexec.mainClass="com.example.designpatterns.patterns.AdapterPattern"
 *   mvn clean compile exec:java -Dexec.mainClass="com.example.designpatterns.patterns.BuilderPattern"
 *   mvn clean compile exec:java -Dexec.mainClass="com.example.designpatterns.patterns.ObserverPattern"
 * 
 * Opção 2 - Compilar e Executar Com Java Direto:
 *   javac -d target/classes src/main/java/com/example/designpatterns/patterns/*.java
 *   java -cp target/classes com.example.designpatterns.patterns.SingletonPattern
 *   java -cp target/classes com.example.designpatterns.patterns.StrategyPattern
 *   java -cp target/classes com.example.designpatterns.patterns.AdapterPattern
 *   java -cp target/classes com.example.designpatterns.patterns.BuilderPattern
 *   java -cp target/classes com.example.designpatterns.patterns.ObserverPattern
 * 
 * ============================================================================
 * COMPARATIVO VISUAL DOS PADRÕES:
 * ============================================================================
 * 
 * SINGLETON vs FACTORY:
 *   - Singleton: Uma única instância (sempre a mesma)
 *   - Factory: Cria múltiplas instâncias (pode ser diferente cada vez)
 * 
 * STRATEGY vs TEMPLATE METHOD:
 *   - Strategy: Objeto que define o algoritmo (composição)
 *   - Template Method: Herança que define o algoritmo (herança)
 * 
 * ADAPTER vs BRIDGE:
 *   - Adapter: Compatibilidade (problema existe, precisa adaptar)
 *   - Bridge: Flexibilidade (design para evitar o problema)
 * 
 * BUILDER vs PROTOTYPE:
 *   - Builder: Constrói passo a passo (fluent interface)
 *   - Prototype: Clona um objeto existente
 * 
 * OBSERVER vs PUBLISHER/SUBSCRIBER:
 *   - Observer: Acoplamento direto (sabe quem notificar)
 *   - Pub/Sub: Desacoplamento via message broker
 * 
 * ============================================================================
 * QUANDO USAR CADA PADRÃO:
 * ============================================================================
 * 
 * Use SINGLETON quando:
 *   ✓ Precisa de apenas uma instância (logger, cache, config)
 *   ✓ Quer acesso global a essa instância
 *   ! Cuidado com threads (use synchronized ou eager initialization)
 * 
 * Use STRATEGY quando:
 *   ✓ Precisa de múltiplos algoritmos intercambiáveis
 *   ✓ Quer evitar múltiplos if-else/switch
 *   ✓ Algoritmos podem mudar em tempo de execução
 * 
 * Use ADAPTER quando:
 *   ✓ Precisa usar uma classe com interface incompatível
 *   ✓ Não pode modificar a classe existente
 *   ✓ Quer integrar código legado com novo código
 * 
 * Use BUILDER quando:
 *   ✓ Objeto tem muitos atributos opcionais
 *   ✓ Quer evitar construtores com muitos parâmetros
 *   ✓ Quer fluent interface (chamadas encadeadas)
 * 
 * Use OBSERVER quando:
 *   ✓ Múltiplos objetos precisam ser notificados de mudanças
 *   ✓ Quer desacoplamento entre observador e observado
 *   ✓ Event-driven architecture (MVC, chat, notificações)
 * 
 * ============================================================================
 * DICAS IMPORTANTES:
 * ============================================================================
 * 
 * 1. SINGLETON THREAD-SAFE:
 *    - Use synchronized ou eager initialization
 *    - Considere usar enum (Java garante thread-safety)
 * 
 * 2. STRATEGY DINÂMICA:
 *    - Pode trocar a strategy em tempo de execução
 *    - Útil para escolher algoritmo baseado em entrada do usuário
 * 
 * 3. ADAPTER COMPATIBILIDADE:
 *    - Não modifique classes existentes (Princípio Aberto/Fechado)
 *    - Use composição ao invés de herança
 * 
 * 4. BUILDER COMPOSTAS:
 *    - Classe Builder pode estar aninhada (como no exemplo)
 *    - Use varargs para atributos opcionais mais complexos
 * 
 * 5. OBSERVER PERFORMANCE:
 *    - Cuidado com muitos observadores (overhead)
 *    - Remova observadores quando não precisar mais
 * 
 * ============================================================================
 */

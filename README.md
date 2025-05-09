# Challenge Conversor Moeda Java

Um aplicativo de console simples em Java para converter valores entre diferentes moedas utilizando a API externa [ExchangeRate-API](https://www.exchangerate-api.com/).

## Descrição

Este projeto permite ao usuário escolher pares de moedas pré-definidos (como Dólar Americano para Real Brasileiro, Peso Argentino para Dólar, etc.) e converter um valor específico entre eles. As taxas de câmbio são obtidas em tempo real através de uma chamada à API ExchangeRate-API. A última conversão realizada é salva em um arquivo JSON (`ultima_conversao.json`).

## Funcionalidades

*   Conversão entre os seguintes pares de moedas:
    *   Dólar Americano (USD) <=> Peso Argentino (ARS)
    *   Dólar Americano (USD) <=> Real Brasileiro (BRL)
    *   Dólar Americano (USD) <=> Peso Colombiano (COP)
    *   Dólar Americano (USD) <=> Euro (EUR)
*   Utiliza a API [ExchangeRate-API](https://www.exchangerate-api.com/) para obter taxas de câmbio atualizadas.
*   Interface de linha de comando (console) interativa.
*   Salva os detalhes da última conversão (moedas, valor, taxa, resultado, data da cotação) em um arquivo JSON.
*   Tratamento básico de erros de entrada e comunicação com a API.

## Tecnologias Utilizadas

*   **Linguagem:** Java (JDK 11 ou superior recomendado devido ao `java.net.http.HttpClient`)
*   **Bibliotecas:**
    *   [Gson](https://github.com/google/gson): Para manipulação de dados JSON (parsing da resposta da API e gravação do arquivo).
*   **API Externa:** [ExchangeRate-API (v6)](https://www.exchangerate-api.com/docs/standard-requests)

## Pré-requisitos

Antes de começar, você precisará ter o seguinte instalado:

1.  **Java Development Kit (JDK):** Versão 11 ou mais recente. Você pode verificar sua versão com `java -version`.
2.  **Maven (Opcional, mas recomendado):** Para gerenciamento de dependências (Gson) e compilação. Você pode verificar com `mvn -version`.
3.  **API Key da ExchangeRate-API:**
    *   Vá para [https://www.exchangerate-api.com/](https://www.exchangerate-api.com/)
    *   Registre-se para obter uma API Key gratuita.

## Configuração

1.  **Clone o repositório:**
    ```bash
    git clone <https://github.com/RichardRoqueSilva/ChallengeConversorMoeda.git>
    cd <ChallengeConversorMoeda>
    ```

2.  **Configure a API Key:**
    *   Abra o arquivo `src/main/java/ConversorMoeda.java` (ou o caminho correspondente onde o arquivo está).
    *   Encontre a linha:
        ```java
        private static final String API_KEY = "YOUR-API-KEY";
        ```
    *   **Substitua `"YOUR-API-KEY"` pela sua chave de API real** obtida no site ExchangeRate-API.

3.  **Compile o projeto (usando Maven):**
    *   Se você tiver um arquivo `pom.xml` configurado com a dependência Gson, use o Maven:
        ```bash
        # Limpa compilações anteriores e baixa dependências/compila o projeto
        mvn clean install
        # Ou apenas para compilar
        # mvn compile
        ```
    *   **Se não estiver usando Maven:** Certifique-se de ter o arquivo JAR da biblioteca Gson no seu classpath ao compilar manualmente. Baixe o Gson JAR ([download aqui](https://search.maven.org/artifact/com.google.code.gson/gson/)) e compile:
        ```bash
        # Exemplo (ajuste o caminho/versão do gson.jar)
        javac -cp ./path/to/gson-2.10.1.jar:. *.java
        ```
        (Compile todas as classes .java necessárias).

## Como Usar

1.  **Execute a Aplicação:**
    *   **Se usou Maven para compilar:** Você pode executar a classe principal via Maven:
        ```bash
        mvn exec:java -Dexec.mainClass="Principal"
        ```
    *   **Se compilou manualmente (ou via IDE):** Execute a classe `Principal` garantindo que o Gson JAR esteja no classpath:
        ```bash
        # Exemplo (ajuste o caminho/versão do gson.jar)
        java -cp .:./path/to/gson-2.10.1.jar Principal
        ```
    *   **Via IDE:** Simplesmente execute o método `main` da classe `Principal.java` na sua IDE (IntelliJ, Eclipse, VS Code).

2.  **Interaja com o Menu:**
    *   O programa exibirá um menu com as opções de conversão numeradas e a opção `0` para sair.
    *   Digite o número da conversão desejada e pressione Enter.
    *   Digite o valor que você deseja converter e pressione Enter.

3.  **Veja o Resultado:**
    *   O programa buscará a taxa de câmbio na API, realizará a conversão e exibirá o resultado formatado no console.
    *   Os detalhes da conversão (incluindo moedas, valor original, taxa, valor convertido e data/hora da cotação da API) também serão salvos no arquivo `ultima_conversao.json` na raiz do projeto.

4.  **Sair:** Escolha a opção `0` no menu para finalizar o programa.

## Estrutura do Projeto (Simplificada)

.
├── src
│ └── main
│ └── java
│ ├── Principal.java # Classe principal, interage com o usuário
│ ├── ConversorMoeda.java # Lógica para chamar a API e processar a resposta
│ ├── ResultadoConversao.java # Record para armazenar os dados da conversão
│ └── GeradorArquivoConversoes.java # Lógica para salvar o resultado em JSON
├── pom.xml # (Opcional) Arquivo de configuração do Maven
├── ultima_conversao.json # Arquivo gerado com o resultado da última conversão
└── README.md # Este arquivo

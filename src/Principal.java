import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        try (Scanner leitura = new Scanner(System.in)) {
            ConversorMoeda conversor = new ConversorMoeda();
            GeradorArquivoConversoes gerador = new GeradorArquivoConversoes();

            System.out.println("******************************************");
            System.out.println(" Seja bem-vindo/a ao Conversor de Moeda ");
            System.out.println("******************************************");

            String menu = """

                    Escolha uma opção de conversão:
                    ------------------------------------------
                     1) Dólar Americano (USD) =>> Peso Argentino (ARS)
                     2) Peso Argentino (ARS)  =>> Dólar Americano (USD)
                     3) Dólar Americano (USD) =>> Real Brasileiro (BRL)
                     4) Real Brasileiro (BRL) =>> Dólar Americano (USD)
                     5) Dólar Americano (USD) =>> Peso Colombiano (COP)
                     6) Peso Colombiano (COP) =>> Dólar Americano (USD)
                     7) Dólar Americano (USD) =>> Euro (EUR)
                     8) Euro (EUR)            =>> Dólar Americano (USD)

                     0) Sair
                    ------------------------------------------
                    Digite a opção desejada:""";

            int opcao = -1;

            while (opcao != 0) {
                System.out.println(menu);
                try {
                    opcao = leitura.nextInt();
                    leitura.nextLine();

                    if (opcao == 0) {
                        System.out.println("Saindo do programa...");
                        break;
                    }

                    String moedaBase = "";
                    String moedaAlvo = "";

                    switch (opcao) {
                        case 1: moedaBase = "USD"; moedaAlvo = "ARS"; break;
                        case 2: moedaBase = "ARS"; moedaAlvo = "USD"; break;
                        case 3: moedaBase = "USD"; moedaAlvo = "BRL"; break;
                        case 4: moedaBase = "BRL"; moedaAlvo = "USD"; break;
                        case 5: moedaBase = "USD"; moedaAlvo = "COP"; break;
                        case 6: moedaBase = "COP"; moedaAlvo = "USD"; break;
                        case 7: moedaBase = "USD"; moedaAlvo = "EUR"; break;
                        case 8: moedaBase = "EUR"; moedaAlvo = "USD"; break;
                        default:
                            System.out.println("Opção inválida! Por favor, escolha um número do menu.");
                            continue;
                    }

                    System.out.printf("Digite o valor em %s que deseja converter para %s: ", moedaBase, moedaAlvo);
                    double valor;
                    try {
                        valor = leitura.nextDouble();
                        leitura.nextLine();
                        if (valor <= 0) {
                            System.out.println("Erro: O valor deve ser positivo.");
                            continue;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Erro: Valor inválido. Por favor, digite um número (ex: 150.75).");
                        leitura.nextLine();
                        continue;
                    }



                    System.out.println("Buscando cotação e convertendo...");
                    ConsultaMoeda resultado = conversor.converterMoeda(moedaBase, moedaAlvo, valor);

                    System.out.println(resultado);

                    try {
                        gerador.salvaJson(resultado);
                        System.out.println("Conversão salva em 'ultima_conversao.json'");
                    } catch (IOException e) {
                        System.err.println("Erro ao salvar o arquivo JSON: " + e.getMessage());
                    }


                } catch (InputMismatchException e) {
                    System.out.println("Erro: Opção inválida. Por favor, digite um número inteiro.");
                    leitura.nextLine();
                    opcao = -1;
                } catch (IOException | InterruptedException e) {
                    System.err.println("Erro durante a comunicação com a API: " + e.getMessage());
                } catch (RuntimeException e) {
                    System.err.println("Erro: " + e.getMessage());
                }

                System.out.println("\nPressione Enter para continuar...");
                leitura.nextLine();

            }

        }

        System.out.println("\nPrograma finalizado. Obrigado por usar o Conversor de Moeda!");
    }
}
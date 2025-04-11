import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorMoeda {

    private static final String API_KEY = "627efc71750c2804f4d05f19";
    private static final String API_BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public ConsultaMoeda converterMoeda(String moedaBase, String moedaAlvo, double valor) throws IOException, InterruptedException, RuntimeException {


        if (API_KEY == null || API_KEY.equals("YOUR-API-KEY") || API_KEY.trim().isEmpty()) {
            throw new RuntimeException("Erro: API Key não configurada na classe ConversorMoeda.");
        }
        if (moedaBase == null || moedaAlvo == null || moedaBase.trim().isEmpty() || moedaAlvo.trim().isEmpty()) {
            throw new IllegalArgumentException("Moeda base e alvo não podem ser nulas ou vazias.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para conversão deve ser positivo.");
        }



        String urlStr = String.format("%s%s/pair/%s/%s/%.2f",
                        API_BASE_URL, API_KEY, moedaBase.toUpperCase(), moedaAlvo.toUpperCase(), valor)

                .replace(",", ".");


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlStr))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao conectar com a API: Código " + response.statusCode() + "\n Corpo: "+ response.body());
        }


        String jsonResponse = response.body();
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();


            String resultStatus = jsonObject.get("result").getAsString();
            if (!"success".equalsIgnoreCase(resultStatus)) {

                String errorType = jsonObject.has("error-type") ? jsonObject.get("error-type").getAsString() : "Erro desconhecido na API";
                throw new RuntimeException("Erro retornado pela API: " + errorType);
            }

            String baseCode = jsonObject.get("base_code").getAsString();
            String targetCode = jsonObject.get("target_code").getAsString();
            double conversionRate = jsonObject.get("conversion_rate").getAsDouble();
            double conversionResult = jsonObject.get("conversion_result").getAsDouble();
            String timeLastUpdate = jsonObject.has("time_last_update_utc") ? jsonObject.get("time_last_update_utc").getAsString() : null;

            return new ConsultaMoeda(baseCode, targetCode, conversionRate, valor, conversionResult, timeLastUpdate);


        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Erro ao analisar a resposta JSON da API.", e);
        } catch (NullPointerException | IllegalStateException e) {
            throw new RuntimeException("Erro ao extrair dados da resposta JSON. Formato inesperado? " + jsonResponse, e);
        }
    }
}

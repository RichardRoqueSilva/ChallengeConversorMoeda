
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ConsultaMoeda(
                            String base_code,
                            String target_code,
                            double conversion_rate,
                            double valor_original,
                            double conversion_result,
                            String time_last_update_utc)
{
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada;
        try {
            dataFormatada = LocalDateTime.now().format(formatter);
        } catch (Exception e) {
            dataFormatada = time_last_update_utc != null ? time_last_update_utc : LocalDateTime.now().format(formatter) ;
        }


        return String.format(
                "----- Conversão -----\n" +
                        "%.2f %s equivale a %.2f %s\n" +
                        "Taxa de câmbio: 1 %s = %.5f %s\n" +
                        "Última atualização da cotação: %s (UTC)\n" +
                        "---------------------\n",
                valor_original, base_code,
                conversion_result, target_code,
                base_code, conversion_rate, target_code,
                time_last_update_utc
        );
    }
}

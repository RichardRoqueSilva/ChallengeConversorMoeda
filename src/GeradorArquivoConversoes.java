import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
public class GeradorArquivoConversoes {

    public void salvaJson(ConsultaMoeda consultaMoeda) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter escrita = new FileWriter( "ultima_conversao.json");
        escrita.write(gson.toJson(consultaMoeda));
        escrita.close();

    }
}
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIClient {

    /**
     * Consume la API generadora de reportes
     * Endpoint: https://58o1y6qyic.execute-api.us-east-1.amazonaws.com/default/taskReport
     * Método: GET
     * @return JsonElement con el contenido completo del reporte
     * @throws Exception si ocurre un error al hacer la petición
     */
    public static JsonElement getReporte() throws Exception {
        URL url = new URL("https://58o1y6qyic.execute-api.us-east-1.amazonaws.com/default/taskReport");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        // Parsea la respuesta como JSON
        return JsonParser.parseReader(new InputStreamReader(conn.getInputStream()));
    }

    /**
     * Envía el resultado final ya procesado a la API evaluadora
     * Endpoint: https://t199qr74fg.execute-api.us-east-1.amazonaws.com/default/taskReportVerification
     * Método: POST
     * @param json JSON en formato String con los resultados que se desean enviar
     * @throws Exception si ocurre un error al enviar los datos
     */
    public static void enviarResultado(String json) throws Exception {
        URL url = new URL("https://t199qr74fg.execute-api.us-east-1.amazonaws.com/default/taskReportVerification");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        // Enviar el JSON por el OutputStream
        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
            os.flush();
        }

        int responseCode = conn.getResponseCode();
        System.out.println("✅ Respuesta del servidor: " + responseCode);
    }
}

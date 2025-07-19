// Main.java

import com.google.gson.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // Obtener reporte desde la API
        JsonElement reporteJson = APIClient.getReporte();

        // Convertir JSON a objeto Proceso
        Gson gson = new Gson();
        Proceso root = gson.fromJson(reporteJson, Proceso.class);

        // Analizar el proceso de forma recursiva
        ReporteAnalyzer.Resultado r = new ReporteAnalyzer.Resultado();
        ReporteAnalyzer.analizarProceso(root, r);

        // Calcular eficiencia promedio
        double eficienciaPromedio = r.total > 0 ? (r.sumaEficiencia / r.total) : 0.0;

        // Construir JSON de respuesta
        JsonObject envio = new JsonObject();
        envio.addProperty("nombre", "Isaac Flores");
        envio.addProperty("carnet", "0905-22-6380");
        envio.addProperty("seccion", "A");

        JsonObject resultadoBusqueda = new JsonObject();
        resultadoBusqueda.addProperty("totalProcesos", r.total);
        resultadoBusqueda.addProperty("procesosCompletos", r.completos);
        resultadoBusqueda.addProperty("procesosPendientes", r.pendientes);
        resultadoBusqueda.addProperty("recursosTipoHerramienta", r.herramientas);
        resultadoBusqueda.addProperty("eficienciaPromedio", eficienciaPromedio);

        JsonObject masAntiguo = new JsonObject();
        if (r.masAntiguo != null) {
            masAntiguo.addProperty("id", r.masAntiguo.id);
            masAntiguo.addProperty("fechaInicio", r.masAntiguo.fechaInicio);
        } else {
            masAntiguo.addProperty("id", "N/A");
            masAntiguo.addProperty("fechaInicio", "N/A");
        }
        resultadoBusqueda.add("procesoMasAntiguo", masAntiguo);

        envio.add("resultadoBusqueda", resultadoBusqueda);
        envio.add("payload", reporteJson);  // Reporte original completo

        // Imprimir JSON final bonito
        String jsonFinal = new GsonBuilder().setPrettyPrinting().create().toJson(envio);
        System.out.println(jsonFinal);

        // Enviar POST a la API evaluadora
        APIClient.enviarResultado(jsonFinal);
    }
}

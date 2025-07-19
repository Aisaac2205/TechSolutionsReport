// ReporteAnalyzer.java

public class ReporteAnalyzer {

    public static class Resultado {
        public int total = 0, completos = 0, pendientes = 0, herramientas = 0;
        public double sumaEficiencia = 0.0;
        public Proceso masAntiguo = null;
    }

    public static void analizarProceso(Proceso proceso, Resultado r) {
        r.total++;

        if ("completo".equalsIgnoreCase(proceso.estado)) r.completos++;
        else r.pendientes++;

        r.sumaEficiencia += proceso.eficiencia;

        if (proceso.recursos != null) {
            for (Recurso recurso : proceso.recursos) {
                if ("herramienta".equalsIgnoreCase(recurso.tipo)) r.herramientas++;
            }
        }

        if (r.masAntiguo == null || proceso.fechaInicio.compareTo(r.masAntiguo.fechaInicio) < 0) {
            r.masAntiguo = proceso;
        }

        if (proceso.subprocesos != null) {
            for (Proceso sub : proceso.subprocesos) {
                analizarProceso(sub, r);
            }
        }
    }
}

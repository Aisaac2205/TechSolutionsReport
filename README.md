# TechSolutions - Procesamiento de Reportes Jerárquicos de Procesos

## Descripción

Este proyecto en **Java** consume una API que genera reportes diarios con procesos organizados jerárquicamente. La aplicación procesa recursivamente la estructura del reporte para extraer métricas específicas y envía los resultados a una API evaluadora para su verificación automática.

La empresa **TechSolutions** utiliza estos reportes para monitorear la ejecución y eficiencia de sus procesos internos.

---

## Funcionalidades

- **Consumo de API generadora de reportes**  
  Obtiene un JSON con procesos y subprocesos anidados.

- **Procesamiento recursivo de la estructura de procesos**  
  Calcula las siguientes métricas:  
  - Cantidad total de procesos  
  - Procesos completados vs pendientes  
  - Recursos de tipo "herramienta" utilizados  
  - Eficiencia promedio  
  - Proceso más antiguo

- **Envío de resultados a API evaluadora**  
  Construye y envía un payload JSON con los datos calculados y la respuesta original para validación automática.

---

## APIs Utilizadas

| API                         | Endpoint                                                                                           | Método | Descripción                           |
|-----------------------------|--------------------------------------------------------------------------------------------------|--------|-------------------------------------|
| API Generadora de Reportes   | `https://58o1y6qyic.execute-api.us-east-1.amazonaws.com/default/taskReport`                      | GET    | Obtiene reporte jerárquico en JSON  |
| API Evaluadora de Resultados | `https://t199qr74fg.execute-api.us-east-1.amazonaws.com/default/taskReportVerification`          | POST   | Envía resultados para evaluación     |

---

## Especificaciones del Payload para Evaluación

```json
{
  "nombre": "Su Nombre",
  "carnet": "Su Carnet",
  "seccion": "Su Sección",
  "resultadoBusqueda": {
    "totalProcesos": 0,
    "procesosCompletos": 0,
    "procesosPendientes": 0,
    "recursosTipoHerramienta": 0,
    "eficienciaPromedio": 0.0,
    "procesoMasAntiguo": {
      "id": 0,
      "nombre": "",
      "fechaInicio": ""
    },
    "payload": null
  }
}

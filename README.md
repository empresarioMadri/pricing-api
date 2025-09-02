# Pricing API – Spring Boot (Java) · Hexagonal + DDD

> empresarioMadri/pricing-api

![CI](https://img.shields.io/github/actions/workflow/status/tu-usuario/pricing-api/ci.yml?branch=main)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen)
![License](https://img.shields.io/badge/license-MIT-informational)

## 📌 Descripción
**Pricing API** expone un endpoint **GET** que, dada una fecha (`applicationDate`), un `productId` y un `brandId`, devuelve **exactamente un precio** aplicable (tarifa y rango temporal). Implementada con **Spring Boot (Java)**, **JPA/H2**, arquitectura **Hexagonal + DDD**, y con **tests de integración**, **CI** y **cobertura**.

## 🧱 Stack
- Java 21 · Spring Boot 3.3 · Spring Web · Validation · Spring Data JPA
- H2 in‑memory (con `schema.sql` + `data.sql`)
- Maven · JUnit 5 · MockMvc · JaCoCo · Lombok

## 🏗️ Arquitectura (Hexagonal + DDD)
- **Dominio**: modelos (`Price`, `Currency`) y puerto `PriceRepositoryPort` (sin dependencias de framework).
- **Aplicación**: caso de uso `GetApplicablePriceUseCase` (orquesta puertos).
- **Infraestructura**: adaptador JPA/H2 (`PriceJpaAdapter`, `PriceJpaRepository`, `PriceEntity`) y capa Web (`PriceController`, DTOs, `@ControllerAdvice`).

```text
Domain ──(port)──▶ Application ──(adapter)──▶ Infrastructure: JPA
                         │
                         └──▶ Infrastructure: Web (REST)
```

## 🔌 API
`GET /api/v1/prices`

**Query params**
- `applicationDate` (ISO-8601, ej. `2020-06-14T10:00:00`)
- `productId` (long ≥ 1)
- `brandId` (int ≥ 1)

**200 OK**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate":   "2020-06-14T18:30:00",
  "finalPrice": 25.45,
  "currency": "EUR"
}
```
**Errores**: `400` parámetros inválidos · `404` sin precio aplicable · `422` semántica inválida · `500` error interno.

### cURL
```bash
curl "http://localhost:8080/api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

## 🗃️ Datos y persistencia
- **H2** en memoria. Consola: `http://localhost:8080/h2-console`  
  JDBC URL: `jdbc:h2:mem:pricingdb` · usuario: `sa` · sin password
- Índice compuesto: `(PRODUCT_ID, BRAND_ID, START_DATE, END_DATE, PRIORITY)`
- Selección eficiente con método derivado: `findTop…OrderByPriorityDesc`

## ▶️ Ejecución
```bash
mvn -B spring-boot:run
```

## 🧪 Tests & cobertura
```bash
mvn -B clean verify
# JaCoCo HTML: target/site/jacoco/index.html
```
Incluye **tests de integración** con MockMvc para los 5 escenarios solicitados + casos 400/404.

## 🔁 CI/CD
- GitHub Actions (`.github/workflows/ci.yml`): compila, ejecuta tests y genera cobertura.

## 🧼 Calidad
- Clean Code + SOLID (SRP, DIP a través de puertos, OCP en dominio).
- `@ControllerAdvice` mapeando excepciones a HTTP (400/404/422/500).
- Naming consistente y capas con responsabilidad única.

## 🧩 Convenciones y flujo de trabajo
- **Conventional Commits** (`feat:`, `fix:`, `chore:`, `docs:`, `test:`).
- Ramas: `main` (estable), `feat/*`, `fix/*`, `chore/*`.
- Protecciones de rama recomendadas: requerir PR y CI verde en `main`.

## 🏷️ Versionado y releases
```bash
git tag -a v0.1.0 -m "Initial release"
git push origin v0.1.0
```

## 🔧 Configuración
- Zona horaria de serialización: `Europe/Madrid` (ver `application.yml`).
- Endpoint único idempotente (GET) con validación de parámetros.

## 💡 Extensión/Mantenibilidad
- Añadir nuevas estrategias/tarifas → nuevas filas en PRICES o nuevo adaptador sin tocar el dominio.
- Cache (Caffeine) opcional para alta repetición de consultas.

## 🧰 IDE y Lombok
- Habilitar **annotation processing** en tu IDE:  
  IntelliJ IDEA → *Settings → Build, Execution, Deployment → Compiler → Annotation Processors* → ✅ Enable

## 📜 Licencia
MIT. Ver `LICENSE`.

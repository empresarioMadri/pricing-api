# Pricing API – Spring Boot (Java) · Hexagonal + DDD · Maven + JPA/H2 + Lombok

> Reemplaza `tu-usuario/pricing-api` por tu usuario/organización real.

![CI](https://img.shields.io/github/actions/workflow/status/tu-usuario/pricing-api/ci.yml?branch=main)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen)
![License](https://img.shields.io/badge/license-MIT-informational)

## 📌 Descripción
**Pricing API** expone un endpoint **GET** que, dada una fecha (`applicationDate`), un `productId` y un `brandId`, devuelve **exactamente un precio** aplicable (tarifa y rango temporal). Implementada con **Spring Boot (Java)**, **JPA/H2**, arquitectura **Hexagonal + DDD**, **Lombok**, con **tests de integración**, **CI** y **cobertura**.

## 🧱 Stack
- Java 21 · Spring Boot 3.5.5 · Spring Web · Validation · Spring Data JPA
- H2 in-memory (con `schema.sql` + `data.sql`)
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
- Índice compuesto recomendado: `(PRODUCT_ID, BRAND_ID, START_DATE, END_DATE, PRIORITY)`
- Selección eficiente con método derivado Spring Data: `findTop…OrderByPriorityDesc`

## ⚙️ Configuración
`src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:pricingdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.sql.init.mode=always
spring.sql.init.encoding=UTF-8
spring.jpa.hibernate.ddl-auto=none
spring.jpa.defer-datasource-initialization=true

server.error.include-message=always
spring.jackson.time-zone=Europe/Madrid
```

Coloca `schema.sql` y `data.sql` en `src/main/resources/` para que se ejecuten al arranque.

## ▶️ Ejecución
```bash
mvn -B spring-boot:run
```

## 🧪 Tests & cobertura
Tests de integración con MockMvc validando los 5 casos del enunciado (ZARA).

```bash
mvn -B clean verify
# Reporte HTML JaCoCo: target/site/jacoco/index.html
```

## 🔁 CI/CD
`.github/workflows/ci.yml` (ejemplo):
```yaml
name: CI
on:
  push: { branches: [ main ] }
  pull_request: { branches: [ main ] }

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 21
      - name: Build & Test
        run: mvn -B clean verify
      - name: Upload JaCoCo report
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: jacoco-report
          path: target/site/jacoco
```

## 🧩 Convenciones y flujo de trabajo
- **Conventional Commits** (`feat:`, `fix:`, `chore:`, `docs:`, `test:`).
- Ramas: `main` (estable), `feat/*`, `fix/*`, `chore/*`.
- Reglas de protección: requerir PR + CI verde en `main`.

## 🧼 Calidad & errores
- Clean Code + SOLID (SRP, DIP con puertos, OCP en dominio).
- `@ControllerAdvice` mapeando errores a HTTP (400/404/422/500).
- Naming consistente y responsabilidades de capa claras.

## 🧰 IDE y Lombok
- Activa **annotation processing** en tu IDE.  
  IntelliJ IDEA → *Settings → Build, Execution, Deployment → Compiler → Annotation Processors* → ✅ Enable

## 🧪 Troubleshooting rápido
- **Lombok no genera código**: verifica `annotation processing` y que Lombok está como *annotation processor* en `maven-compiler-plugin`.
- **MockMvc = null** en tests: añade `@SpringBootTest(webEnvironment=MOCK)` + `@AutoConfigureMockMvc` y usa `@Autowired MockMvc`.

## 📜 Licencia
MIT. Ver `LICENSE`.

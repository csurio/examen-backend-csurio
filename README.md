# Bank Transactions API

Este proyecto implementa una solución de prueba técnica para el manejo de cuentas y transacciones bancarias utilizando **Java 17**, **Spring Boot 3**, **JPA/Hibernate**, principios **SOLID**, documentación con **OpenAPI**, manejo profesional de errores, logs estructurados y pruebas unitarias.

---

## 🌐 Tecnologías y herramientas utilizadas

- Java 17
- Spring Boot 3
- Spring Web / Spring Data JPA
- Base de datos: H2 / PostgreSQL / MySQL (configurable)
- Lombok
- OpenAPI / Swagger (springdoc-openapi)
- SLF4J + Logback para logging empresarial
- JUnit + Mockito para pruebas unitarias

---

## 📖 Casos de uso implementados

### 1. Acreditar o debitar a una cuenta bancaria

- Endpoint: `POST /api/v1/transactions/process`
- Entrada:
```json
{
  "clientId": 1,
  "account": "ACC123",
  "amount": 100.00
}
```
- Reglas:
  - Si el cliente no existe → error.
  - Si la cuenta no existe pero el cliente sí → se crea automáticamente.
  - El campo `amount` puede ser positivo (acreditar) o negativo (debitar).
  - La transacción se registra siempre.
  - Se simula el envío de la transacción a una cola (RabbitMQ/Kafka).
- Respuesta:
```json
{
  "message": "Transaction received and is being processed",
  "code": "00"
}
```

### 2. Consultar el historial de transacciones

- Endpoint: `GET /api/v1/transactions/history`
- Parámetros:
  - `clientId` (requerido)
  - `account` (opcional)
- Muestra:
  - Datos del cliente.
  - Todas las cuentas o la cuenta filtrada.
  - Saldo actual.
  - Transacciones asociadas.

---

## 🖊️ Estructura del proyecto

```
com.assessment.bank
├── config                  # Configuración inicial y carga de datos
├── controller              # Controladores REST
├── dto                    # Clases DTO para entrada/salida
├── exception              # Manejo de errores y excepciones personalizadas
├── persistence
│   ├── entity              # Entidades JPA
│   └── repository          # Interfaces de repositorio
├── service
│   ├── impl                # Implementaciones de servicios
│   └── TransactionService  # Interface de servicio principal
└── BankApplication.java    # Clase principal
```

---

## 📃 Datos de prueba

Se cargan automáticamente al iniciar la aplicación en ambiente de desarrollo mediante `DataInitializer`. Incluye:
- 2 clientes:
  - Cliente 1: 1 cuenta (ACC123)
  - Cliente 2: 2 cuentas (ACC456, ACC789)
- Varias transacciones por cuenta para pruebas reales.

---

## 📅 Documentación OpenAPI

Disponible en:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- JSON: `http://localhost:8080/api-docs`

Documenta los endpoints, modelos y respuestas.

---

## 📄 Pruebas unitarias y de integración

### 1. Test de conexión a base de datos MySQL

La clase `DatabaseConnectionTest` valida que la conexión con la base de datos configurada (por defecto MySQL) esté activa y funcional:

```java
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class DatabaseConnectionTest {

    private final DataSource dataSource;

    @Test
    void shouldConnectToDatabase() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            assertFalse(connection.isClosed());
            System.out.println("Connected successfully to MySQL: " + connection.getMetaData().getURL());
        }
    }
}
```

Este test usa la misma configuración del archivo `application.yml` y se puede ejecutar con:

```bash
mvn test -Dtest=DatabaseConnectionTest
```

### 2. Test de historial de transacciones

Valida que el servicio `TransactionService.getHistory(...)` funcione correctamente y devuelva la información del cliente, sus cuentas y transacciones.

---

## 📊 Colección Postman

El proyecto incluye una colección Postman para probar los endpoints de la API de forma interactiva. Puedes acceder a ella en el siguiente path dentro del repositorio:

**[examen-backend-carlosmonterrosa/Documentacion](examen-backend-carlosmonterrosa/Documentacion)**

La colección contiene ejemplos para:
- Acreditar/debitar a una cuenta
- Consultar el historial de transacciones

---

## 🚀 Ejecución del proyecto

```bash
mvn spring-boot:run
```

o empaquetado:

```bash
mvn clean package
java -jar target/bank-0.0.1-SNAPSHOT.jar
```

---

## 💼 Autor

Desarrollado por **Carlos M. Surio** como parte de una prueba técnica para evaluación de habilidades con Java y microservicios.

---

🚀 Si deseas que se prepare el despliegue con Docker, Pruebas de Integración o se conecte a RabbitMQ real, no dudes en solicitarlo.


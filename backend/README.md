# Kay Forex Backend

This is the Java Spring Boot backend for the AI Forex Trading Dashboard.

## Features

- REST API for:
  - Pattern upload
  - Sentiment analysis
  - Live market candle data (for TradingView chart)
- User authentication (Spring Security)
- Ready for deployment (local or cloud)

## Requirements

- Java 11+ (OpenJDK recommended)
- Maven 3.6+

## Setup

1. **Install dependencies and build:**
    ```sh
    cd backend
    mvn clean package
    ```

2. **Run the application:**
    ```sh
    mvn spring-boot:run
    ```
    or (after build)
    ```sh
    java -jar target/kay-forex-backend-1.0.0.jar
    ```

3. **Configuration:**

    Edit `src/main/resources/application.properties` for settings like port, database, or security.

4. **API Endpoints:**

    - `/api/patterns/upload` – POST pattern uploads (multipart)
    - `/api/patterns/analyze-sentiment` – POST sentiment analysis
    - `/api/market/candles` – GET live candle data (JSON)
    - (Add further endpoints as needed)

## Deployment

- Deploy as a JAR (see above), or to any cloud hosting supporting Java (Heroku, AWS, Render, etc).

## Contributing

Please fork and submit PRs! Issues and feature requests welcome.

---

**Frontend is in the `/frontend` folder.**

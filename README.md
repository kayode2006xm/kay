# AI Forex Trading Dashboard

A modern web application for real-time forex analytics, chart visualization, pattern uploads, and AI-driven sentiment analysis.

---

## Features

- **Live Forex Chart** with TradingView Lightweight Charts  
- **Pair Search & Realtime Prices**
- **Pattern Upload** (image/video, with name & description)
- **Sentiment Analysis** (AI-powered, for news/comments)
- **Animated UI** with Tailwind CSS & custom effects
- **Modular Frontend** (Vanilla JS)
- **Spring Boot Backend** (Java)
- **API-first Architecture**

---

## Folder Structure

```
frontend/
  index.html
  /js/
    market.js
    utils.js
    pattern-upload.js
    chart.js
  /styles/
    main.css

backend/
  /src/main/java/com/forexai/controller/
    MarketController.java
    PatternController.java
  /src/main/java/com/forexai/service/
    PatternService.java
  /src/main/resources/
    application.properties
```

---

## Quickstart

### Backend

1. **Setup**

   - Java 17+ & Maven required

2. **Config**

   - Edit `backend/src/main/resources/application.properties`  
   - Set API keys, file upload path, etc.

3. **Run**

   ```sh
   cd backend
   ./mvnw spring-boot:run
   ```

   Backend runs at: `http://localhost:8080/`

---

### Frontend

1. **Serve Static Files**

   - Place all frontend files in a web server root (or use `python3 -m http.server` for testing)
   - Or, configure Spring Boot to serve `/frontend` as static

2. **Usage**

   - Open `http://localhost:8080/` (if served by backend)
   - Or, open `frontend/index.html` directly and set API endpoints to match backend location

---

## API Endpoints

| Endpoint                       | Method | Description                |
| ------------------------------ | ------ | -------------------------- |
| `/api/market/candles`          | GET    | Get candle data            |
| `/api/market/price`            | GET    | Get latest price           |
| `/api/market/pairs`            | GET    | List available pairs       |
| `/api/patterns/upload`         | POST   | Upload pattern file        |
| `/api/patterns/analyze-sentiment` | POST | AI-powered sentiment check |

---

## Environment & Security

- Edit secrets in `application.properties` (never commit real API keys)
- For local dev, set CORS if frontend/backend run on different ports
- For production, use HTTPS and secure file upload directory

---

## Customization

- **Patterns:** Extend `PatternService.java` for custom logic or DB storage
- **Sentiment:** Plug in your own AI model or external API
- **Frontend:** Add new JS modules for more chart tools/analytics

---

## Screenshots

_Add screenshots to show off your dashboard UI here!_

---

## Credits

- [TradingView Lightweight Charts](https://tradingview.github.io/lightweight-charts/)
- [Tailwind CSS](https://tailwindcss.com/)
- FontAwesome

---

## License

MIT

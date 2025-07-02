import json
from textblob import TextBlob

def analyze_sentiment(text):
    """
    Analyze sentiment using TextBlob: returns polarity and subjectivity.
    """
    blob = TextBlob(text)
    polarity = blob.sentiment.polarity
    subjectivity = blob.sentiment.subjectivity
    if polarity > 0.1:
        label = "Bullish"
    elif polarity < -0.1:
        label = "Bearish"
    else:
        label = "Neutral"
    return {"label": label, "polarity": polarity, "subjectivity": subjectivity}

def detect_patterns(prices):
    """
    Dummy pattern detection: returns a list of found patterns in price data.
    Real implementation would use ML or TA-lib.
    """
    # This is a stub: replace with your own pattern logic
    patterns = []
    if len(prices) >= 2 and prices[-1] > prices[-2]:
        patterns.append({"pattern": "Uptrend", "confidence": 0.7})
    if len(prices) >= 2 and prices[-1] < prices[-2]:
        patterns.append({"pattern": "Downtrend", "confidence": 0.7})
    return patterns

def save_pattern(pattern, filename="patterns.json"):
    """
    Save a detected pattern to a JSON file (append mode).
    """
    with open(filename, "a") as f:
        f.write(json.dumps(pattern) + "\n")

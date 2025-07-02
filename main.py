from pipeline import analyze_sentiment, detect_patterns, save_pattern

def main():
    print("Forex AI Pipeline Demo")
    # Sentiment Analysis Usage
    text = input("Enter forex news text: ")
    result = analyze_sentiment(text)
    print(f"Sentiment: {result['label']} (polarity: {result['polarity']:.2f})")

    # Pattern Detection Usage
    price_str = input("Enter recent prices separated by commas: ")
    prices = [float(p.strip()) for p in price_str.split(",") if p.strip()]
    patterns = detect_patterns(prices)
    if patterns:
        for pattern in patterns:
            print(f"Pattern detected: {pattern['pattern']} (confidence: {pattern['confidence']})")
            save_pattern(pattern)
    else:
        print("No patterns detected.")

if __name__ == "__main__":
    main()

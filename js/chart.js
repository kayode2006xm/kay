// TradingView Lightweight Chart integration with backend candles

// Assumes: <div id="container"></div> exists in DOM
// Requires: lightweight-charts loaded via CDN

/**
 * Initialize TradingView chart and load candles for a selected symbol/interval
 * @param {string} symbol
 * @param {string} interval
 */
async function initChart(symbol = 'EURUSD', interval = '1m') {
  const container = document.getElementById('container');
  if (!container) return;
  container.innerHTML = ""; // Clear before re-init

  const chart = LightweightCharts.createChart(container, {
    layout: { textColor: 'black', background: { type: 'solid', color: 'white' } },
    width: container.offsetWidth,
    height: 500,
    rightPriceScale: { visible: true },
    timeScale: { timeVisible: true, secondsVisible: false }
  });

  const candleSeries = chart.addCandlestickSeries({
    upColor: '#26a69a',
    downColor: '#ef5350',
    borderVisible: false,
    wickUpColor: '#26a69a',
    wickDownColor: '#ef5350'
  });

  try {
    const candles = await getCandles(symbol, interval);
    // Normalize to TradingView format
    const data = candles.map(c => ({
      time: c.t || c.time,
      open: c.o || c.open,
      high: c.h || c.high,
      low: c.l || c.low,
      close: c.c || c.close
    }));
    candleSeries.setData(data);
    chart.timeScale().fitContent();
  } catch (err) {
    showAlert('upload-status', 'Failed to load chart data', 'error');
  }

  // Optional: return chart object for further use
  return chart;
}

// Example usage: Call this after DOMContentLoaded or user selects a new symbol
// initChart('EURUSD', '1m');

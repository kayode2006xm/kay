// Handles market data fetching and UI updates

const API_BASE = window.location.origin;

/**
 * Fetch available trading pairs from backend and populate a dropdown
 * @param {string} dropdownId - The ID of the select element
 */
async function loadPairs(dropdownId) {
  try {
    const resp = await fetch(`${API_BASE}/api/market/pairs`);
    if (!resp.ok) throw new Error("Failed to fetch pairs");
    const pairs = await resp.json();
    const select = document.getElementById(dropdownId);
    select.innerHTML = "";
    pairs.forEach(pair => {
      const option = document.createElement("option");
      option.value = pair.symbol || pair;
      option.textContent = pair.label || pair.symbol || pair;
      select.appendChild(option);
    });
  } catch (err) {
    console.error(err);
  }
}

/**
 * Fetch latest price for a symbol and update an element
 * @param {string} symbol - e.g., 'EURUSD'
 * @param {string} elementId - The ID of the DOM element to update
 */
async function updatePrice(symbol, elementId) {
  try {
    const resp = await fetch(`${API_BASE}/api/market/price?symbol=${encodeURIComponent(symbol)}`);
    if (!resp.ok) throw new Error("Failed to fetch price");
    const data = await resp.json();
    const el = document.getElementById(elementId);
    el.textContent = data.price || JSON.stringify(data);
  } catch (err) {
    console.error(err);
  }
}

/**
 * Fetch candle data for a symbol and interval
 * @param {string} symbol
 * @param {string} interval (e.g. '1m', '5m')
 * @returns {Promise<Array>} Array of candle objects
 */
async function getCandles(symbol = 'EURUSD', interval = '1m') {
  const resp = await fetch(`${API_BASE}/api/market/candles?symbol=${encodeURIComponent(symbol)}&interval=${encodeURIComponent(interval)}`);
  if (!resp.ok) throw new Error('Failed to fetch candles');
  return await resp.json();
}

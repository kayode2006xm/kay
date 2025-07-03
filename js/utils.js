// General utility functions

/**
 * Format a date (UNIX timestamp or ISO string) to 'YYYY-MM-DD HH:mm'
 * @param {number|string} date
 */
function formatDateTime(date) {
  const d = typeof date === 'number' ? new Date(date * 1000) : new Date(date);
  return d.getFullYear() + '-' +
    String(d.getMonth() + 1).padStart(2, '0') + '-' +
    String(d.getDate()).padStart(2, '0') + ' ' +
    String(d.getHours()).padStart(2, '0') + ':' +
    String(d.getMinutes()).padStart(2, '0');
}

/**
 * Show a temporary alert message in a given element
 * @param {string} elementId
 * @param {string} message
 * @param {string} type - 'success', 'error', 'info'
 */
function showAlert(elementId, message, type = 'info') {
  const el = document.getElementById(elementId);
  if (!el) return;
  const color = type === 'success' ? 'text-green-400' : type === 'error' ? 'text-red-400' : 'text-blue-300';
  el.innerHTML = `<span class="${color}">${message}</span>`;
  setTimeout(() => { el.innerHTML = ""; }, 4000);
}

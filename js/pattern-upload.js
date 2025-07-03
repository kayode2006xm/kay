// Handles pattern upload and sentiment analysis UI logic

const API_BASE = window.location.origin;

// Pattern Upload Handler
document.getElementById('pattern-upload-form').addEventListener('submit', async function(e) {
  e.preventDefault();
  const formData = new FormData();
  formData.append('name', document.getElementById('pattern-name').value);
  formData.append('description', document.getElementById('pattern-description').value);
  const fileInput = document.getElementById('pattern-file');
  if (fileInput.files.length > 0) {
    formData.append('file', fileInput.files[0]);
  }
  try {
    const response = await fetch(`${API_BASE}/api/patterns/upload`, {
      method: 'POST',
      body: formData
    });
    if (!response.ok) throw new Error('Upload failed');
    const result = await response.json();
    showAlert('upload-status', `Pattern uploaded: ${result.name}`, 'success');
  } catch (err) {
    showAlert('upload-status', err.message, 'error');
  }
});

// Sentiment Analysis Handler
document.getElementById('analyze-sentiment').onclick = async function() {
  const text = document.getElementById('sentiment-text').value;
  try {
    const response = await fetch(`${API_BASE}/api/patterns/analyze-sentiment`, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({ text })
    });
    if (!response.ok) throw new Error('Request failed');
    const result = await response.json();
    showAlert('sentiment-result', `Sentiment: ${result.label} (polarity: ${result.polarity})`, 'info');
  } catch (err) {
    showAlert('sentiment-result', err.message, 'error');
  }
};

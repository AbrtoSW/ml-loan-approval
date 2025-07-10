import React, { useState } from 'react';
import './App.css';

function App() {
  const [formData, setFormData] = useState({
    name: '',
    age: '',
    annualIncome: '',
    requestedLoanAmount: '',
    creditScore: ''
  });
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setResult(null);

    try {
      const response = await fetch('http://localhost:8080/loan/apply', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: formData.name,
          age: parseInt(formData.age),
          annualIncome: parseFloat(formData.annualIncome),
          requestedLoanAmount: parseFloat(formData.requestedLoanAmount),
          creditScore: parseInt(formData.creditScore),
        }),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();
      setResult(data);
    } catch (err) {
      setError(err.message);
    }
  };

  return (
      <div className="App">
        <h1>Loan Approval Application</h1>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="name">Name:</label>
            <input
                type="text"
                id="name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
            />
          </div>
          <div className="form-group">
            <label htmlFor="age">Age:</label>
            <input
                type="number"
                id="age"
                name="age"
                value={formData.age}
                onChange={handleChange}
                required
                min="18"
            />
          </div>
          <div className="form-group">
            <label htmlFor="annualIncome">Annual Income ($):</label>
            <input
                type="number"
                id="annualIncome"
                name="annualIncome"
                value={formData.annualIncome}
                onChange={handleChange}
                step="0.01"
                required
                min="0"
            />
          </div>
          <div className="form-group">
            <label htmlFor="requestedLoanAmount">Requested Loan Amount ($):</label>
            <input
                type="number"
                id="requestedLoanAmount"
                name="requestedLoanAmount"
                value={formData.requestedLoanAmount}
                onChange={handleChange}
                step="0.01"
                required
                min="0"
            />
          </div>
          <div className="form-group">
            <label htmlFor="creditScore">Credit Score:</label>
            <input
                type="number"
                id="creditScore"
                name="creditScore"
                value={formData.creditScore}
                onChange={handleChange}
                required
                min="300"
                max="850"
            />
          </div>
          <button type="submit">Submit Application</button>
        </form>

        {result && (
            <div className="result">
              <h2>Result</h2>
              <p><strong>Decision:</strong> {result.decision}</p>
              <p><strong>Reason:</strong> {result.reason}</p>
            </div>
        )}
        {error && (
            <div className="error">
              <h2>Error</h2>
              <p>{error}</p>
            </div>
        )}
      </div>
  );
}

export default App;
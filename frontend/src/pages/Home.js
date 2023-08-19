import React, { useState, useEffect } from 'react';

function Home() {
  const [data, setData] = useState([]);
  const apiUrl = '/dashboard';

  useEffect(() => {
    fetch(apiUrl)
      .then((response) => response.json())
      .then((data) => setData(data))
      .catch((error) => console.error('Error fetching data:', error));
  }, []);

  return (
    <div>
      <h2>Invested Info:</h2>
      <table border="1">
        <thead>
          <tr>
            <th>Email</th>
            <th>Invested Fin. Instrument</th>
            <th>Invested Amount</th>
            <th>Updated Amount</th>
            <th>Updated Date</th>
            <th>Updated By</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item) => (
            <tr key={item.id}>
              <td>{item.email}</td>
              <td>{item.instrument}</td>
              <td>{item.investedAmount}</td>
              <td>{item.updatedAmount}</td>
              <td>{item.updatedDate}</td>
              <td>{item.updatedBy}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Home;

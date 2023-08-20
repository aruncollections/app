import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Home.css'; // Import the CSS file for the component

function Home() {
  const [data, setData] = useState([]);
  const [uploadSuccess, setUploadSuccess] = useState(false);
  const dashboardUrl = '/dashboard';
  const uploadUrl = '/dashboard/upload';

  useEffect(() => {
    fetch(dashboardUrl)
      .then((response) => response.json())
      .then((data) => setData(data))
      .catch((error) => console.error('Error fetching data:', error));
  }, []);

  const handleFileUpload = async (event) => {
    event.preventDefault();
    const formData = new FormData(event.target); // Create FormData from the form

    try {
      const response = await axios.post(uploadUrl, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      console.log('File uploaded successfully:', response.data);
      setUploadSuccess(true);
    } catch (error) {
      console.error('Error uploading file:', error);
      // Handle error and update your UI
    }
  };

  return (
    <div className="dark-theme">
      <h2 className="dark-text">Invested Info:</h2>
      <form onSubmit={handleFileUpload} action={uploadUrl} method="post" encType="multipart/form-data">
        <input type="file" name="file" />
        <button type="submit">Upload File</button>
      </form>

        <br/>
        {uploadSuccess && (
            <p className="success-message">File uploaded successfully!</p>
        )}
        <br/>

      <table className="dark-table">
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
              <td>{item.emailId}</td>
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

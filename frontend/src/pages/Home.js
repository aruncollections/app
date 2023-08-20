import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Home.css';

function Home() {
  const [data, setData] = useState([]);
  const [uploadSuccess, setUploadSuccess] = useState(false);
  const [dataFetching, setDataFetching] = useState(true);
  const [uploadError, setUploadError] = useState(null);
  const dashboardUrl = '/dashboard';
  const uploadUrl = '/dashboard/upload';

  useEffect(() => {
    fetch(dashboardUrl)
      .then((response) => response.json())
      .then((data) => {
        setData(data);
        setDataFetching(false);
      })
      .catch((error) => console.error('Error fetching data:', error));
  }, [dataFetching]);

  const handleFileUpload = async (event) => {
    event.preventDefault();
    setUploadSuccess(false);

    const form = event.target;
    const formData = new FormData(form);
    const fileInput = form.querySelector('input[type="file"]');
    const file = fileInput.files[0];

    if (file) {
      const fileExtension = file.name.split('.').pop().toLowerCase();

      if (fileExtension !== 'csv') {
        setUploadError('Only CSV files are allowed.');
        form.reset();
        return;
      }

      try {
        const response = await axios.post(uploadUrl, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
        console.log('File uploaded successfully:', response.data);
        setUploadSuccess(true);
        setDataFetching(true);
        setUploadError(null);
      } catch (error) {
        console.error('Error uploading file:', error);
        setUploadError('Error uploading file. Please try again.');
      } finally {
        form.reset();
      }
    }
  };

  return (
    <div className="dark-theme">
      <h2 className="dark-text">Invested Info:</h2>
      <form onSubmit={handleFileUpload} action={uploadUrl} method="post" encType="multipart/form-data">
        <input type="file" name="file" />
        <button type="submit">Upload File</button>
      </form>

      <br />
      {uploadSuccess && (
        <p className="success-message">File uploaded successfully!</p>
      )}
      {uploadError && (
        <p className="error-message">{uploadError}</p>
      )}
      <br />

      <table className="dark-table">
        <thead>
          <tr>
            <th>Email</th>
            <th>Name</th>
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
              <td>{item.firstName} {item.lastName}</td>
              <td>{item.investedAmount}</td>
              <td>{item.updatedAmount}</td>
              <td>{item.uploadedDate}</td>
              <td>{item.updatedBy}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Home;

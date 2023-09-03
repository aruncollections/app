import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Common.css';

function Home(props) {
  const [data, setData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [uploadSuccess, setUploadSuccess] = useState(false);
  const [dataFetching, setDataFetching] = useState(true);
  const [uploadError, setUploadError] = useState(null);
  const itemsPerPage = 10;
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    fetchData();
  }, [currentPage]); // Trigger a fetch when the currentPage changes

  useEffect(() => {
    if (data.length > 0) {
      setTotalPages(Math.ceil(data.length / itemsPerPage));
    }
  }, [data]); // Calculate the total number of pages based on the data length

  const fetchData = () => {
    axios.get('/dashboard')
      .then((response) => {
        setData(response.data);
        setDataFetching(false);
      })
      .catch((error) => console.error('Error fetching data:', error));
  };

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

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
        await axios.post('/dashboard/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
        console.log('File uploaded successfully');
        setUploadSuccess(true);
        setUploadError(null);
        form.reset();

        // Trigger a data refresh and reset to the first page
        fetchData();
        setCurrentPage(1);
      } catch (error) {
        console.error('Error in uploading file:', error);
        setUploadError('Error in uploading file. Please try again.');
      }
    }
  };

  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentData = data.slice(startIndex, endIndex);

  return (
    <div className="dark-theme">
      {props.isAdmin && (
        <form onSubmit={handleFileUpload} encType="multipart/form-data">
          <div className="button-container">
            <input className="button" type="file" name="file" />
            <button className="button" type="submit">Upload File</button>
          </div>
        </form>
      )}
      <br/>

      {uploadSuccess && (
        <p className="success-message">File uploaded successfully!</p>
      )}
      {uploadError && (
        <p className="error-message">{uploadError}</p>
      )}

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
          {currentData.length > 0 ? currentData.map((item) => (
            <tr key={item.id}>
              <td>{item.emailId}</td>
              <td>{item.firstName} {item.lastName}</td>
              <td>{item.investedAmount}</td>
              <td>{item.updatedAmount}</td>
              <td>{item.uploadedDate}</td>
              <td>{item.updatedBy}</td>
            </tr>
          )) : (
            <tr>
              <td colSpan="6">No data available</td>
            </tr>
          )}
        </tbody>
      </table>

      <div className="pagination">
        {currentPage > 1 && (
          <button onClick={() => handlePageChange(currentPage - 1)}>Previous</button>
        )}
        {currentPage < totalPages && (
          <button onClick={() => handlePageChange(currentPage + 1)}>Next</button>
        )}
      </div>
    </div>
  );
}

export default Home;

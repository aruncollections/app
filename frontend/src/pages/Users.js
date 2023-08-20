import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Common.css';

function Users() {
  const [data, setData] = useState([]);
  const [selectedRow, setSelectedRow] = useState(null);
  const [dataFetching, setDataFetching] = useState(true);
  const [updateError, setUpdateError] = useState(null);
  const [activateSuccess, setActivateSuccess] = useState(false);
  const usersUrl = '/users';
  const activateUrl = '/activate';
  const inactivateUrl = '/inactivate';

  useEffect(() => {
    fetch(usersUrl)
      .then((response) => response.json())
      .then((data) => {
        setData(data);
        setDataFetching(false);
      })
      .catch((error) => console.error('Error fetching data:', error));
  }, [dataFetching]);

  const handleRowClick = (selectedItem) => {
    setSelectedRow(selectedItem);
  };

  const handleActivate = async () => {
    if (selectedRow !== null) {
      try {
        // Assuming you have an API endpoint to activate the user
        const response = await axios.put(`${activateUrl}/${selectedRow.emailId}`);
        console.log('User activated:', response.data);
        setActivateSuccess(true);
        setDataFetching(true);
        setUpdateError(null);
      } catch (error) {
        console.error('Error activating user:', error);
        setUpdateError('Error activating user. Please try again.');
      }
    }
  };

  return (
    <div className="dark-theme">
      <h2 className="dark-text">Users in system</h2>

      <br />
      {updateError && (
        <p className="error-message">{updateError}</p>
      )}
      {activateSuccess && (
        <p className="success-message">User activated successfully!</p>
      )}
      <br />

      {selectedRow && (
        <button onClick={handleActivate}>Activate User</button>
      )}

      <table className="dark-table">
        <thead>
          <tr>
            <th>User Id</th>
            <th>Email Id</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Is Active</th>
          </tr>
        </thead>
        <tbody>
          {data.length > 0 ? data.map((item) => (
            <tr
              key={item.id}
              className={selectedRow === item ? 'selected-row' : ''}
              onClick={() => handleRowClick(item)}
            >
              <td>{item.id}</td>
              <td>{item.emailId}</td>
              <td>{item.firstName}</td>
              <td>{item.lastName}</td>
              <td>{item.active ? 'Yes' : 'No'}</td>
            </tr>
          )) : (
            <tr>
              <td colSpan="5">No data available</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default Users;

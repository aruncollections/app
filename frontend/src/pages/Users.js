import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Common.css';

function Users() {
  const [data, setData] = useState([]);
  const [selectedRow, setSelectedRow] = useState(null);
  const [dataFetching, setDataFetching] = useState(true);
  const [updateError, setUpdateError] = useState(null);
  const [activateSuccess, setActivateSuccess] = useState(false);
  const [inactivateSuccess, setInactivateSuccess] = useState(false);
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
        const response = await axios.put(`${activateUrl}/${selectedRow.emailId}`);
        console.log('User activated:', response.data);
        setActivateSuccess(true);
        setDataFetching(true);
        setInactivateSuccess(false);
        setUpdateError(null);
      } catch (error) {
        console.error('Error activating user:', error);
        setUpdateError('Error activating user. Please try again.');
      }
    }
  };

  const handleInactivate = async () => {
    if (selectedRow !== null) {
      try {
        const response = await axios.put(`${inactivateUrl}/${selectedRow.emailId}`);
        console.log('User inactivated:', response.data);
        setInactivateSuccess(true);
        setDataFetching(true);
        setActivateSuccess(false);
        setUpdateError(null);
      } catch (error) {
        console.error('Error inactivating user:', error);
        setUpdateError('Error inactivating user. Please try again.');
      }
    }
  };

  return (
    <div className="dark-theme">
      <div className="button-container">
        <button className="button" onClick={handleActivate}>Activate User</button>
        <button className="button" onClick={handleInactivate}>Inactivate User</button>
      </div>
       {updateError && (
              <p className="error-message">{updateError}</p>
            )}
            {activateSuccess && (
              <p className="success-message">User activated successfully!</p>
            )}
            {inactivateSuccess && (
              <p className="success-message">User inactivated successfully!</p>
            )}
      <br />

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

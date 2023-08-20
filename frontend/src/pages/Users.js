import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Home.css';

function Users() {
  const [data, setData] = useState([]);
  const [dataFetching, setDataFetching] = useState(true);
  const [updateError, setupdateError] = useState(null);
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

  return (
      <div className="dark-theme" border="20">
      <h2 className="dark-text">Users in system</h2>

      <br />
      {updateError && (
        <p className="error-message">{updateError}</p>
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
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.emailId}</td>
              <td>{item.firstName}</td>
              <td>{item.lastName} {item.isActive} </td>
              <td>{item.active ? 'Yes' : 'No'}</td>
            </tr>
          )) : (
              <tr>
                <td colSpan="6">No data available</td>
              </tr>
            )}
        </tbody>
      </table>
    </div>
  );
}

export default Users;

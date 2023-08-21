import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, NavLink } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Users from './pages/Users';
import About from './pages/About';
import Logout from './pages/Logout';

function App() {
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    async function fetchCurrentUser() {
      try {
        const response = await fetch('/current-user');
        if (response.ok) {
          const userData = await response.json();
          setCurrentUser(userData);
        }
      } catch (error) {
        console.error('Error fetching current user:', error);
      }
    }

    fetchCurrentUser();
  }, []);

  return (
    <Router>
      <div className="App">
        <nav>
          <ul>
            <li>
              <NavLink exact to="/" activeClassName="active">Investor Home</NavLink>
            </li>
            <li>
              <NavLink to="/users" activeClassName="active">Users</NavLink>
            </li>
            <li>
              <NavLink to="/about" activeClassName="active">About</NavLink>
            </li>
            <li>
              <NavLink to="/logout">Logout</NavLink>
            </li>

            <li>
              {currentUser && (
                <span className="current-user">Hello, {currentUser.firstName}</span>
              )}
            </li>
          </ul>
        </nav>

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/users" element={<Users />} />
          <Route path="/about" element={<About />} />
          <Route path="/logout" element={<Logout />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

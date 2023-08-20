import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Logout() {
  const navigate = useNavigate();

  useEffect(() => {
   async function performLogout() {
     try {
       await fetch('/logout', {
         method: 'GET',
         credentials: 'include', // Add this line
       });
       window.location.href = '/';
     } catch (error) {
       console.error('Error logging out:', error);
     }
   }

    performLogout();
  }, [navigate]);

  return <p>Logging out...</p>;
}

export default Logout;

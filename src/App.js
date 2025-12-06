import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AuthOptions from './components/home.jsx';
import Login from './components/login.jsx';
import Register from './components/register.jsx';
import StudentDashboard from './components/studentdashboard.jsx';
import RecruiterDashboard from './components/recruiterdashboard.jsx';
import JobApply from './components/JobApply.jsx';
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<AuthOptions />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/studentdashboard" element={<StudentDashboard />} />
        <Route path="/recruiterdashboard" element={<RecruiterDashboard />} />
        <Route path="/job/apply/:studentId" element={<JobApply />} />
      </Routes>
    </Router>
  );
}

export default App;
// ...existing code...
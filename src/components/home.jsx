import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import zidioBg from '../zidio.jpg'; // ✅ Correct path to your image in src folder

const AuthOptions = () => {
  const [showOverview, setShowOverview] = useState(false);

  const toggleOverview = () => {
    setShowOverview(!showOverview);
  };

  return (
    <div
      className="min-h-screen bg-cover bg-center"
      style={{ backgroundImage: `url(${zidioBg})` }}
    >
      {/* Navigation Bar */}
      <nav className="bg-black bg-opacity-60 px-8 py-4 shadow-md">
        <div className="flex justify-between items-center">
          <span className="text-white text-2xl font-bold">Zidio Connect</span>
          <div className="flex gap-4">
            <span
              onClick={toggleOverview}
              className="text-white font-medium hover:text-blue-300 cursor-pointer"
            >
              {showOverview ? 'Close Overview' : 'Overview'}
            </span>
            <Link to="/internship" className="text-white font-medium hover:text-blue-300">
              Internship
            </Link>
            <Link to="/register" className="text-white font-medium hover:text-blue-300">
              Register
            </Link>
            <Link to="/login" className="text-white font-medium hover:text-blue-300">
              Login
            </Link>
            <Link to="/logout" className="text-white font-medium hover:text-blue-300">
              Logout
            </Link>
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <div className="min-h-[calc(100vh-80px)] flex justify-center items-center bg-black bg-opacity-40 p-4">
        {showOverview ? (
          <div className="bg-white p-8 rounded-2xl shadow-lg max-w-2xl w-full text-left">
            <h3 className="text-xl font-semibold text-gray-800 mb-4">About Zudio</h3>
            <p className="text-gray-700 mb-4 leading-relaxed">
              <strong>Zudio</strong> is a fast-fashion retail brand owned by <strong>Trent Limited</strong>,
              a subsidiary of the <strong>Tata Group</strong>. It's known for offering affordable, trendy clothing and home essentials.
            </p>
            <p className="text-gray-700 mb-4 leading-relaxed">
              <strong>Concept and Vision:</strong><br />
              Zudio was launched with the goal of making quality fashion affordable and accessible to a broader audience.
              It provides a diverse collection of apparel catering to various fashion preferences.
            </p>
            <p className="text-gray-700 mb-4 leading-relaxed">
              <strong>Business Model:</strong><br />
              Zudio operates on a direct-to-consumer approach, minimizing costs and passing savings to customers.
              It follows a FOCO (Franchise-Owned Company-Operated) model for expansion.
            </p>
            <div className="mt-6 text-center">
              <Link
                to="/courses"
                className="inline-block bg-blue-600 text-white px-6 py-2 rounded-xl hover:bg-blue-700 font-semibold"
              >
                View All Courses
              </Link>
            </div>
          </div>
        ) : (
          <div className="bg-white bg-opacity-90 p-8 rounded-2xl shadow-lg max-w-md w-full text-center">
            <h1 className="text-2xl font-bold mb-2 text-gray-800">Welcome to Zidio Connect</h1>
            <p className="text-gray-600 mb-6">Please choose an option to continue</p>
            <div className="flex flex-col gap-4">
              <Link
                to="/login"
                className="bg-blue-600 text-white py-2 rounded-xl hover:bg-blue-700 font-semibold text-center"
              >
                Login
              </Link>
              <Link
                to="/register"
                className="border-2 border-blue-600 text-blue-600 py-2 rounded-xl hover:bg-blue-50 font-semibold text-center"
              >
                Register
              </Link>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default AuthOptions;
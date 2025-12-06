import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Login = () => {
  const [formData, setFormData] = useState({ email: "", password: "" });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/login", formData, {
        headers: { "Content-Type": "application/json" },
        withCredentials: true,
      });

      const role = response.data.trim();

      if (role === "Admin") {
        navigate("/admindashboard");
      } else if (role === "Student") {
        localStorage.setItem("userEmail", formData.email);
        navigate("/studentdashboard");
      } else if (role === "Recruiter") {
        localStorage.setItem("userEmail", formData.email);
        navigate("/recruiterdashboard");
      } else {
        setMessage(response.data);
      }
    } catch (error) {
      setMessage("Login failed. Please try again.");
    }
  };

  return (
    <div className="flex flex-col items-center min-h-screen p-8 bg-gray-100">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">Login</h2>
      <form
        onSubmit={handleSubmit}
        className="bg-white p-6 rounded-lg shadow-md w-full max-w-md flex flex-col gap-4"
      >
        <input
          type="email"
          name="email"
          placeholder="Email"
          className="p-2 border border-gray-300 rounded text-base"
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          className="p-2 border border-gray-300 rounded text-base"
          onChange={handleChange}
          required
        />
        <button
          type="submit"
          className="bg-blue-600 text-white py-2 rounded hover:bg-blue-700 font-semibold"
        >
          Login
        </button>
      </form>

      {message && (
        <p className="mt-4 text-red-600 font-semibold text-center">{message}</p>
      )}

      <button
        onClick={() => navigate("/forgot-password")}
        className="mt-4 px-4 py-2 font-semibold rounded bg-gray-300 text-gray-800 hover:bg-gray-400"
      >
        Forgot Password?
      </button>

      <p className="mt-4 text-gray-600">Don't have an account?</p>
      <button
        onClick={() => navigate("/register")}
        className="mt-2 px-4 py-2 font-semibold rounded bg-green-500 text-white hover:bg-green-600"
      >
        Register Here
      </button>
    </div>
  );
};

export default Login;
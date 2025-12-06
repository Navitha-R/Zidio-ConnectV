import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Register() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
    phone: "",
    gender: "",
    role: "",
    address: "",
    profilePicture: null,
  });

  const [message, setMessage] = useState("");

  const validatePassword = (password) => {
    const regex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#])[A-Za-z\d@$!%*?&#]{8,}$/;
    return regex.test(password);
  };

  const handleChange = (e) => {
    const { name, value, files } = e.target;

    if (name === "profilePicture") {
      setFormData({ ...formData, profilePicture: files[0] });
    } else {
      setFormData({ ...formData, [name]: value });

      if (name === "password" && !validatePassword(value)) {
        setMessage(
          "❌ Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character."
        );
      } else {
        setMessage("");
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validatePassword(formData.password)) {
      setMessage("❌ Password does not meet complexity requirements.");
      return;
    }

    const data = new FormData();
    Object.entries(formData).forEach(([key, value]) => {
      data.append(key, value);
    });

    try {
      await axios.post("http://localhost:8080/register", data, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      navigate("/login");
    } catch (error) {
      if (error.response) {
        setMessage(`❌ ${error.response.data}`);
      } else {
        setMessage("❌ Registration failed. Please try again.");
      }
    }
  };

  return (
    <div className="flex flex-col items-center min-h-screen p-8 bg-gray-100">
      <h2 className="text-3xl font-bold text-gray-800 mb-6">Register</h2>
      <form
        onSubmit={handleSubmit}
        className="bg-white p-6 rounded-lg shadow-md w-full max-w-lg"
      >
        {["username", "email", "phone"].map((field) => (
          <div key={field} className="mb-4">
            <label className="font-semibold text-gray-700 capitalize">{field}:</label>
            <input
              type={field === "email" ? "email" : "text"}
              name={field}
              value={formData[field]}
              onChange={handleChange}
              className="w-full p-2 mt-1 border border-gray-300 rounded"
              required
            />
          </div>
        ))}

        <div className="mb-4">
          <label className="font-semibold text-gray-700">Password:</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            className="w-full p-2 mt-1 border border-gray-300 rounded"
            required
          />
        </div>

        <div className="mb-4">
          <label className="font-semibold text-gray-700">Gender:</label>
          <div className="flex gap-4 mt-1">
            {["male", "female", "other"].map((g) => (
              <label key={g}>
                <input
                  type="radio"
                  name="gender"
                  value={g}
                  onChange={handleChange}
                />{" "}
                {g.charAt(0).toUpperCase() + g.slice(1)}
              </label>
            ))}
          </div>
        </div>

        <div className="mb-4">
          <label className="font-semibold text-gray-700">Role:</label>
          <div className="flex gap-4 mt-1">
            {["Admin", "Student", "Recruiter"].map((role) => (
              <label key={role}>
                <input
                  type="radio"
                  name="role"
                  value={role}
                  onChange={handleChange}
                />{" "}
                {role}
              </label>
            ))}
          </div>
        </div>

        <div className="mb-4">
          <label className="font-semibold text-gray-700">Address:</label>
          <textarea
            name="address"
            value={formData.address}
            onChange={handleChange}
            className="w-full p-2 mt-1 border border-gray-300 rounded resize-y min-h-[60px]"
            required
          ></textarea>
        </div>

        <div className="mb-4">
          <label className="font-semibold text-gray-700">Profile Picture:</label>
          <input
            type="file"
            name="profilePicture"
            accept="image/*"
            onChange={handleChange}
            className="w-full p-2 mt-1 border border-gray-300 rounded"
          />
        </div>

        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded font-semibold hover:bg-blue-700 transition duration-200"
        >
          Register
        </button>
      </form>

      {message && (
        <p className="mt-4 text-red-600 font-semibold text-center">{message}</p>
      )}
    </div>
  );
}

export default Register;
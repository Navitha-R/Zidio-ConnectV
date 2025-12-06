import React, { useState } from "react";
import { useForm } from "react-hook-form";
import axios from "axios";

const ProfileManagement = () => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { isSubmitting },
  } = useForm();

  const [message, setMessage] = useState("");
  const [filesDisabled, setFilesDisabled] = useState(false);

  const onSubmit = async (data) => {
    const formData = new FormData();
    Object.entries(data).forEach(([key, value]) => {
      if (key === "profilePhoto" || key === "resume") {
        if (value?.[0]) formData.append(key, value[0]);
      } else {
        formData.append(key, value);
      }
    });

    try {
      await axios.post("http://localhost:8080/students", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      setMessage("🎉 Student profile added successfully!");
      reset();
      setFilesDisabled(true);
    } catch (error) {
      console.error("Error submitting profile:", error);
      setMessage("❌ Failed to submit profile.");
    }
  };

  return (
    <div className="max-w-4xl mx-auto p-8 bg-white shadow-2xl rounded-2xl mt-10">
      <h1 className="text-3xl font-bold text-center mb-6">Student Profile Form !</h1>

      {message && (
        <div className="mb-6 flex items-center justify-center">
          <div className={`px-6 py-4 rounded-lg shadow-sm w-full md:w-2/3
              ${message.includes("successfully")
              ? "bg-green-100 border border-green-400 text-green-800"
              : "bg-red-100 border border-red-400 text-red-800"}`}>
            <p className="text-sm md:text-base font-medium text-center">{message}</p>
          </div>
        </div>
      )}

      <form onSubmit={handleSubmit(onSubmit)} className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Personal Info */}
        <InputField label="Full Name" name="fullName" register={register} required />
        <InputField label="Email" name="email" register={register} type="email" required />
        <InputField label="Phone" name="phone" register={register} required />
        <InputField label="Location" name="location" register={register} required />

        {/* Education */}
        <InputField label="College" name="college" register={register} required />
        <InputField label="Degree" name="degree" register={register} required />
        <InputField label="Year" name="year" register={register} required />
        <InputField label="GPA" name="gpa" register={register} required />
        <InputField label="Graduation Date" name="gradDate" type="date" register={register} required />

        {/* Skills & Projects */}
        <InputField label="Skills" name="skills" register={register} required />
        <InputField label="Certifications" name="certifications" register={register} />
        <InputField label="Projects" name="projects" register={register} required />

        {/* Social Links */}
        <InputField label="LinkedIn" name="linkedIn" register={register} required />
        <InputField label="GitHub" name="github" register={register} required />

        {/* Availability Checkboxes */}
        <div className="col-span-1 md:col-span-2 grid grid-cols-1 md:grid-cols-3 gap-4">
          <label className="flex items-center gap-2 font-medium">
            <input type="checkbox" {...register("lookingForInternship")} className="accent-blue-600" />
            Looking for Internship
          </label>
          <label className="flex items-center gap-2 font-medium">
            <input type="checkbox" {...register("lookingForFullTime")} className="accent-blue-600" />
            Looking for Full-Time
          </label>
          <label className="flex items-center gap-2 font-medium">
            <input type="checkbox" {...register("lookingForPartTime")} className="accent-blue-600" />
            Looking for Part-Time
          </label>
        </div>

        <InputField label="Availability" name="availability" register={register} required />
        <InputField label="Preferred Locations" name="preferredLocations" register={register} required />

        {/* File Uploads */}
        <div>
          <label className="font-medium block mb-1">Profile Photo <span className="text-red-500">*</span></label>
          <input
            type="file"
            {...register("profilePhoto", { required: true })}
            className="w-full border border-gray-300 rounded-lg py-2 px-3 bg-white text-sm"
            accept="image/*"
            disabled={filesDisabled}
          />
        </div>

        <div>
          <label className="font-medium block mb-1">Resume <span className="text-red-500">*</span></label>
          <input
            type="file"
            {...register("resume", { required: true })}
            className="w-full border border-gray-300 rounded-lg py-2 px-3 bg-white text-sm"
            accept=".pdf,.doc,.docx"
            disabled={filesDisabled}
          />
        </div>

        {/* Submit */}
        <div className="col-span-1 md:col-span-2 text-center mt-6">
          <button
            type="submit"
            disabled={isSubmitting}
            className="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-lg transition"
          >
            {isSubmitting ? "Submitting..." : "Submit Profile"}
          </button>
        </div>
      </form>
    </div>
  );
};

// 🔹 Reusable input field component with consistent style
const InputField = ({ label, name, register, required = false, type = "text" }) => (
  <div>
    <label className="font-medium block mb-1">
      {label} {required && <span className="text-red-500">*</span>}
    </label>
    <input
      {...register(name, required ? { required: true } : {})}
      type={type}
      className="w-full border border-gray-300 rounded-lg py-2 px-3 bg-white text-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
      placeholder={`Enter ${label.toLowerCase()}`}
    />
  </div>
);

export default ProfileManagement;
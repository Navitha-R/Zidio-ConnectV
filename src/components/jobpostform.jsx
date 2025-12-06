import React, { useState } from "react";

const defaultDepartments = ["CSE", "IT", "ECE", "EEE", "MECH", "CIVIL", "MBA"];

const JobPostForm = () => {
  const [formData, setFormData] = useState({
    title: "",
    jobType: "",
    workMode: "",
    location: "",
    companyName: "",
    companyLogo: "",
    aboutCompany: "",
    startDate: "",
    deadline: "",
    duration: "",
    responsibilities: "",
    skills: "",
    minimumPercentage: "",
    openings: 1,
    contactEmail: "",
  });

  const [passingYearInput, setPassingYearInput] = useState("");
  const [passingYears, setPassingYears] = useState([]);
  const [selectedDepartments, setSelectedDepartments] = useState([]);
  const [customDepartment, setCustomDepartment] = useState("");
  const [showCustomDeptInput, setShowCustomDeptInput] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleAddYear = () => {
    const year = passingYearInput.trim();
    if (year && /^\d{4}$/.test(year) && !passingYears.includes(year)) {
      setPassingYears([...passingYears, year]);
      setPassingYearInput("");
    }
  };

  const handleRemoveYear = (year) => {
    setPassingYears(passingYears.filter((y) => y !== year));
  };

  const handleDepartmentChange = (dept) => {
    if (dept === "Others") {
      setShowCustomDeptInput(!showCustomDeptInput);
      return;
    }
    if (selectedDepartments.includes(dept)) {
      setSelectedDepartments(selectedDepartments.filter((d) => d !== dept));
    } else {
      setSelectedDepartments([...selectedDepartments, dept]);
    }
  };

  const handleAddCustomDepartment = () => {
    const trimmed = customDepartment.trim();
    if (
      trimmed &&
      !selectedDepartments.includes(trimmed) &&
      !defaultDepartments.includes(trimmed)
    ) {
      setSelectedDepartments([...selectedDepartments, trimmed]);
      setCustomDepartment("");
      setShowCustomDeptInput(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      formData.title &&
      formData.jobType &&
      formData.workMode &&
      formData.location &&
      formData.companyName &&
      formData.aboutCompany &&
      formData.startDate &&
      formData.deadline &&
      formData.duration &&
      formData.responsibilities &&
      formData.skills &&
      formData.minimumPercentage &&
      formData.openings &&
      formData.contactEmail &&
      passingYears.length > 0 &&
      selectedDepartments.length > 0
    ) {
      const fullJobData = {
        ...formData,
        passingYears,
        eligibleDepartments: selectedDepartments,
      };

      try {
        const response = await fetch("http://localhost:8080/jobs", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(fullJobData),
        });

        if (!response.ok) {
          throw new Error("Failed to post job");
        }

        const result = await response.json();
        console.log("✅ Job Posted Successfully:", result);
        alert("✅ Job posted successfully!");

        // Reset form
        setFormData({
          title: "",
          jobType: "",
          workMode: "",
          location: "",
          companyName: "",
          companyLogo: "",
          aboutCompany: "",
          startDate: "",
          deadline: "",
          duration: "",
          responsibilities: "",
          skills: "",
          minimumPercentage: "",
          openings: 1,
          contactEmail: "",
        });
        setPassingYears([]);
        setSelectedDepartments([]);
        setCustomDepartment("");
        setShowCustomDeptInput(false);
      } catch (error) {
        console.error("❌ Error posting job:", error);
        alert("❌ Failed to post job. Please try again.");
      }
    } else {
      alert("❌ Please fill all required fields.");
    }
  };

  return (
    <div className="max-w-5xl mx-auto p-8 bg-white shadow-xl rounded-2xl">
      <h2 className="text-3xl font-semibold text-gray-800 mb-8 text-center">
        Post a New Job
      </h2>

      <form onSubmit={handleSubmit} className="space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {[
            ["title", "Job Title"],
            ["jobType", "Job Type"],
            ["workMode", "Work Mode"],
            ["location", "Location"],
            ["companyName", "Company Name"],
            ["companyLogo", "Company Logo URL"],
            ["startDate", "Start Date", "date"],
            ["deadline", "Application Deadline", "date"],
            ["duration", "Duration (in months)"],
            ["openings", "Openings", "number"],
            ["contactEmail", "Contact Email"],
          ].map(([name, label, type = "text"]) => (
            <div key={name}>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                {label} <span className="text-red-500">*</span>
              </label>
              <input
                type={type}
                name={name}
                value={formData[name]}
                onChange={handleChange}
                required
                className="w-full border border-gray-300 px-3 py-2 rounded-md text-sm focus:outline-none focus:ring focus:ring-blue-200"
              />
            </div>
          ))}
        </div>

        {/* Textareas */}
        {[
          ["aboutCompany", "About Company"],
          ["responsibilities", "Responsibilities"],
          ["skills", "Skills Required"],
        ].map(([name, label]) => (
          <div key={name}>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              {label} <span className="text-red-500">*</span>
            </label>
            <textarea
              name={name}
              value={formData[name]}
              onChange={handleChange}
              rows={3}
              required
              className="w-full border border-gray-300 px-3 py-2 rounded-md text-sm focus:outline-none focus:ring focus:ring-blue-200"
            />
          </div>
        ))}

        {/* Eligibility */}
        <div>
          <h3 className="text-lg font-semibold text-gray-800 mb-4">
            Eligibility Criteria
          </h3>

          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Minimum Percentage <span className="text-red-500">*</span>
            </label>
            <input
              type="number"
              name="minimumPercentage"
              value={formData.minimumPercentage}
              onChange={handleChange}
              required
              className="w-full border border-gray-300 px-3 py-2 rounded-md text-sm"
              placeholder="e.g., 60"
            />
          </div>

          {/* Passing Years */}
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1">
              Passing Years <span className="text-red-500">*</span>
            </label>
            <div className="flex gap-2">
              <input
                type="text"
                value={passingYearInput}
                onChange={(e) => setPassingYearInput(e.target.value)}
                onKeyDown={(e) =>
                  e.key === "Enter" && (e.preventDefault(), handleAddYear())
                }
                placeholder="e.g., 2024"
                className="flex-1 border border-gray-300 px-3 py-2 rounded-md"
              />
              <button
                type="button"
                onClick={handleAddYear}
                className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
              >
                Add
              </button>
            </div>
            <div className="flex flex-wrap gap-2 mt-2">
              {passingYears.map((year) => (
                <span
                  key={year}
                  className="bg-blue-100 text-blue-700 px-3 py-1 rounded-full flex items-center gap-2"
                >
                  {year}
                  <button
                    type="button"
                    onClick={() => handleRemoveYear(year)}
                    className="text-red-500"
                  >
                    ×
                  </button>
                </span>
              ))}
            </div>
          </div>

          {/* Departments */}
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1">
              Eligible Departments <span className="text-red-500">*</span>
            </label>
            <div className="grid grid-cols-2 sm:grid-cols-3 gap-2">
              {defaultDepartments.map((dept) => (
                <label key={dept} className="flex items-center gap-2 text-sm">
                  <input
                    type="checkbox"
                    checked={selectedDepartments.includes(dept)}
                    onChange={() => handleDepartmentChange(dept)}
                    className="accent-blue-600"
                  />
                  {dept}
                </label>
              ))}
              <label className="flex items-center gap-2 text-sm">
                <input
                  type="checkbox"
                  checked={showCustomDeptInput}
                  onChange={() => handleDepartmentChange("Others")}
                  className="accent-blue-600"
                />
                Others
              </label>
            </div>

            {showCustomDeptInput && (
              <div className="flex mt-3 gap-2">
                <input
                  type="text"
                  value={customDepartment}
                  onChange={(e) => setCustomDepartment(e.target.value)}
                  placeholder="Enter custom department"
                  className="flex-1 border border-gray-300 px-3 py-2 rounded-md"
                />
                <button
                  type="button"
                  onClick={handleAddCustomDepartment}
                  className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700"
                >
                  Add
                </button>
              </div>
            )}
          </div>
        </div>

        <button
          type="submit"
          className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-md text-lg font-semibold"
        >
          Post Job
        </button>
      </form>
    </div>
  );
};

export default JobPostForm;
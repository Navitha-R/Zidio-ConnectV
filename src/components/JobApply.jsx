import React, { useEffect, useState } from "react";
import axios from "axios";

const JobApply = ({ studentId }) => {
  const [student, setStudent] = useState(null);
  const [eligibleJobs, setEligibleJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchStudentAndJobs = async () => {
      try {
        if (!studentId) {
          setError("Student ID is missing.");
          setLoading(false);
          return;
        }

        // Fetch student profile
        const { data: studentData } = await axios.get(`http://localhost:8080/students/${studentId}`);
        setStudent(studentData);

        // Fetch jobs by passing year
        const { data: jobs } = await axios.get(`http://localhost:8080/job-posts/eligible/${studentData.year}`);

        // Filter jobs based on GPA and degree
        const filteredJobs = jobs.filter(job =>
          studentData.gpa >= job.minimumPercentage &&
          job.eligibleDepartments.includes(studentData.degree)
        );

        setEligibleJobs(filteredJobs);
      } catch (err) {
        console.error(err);
        setError("Failed to fetch data.");
      } finally {
        setLoading(false);
      }
    };

    fetchStudentAndJobs();
  }, [studentId]);

  if (loading) return <div className="text-center mt-10 text-lg">Loading...</div>;
  if (error) return <div className="text-center mt-10 text-red-500">{error}</div>;

  return (
    <div className="max-w-6xl mx-auto px-4 py-6">
      <h1 className="text-3xl font-bold text-center mb-6">
        Jobs Matching Your Profile: {student?.fullName}
      </h1>

      {eligibleJobs.length === 0 ? (
        <p className="text-center text-gray-600">No eligible jobs found based on your profile.</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {eligibleJobs.map(job => (
            <div key={job.id} className="bg-white border shadow rounded-xl p-5">
              <h2 className="text-xl font-semibold text-blue-700 mb-2">{job.title}</h2>
              <p><strong>Company:</strong> {job.companyName}</p>
              <p><strong>Location:</strong> {job.location}</p>
              <p><strong>Mode:</strong> {job.workMode}</p>
              <p><strong>Type:</strong> {job.jobType}</p>
              <p><strong>Skills:</strong> {job.skills}</p>
              <p><strong>GPA Required:</strong> {job.minimumPercentage}</p>
              <p><strong>Departments:</strong> {job.eligibleDepartments.join(", ")}</p>
              <p><strong>Years:</strong> {job.passingYears.join(", ")}</p>
              <button className="mt-4 w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700">
                Apply Now
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default JobApply;
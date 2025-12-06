import React from "react";
import { Trash2 } from "lucide-react";
import toast, { Toaster } from "react-hot-toast";
import useFetchJobs from "./useFetchJobs";
import Countdown from "./Countdown";
import axios from "axios";

const JobCard = ({ job, handleDelete }) => (
  <div className="bg-white rounded-2xl shadow-md hover:shadow-xl transition p-6 border border-gray-100 relative">
    <button
      onClick={() => handleDelete(job.id)}
      title="Delete Job"
      className="absolute top-4 right-4 p-2 bg-red-100 text-red-600 rounded-full hover:bg-red-200 transition"
    >
      <Trash2 className="w-5 h-5" />
    </button>

    <div className="flex items-center gap-4 mb-4">
      {job.companyLogo ? (
        <img
          src={job.companyLogo}
          alt="Logo"
          className="w-14 h-14 rounded-full object-cover border border-gray-300"
        />
      ) : (
        <div className="w-14 h-14 rounded-full bg-gray-200 flex items-center justify-center text-gray-500 text-xl">
          ✦
        </div>
      )}
      <div>
        <h3 className="text-xl font-semibold text-gray-800">{job.title}</h3>
        <p className="text-sm text-gray-500">{job.companyName}</p>
      </div>
    </div>

    <div className="text-sm text-gray-700 space-y-1">
      <p><span className="font-semibold">Location:</span> {job.location}</p>
      <p><span className="font-semibold">Type:</span> {job.jobType} ({job.workMode})</p>
      <p><span className="font-semibold">Duration:</span> {job.duration || "N/A"}</p>
      <p><span className="font-semibold">Start Date:</span> {job.startDate}</p>
      <p><span className="font-semibold">Deadline:</span> <Countdown deadline={job.deadline} /></p>
      <p><span className="font-semibold">Openings:</span> {job.openings}</p>
    </div>

    <div className="mt-4 text-sm text-gray-600 space-y-1">
      <p><span className="font-medium">About:</span> {job.aboutCompany}</p>
      <p><span className="font-medium">Responsibilities:</span> {job.responsibilities}</p>
      <p><span className="font-medium">Skills:</span> {job.skills}</p>
      <p><span className="font-medium">Minimum %:</span> {job.minimumPercentage}%</p>
      <p><span className="font-medium">Passing Years:</span> {job.passingYears?.join(", ")}</p>
      <p><span className="font-medium">Departments:</span> {job.eligibleDepartments?.join(", ")}</p>
      <p>
        <span className="font-medium">Contact:</span>{" "}
        <a
          href={`mailto:${job.contactEmail}`}
          className="text-indigo-600 hover:underline"
        >
          {job.contactEmail}
        </a>
      </p>
    </div>
  </div>
);

const JobList = () => {
  const { jobs, loading, setJobs } = useFetchJobs();

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this job?")) return;

    try {
      await axios.delete(`http://localhost:8080/viewjobs/${id}`);
      setJobs((prev) => prev.filter((job) => job.id !== id));
      toast.success("✅ Job deleted successfully!");
    } catch (error) {
      console.error("❌ Delete failed:", error);
      toast.error("❌ Failed to delete job.");
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-4 py-10">
      <Toaster position="top-right" />
      {loading ? (
        <div className="flex justify-center items-center h-64">
          <div className="animate-spin h-10 w-10 border-t-2 border-indigo-600 rounded-full"></div>
        </div>
      ) : jobs.length === 0 ? (
        <div className="text-center text-gray-500 mt-16 text-lg">
          No jobs found.
        </div>
      ) : (
        <div className="grid gap-6 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
          {jobs.map((job) => (
            <JobCard key={job.id} job={job} handleDelete={handleDelete} />
          ))}
        </div>
      )}
    </div>
  );
};

export default JobList;
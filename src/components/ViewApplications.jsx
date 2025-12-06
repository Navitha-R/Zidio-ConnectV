import React, { useState } from 'react';
import useFetchApplications from './useFetchApplications';
import useFetchJobs from './useFetchJobs';
import ApplicationCard from './ApplicationCard';
import toast, { Toaster } from 'react-hot-toast';
import axios from 'axios';

const ViewApplications = () => {
  const { applications, loading, setApplications } = useFetchApplications();
  const { jobs } = useFetchJobs(); // Fetch job listings for dropdown
  const [selectedJobId, setSelectedJobId] = useState('');
  const [search, setSearch] = useState('');

  const handleAction = async (id, status) => {
    try {
      await axios.put(`http://localhost:8080/updateApplication/${id}`, { status });
      setApplications((prev) =>
        prev.map((app) => (app.id === id ? { ...app, status } : app))
      );
      toast.success(`Application ${status}`);
    } catch (error) {
      console.error('Error updating status:', error);
      toast.error('Failed to update status.');
    }
  };

  // Filtered applications based on job ID and search query
  const filteredApplications = applications.filter(
    (app) =>
      app.jobId === selectedJobId &&
      (app.applicantName.toLowerCase().includes(search.toLowerCase()) ||
        app.jobTitle.toLowerCase().includes(search.toLowerCase()))
  );

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <Toaster position="top-right" />

      <div className="mb-4 flex gap-4 items-center">
        <select
          className="p-2 border border-gray-300 rounded-md w-1/2"
          value={selectedJobId}
          onChange={(e) => setSelectedJobId(Number(e.target.value))}
        >
          <option value="">Select a Job</option>
          {jobs.map((job) => (
            <option key={job.id} value={job.id}>
              {job.title} - {job.companyName}
            </option>
          ))}
        </select>

        <input
          type="text"
          placeholder="Search applications..."
          className="p-2 border border-gray-300 rounded-md flex-1"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading ? (
        <div className="flex justify-center items-center h-64">
          <div className="animate-spin rounded-full h-10 w-10 border-t-2 border-indigo-600"></div>
        </div>
      ) : filteredApplications.length === 0 ? (
        <div className="text-center text-gray-500 mt-16 text-lg">No applications found.</div>
      ) : (
        <div className="grid gap-6 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
          {filteredApplications.map((application) => (
            <ApplicationCard
              key={application.id}
              application={application}
              handleAction={handleAction}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default ViewApplications;
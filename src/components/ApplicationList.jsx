import React, { useState } from "react";

// Sample jobs posted by recruiter with applicants
const sampleJobs = [
  {
    id: 1,
    title: "Frontend Developer Intern",
    applicants: [
      {
        id: 101,
        name: "Alice Johnson",
        email: "alice@example.com",
        appliedDate: "2025-05-10",
        resumeUrl: "#",
        linkedIn: "https://linkedin.com/in/alicejohnson",
        coverLetter: "I am passionate about React and front-end dev.",
        status: "Applied",
        notes: "",
      },
      {
        id: 102,
        name: "Bob Smith",
        email: "bobsmith@gmail.com",
        appliedDate: "2025-05-12",
        resumeUrl: "#",
        linkedIn: "",
        coverLetter: "Looking forward to this internship opportunity.",
        status: "Shortlisted",
        notes: "Strong skills in JS.",
      },
    ],
  },
  {
    id: 2,
    title: "Backend Developer",
    applicants: [
      {
        id: 201,
        name: "Charlie Davis",
        email: "charlie.d@example.com",
        appliedDate: "2025-04-28",
        resumeUrl: "#",
        linkedIn: "https://linkedin.com/in/charliedavis",
        coverLetter: "Experienced in Node.js and databases.",
        status: "Applied",
        notes: "",
      },
    ],
  },
];

const statuses = ["Applied", "Shortlisted", "Rejected"];

const RecruiterApplicationViewer = () => {
  const [selectedJobId, setSelectedJobId] = useState(sampleJobs[0].id);
  const [jobs, setJobs] = useState(sampleJobs);

  // Find selected job
  const selectedJob = jobs.find((job) => job.id === selectedJobId);

  // Handle status change for an applicant
  const updateApplicantStatus = (applicantId, newStatus) => {
    setJobs((prevJobs) =>
      prevJobs.map((job) =>
        job.id === selectedJobId
          ? {
              ...job,
              applicants: job.applicants.map((applicant) =>
                applicant.id === applicantId
                  ? { ...applicant, status: newStatus }
                  : applicant
              ),
            }
          : job
      )
    );
  };

  // Handle notes update
  const updateApplicantNotes = (applicantId, notes) => {
    setJobs((prevJobs) =>
      prevJobs.map((job) =>
        job.id === selectedJobId
          ? {
              ...job,
              applicants: job.applicants.map((applicant) =>
                applicant.id === applicantId ? { ...applicant, notes } : applicant
              ),
            }
          : job
      )
    );
  };

  return (
    <div className="flex flex-col h-screen p-4 bg-gray-50">
      {/* Job selection */}
      <div className="mb-4">
        <label htmlFor="job-select" className="block text-gray-700 font-semibold mb-2">
          Select Job / Internship:
        </label>
        <select
          id="job-select"
          className="w-full p-2 border border-gray-300 rounded"
          value={selectedJobId}
          onChange={(e) => setSelectedJobId(Number(e.target.value))}
        >
          {jobs.map((job) => (
            <option key={job.id} value={job.id}>
              {job.title} ({job.applicants.length} applicants)
            </option>
          ))}
        </select>
      </div>

      {/* Applicant list */}
      <div className="flex flex-col overflow-auto max-h-[75vh] bg-white rounded shadow p-4">
        {selectedJob.applicants.length === 0 ? (
          <p className="text-gray-500">No applications received yet.</p>
        ) : (
          selectedJob.applicants.map((applicant) => (
            <div
              key={applicant.id}
              className="border-b border-gray-200 py-4 last:border-b-0"
            >
              <div className="flex flex-col md:flex-row md:justify-between md:items-center">
                <div>
                  <h3 className="text-lg font-semibold">{applicant.name}</h3>
                  <p className="text-sm text-gray-600">{applicant.email}</p>
                  <p className="text-xs text-gray-500">Applied: {applicant.appliedDate}</p>
                  <p className="mt-1 text-sm">
                    Resume:{" "}
                    <a
                      href={applicant.resumeUrl}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="text-indigo-600 underline"
                    >
                      View / Download
                    </a>
                  </p>
                  {applicant.linkedIn && (
                    <p className="text-sm mt-1">
                      LinkedIn:{" "}
                      <a
                        href={applicant.linkedIn}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="text-indigo-600 underline"
                      >
                        Profile
                      </a>
                    </p>
                  )}
                </div>

                <div className="mt-3 md:mt-0 flex items-center space-x-2">
                  {statuses.map((status) => (
                    <button
                      key={status}
                      onClick={() => updateApplicantStatus(applicant.id, status)}
                      className={`px-3 py-1 rounded text-sm font-semibold transition
                        ${
                          applicant.status === status
                            ? "bg-indigo-600 text-white"
                            : "bg-gray-200 text-gray-700 hover:bg-gray-300"
                        }
                      `}
                      title={`Mark as ${status}`}
                    >
                      {status}
                    </button>
                  ))}
                </div>
              </div>

              <div className="mt-3">
                <p className="font-semibold">Cover Letter:</p>
                <p className="text-gray-700 whitespace-pre-wrap">{applicant.coverLetter || "N/A"}</p>
              </div>

              <div className="mt-3">
                <label htmlFor={`notes-${applicant.id}`} className="font-semibold">
                  Internal Notes (optional):
                </label>
                <textarea
                  id={`notes-${applicant.id}`}
                  className="w-full p-2 border border-gray-300 rounded mt-1 resize-y"
                  rows={3}
                  value={applicant.notes}
                  onChange={(e) => updateApplicantNotes(applicant.id, e.target.value)}
                  placeholder="Add internal remarks here..."
                />
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default RecruiterApplicationViewer;
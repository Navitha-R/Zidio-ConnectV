import React from 'react';
import { Check, X } from 'lucide-react';

const ApplicationCard = ({ application, handleAction }) => {
  const { id, applicantName, email, jobTitle, jobType, status, resumeLink, coverLetter, skills } = application;

  const getStatusColor = () => {
    switch (status) {
      case 'Accepted':
        return 'text-green-600';
      case 'Rejected':
        return 'text-red-600';
      default:
        return 'text-yellow-600';
    }
  };

  return (
    <div className="bg-white shadow-lg rounded-lg p-5 border border-gray-100 hover:shadow-md transition relative">
      {/* Action Buttons */}
      <div className="absolute top-3 right-3 flex space-x-2">
        <button
          onClick={() => handleAction(id, 'Accepted')}
          title="Accept Application"
          className="bg-green-100 p-2 rounded-full text-green-600 hover:bg-green-200 transition"
        >
          <Check className="w-4 h-4" />
        </button>
        <button
          onClick={() => handleAction(id, 'Rejected')}
          title="Reject Application"
          className="bg-red-100 p-2 rounded-full text-red-600 hover:bg-red-200 transition"
        >
          <X className="w-4 h-4" />
        </button>
      </div>

      {/* Applicant Info */}
      <h3 className="text-lg font-semibold text-gray-800">{applicantName}</h3>
      <p className="text-sm text-gray-500">{email}</p>

      {/* Application Details */}
      <div className="mt-2 text-sm text-gray-700 space-y-1">
        <p><strong>Applied for:</strong> {jobTitle} ({jobType})</p>
        <p>
          <strong>Status:</strong>{' '}
          <span className={`font-semibold ${getStatusColor()}`}>
            {status}
          </span>
        </p>
      </div>

      {/* Additional Info */}
      <div className="mt-3 text-sm text-gray-700 space-y-1">
        <p>
          <strong>Resume:</strong>{' '}
          <a
            href={resumeLink}
            target="_blank"
            rel="noopener noreferrer"
            className="text-indigo-600 hover:underline"
          >
            Download
          </a>
        </p>
        {coverLetter && <p><strong>Cover Letter:</strong> {coverLetter}</p>}
        <p><strong>Skills:</strong> {skills}</p>
      </div>
    </div>
  );
};

export default ApplicationCard;
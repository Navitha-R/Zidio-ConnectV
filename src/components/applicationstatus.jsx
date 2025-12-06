import React, { useState } from 'react';

const sampleApplications = [
  {
    id: 1,
    title: 'Software Engineer Intern',
    company: 'Unstop',
    status: 'Under Review',
    appliedDate: '2025-05-01',
    nextStep: 'Awaiting Interview Schedule',
    contact: 'hr@unstop.com',
  },
  {
    id: 2,
    title: 'Marketing Analyst',
    company: 'Apple',
    status: 'Interview Scheduled',
    appliedDate: '2025-04-20',
    nextStep: 'Prepare for Interview on 2025-05-25',
    contact: 'recruitment@apple.com',
  },
];

const stages = [
  'Applied',
  'Under Review',
  'Shortlisted',
  'Interview Scheduled',
  'Offered',
  'Rejected',
];

const ApplicationStatus = () => {
  const [applications] = useState(sampleApplications);
  const [selectedAppId, setSelectedAppId] = useState(null);

  const getStageIndex = (status) => {
    const index = stages.indexOf(status);
    return index !== -1 ? index : 0;
  };

  const selectedApp = applications.find((app) => app.id === selectedAppId);

  return (
    <div className="max-w-6xl mx-auto p-6">
      <h2 className="text-3xl font-bold text-gray-800 mb-6">Application Status</h2>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {applications.map((app) => (
          <div
            key={app.id}
            onClick={() => setSelectedAppId(app.id)}
            className={`p-4 rounded-lg border shadow-sm cursor-pointer transition duration-300
              ${
                selectedAppId === app.id
                  ? 'bg-indigo-100 border-indigo-500'
                  : 'bg-white hover:bg-gray-50'
              }`}
          >
            <h3 className="text-lg font-semibold text-gray-900">{app.title}</h3>
            <p className="text-gray-600">{app.company}</p>
            <p className="text-sm text-indigo-700 font-medium">Status: {app.status}</p>
          </div>
        ))}
      </div>

      {selectedApp && (
        <div className="mt-10 p-6 bg-white rounded-lg shadow-md">
          <h3 className="text-xl font-semibold mb-4 text-gray-800">Application Details</h3>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-gray-700">
            <p><span className="font-medium">Title:</span> {selectedApp.title}</p>
            <p><span className="font-medium">Company:</span> {selectedApp.company}</p>
            <p><span className="font-medium">Applied Date:</span> {selectedApp.appliedDate}</p>
            <p><span className="font-medium">Current Status:</span> {selectedApp.status}</p>
            <p className="sm:col-span-2"><span className="font-medium">Next Step:</span> {selectedApp.nextStep}</p>
            <p className="sm:col-span-2"><span className="font-medium">Contact:</span> {selectedApp.contact}</p>
          </div>

          <div className="mt-6">
            <h4 className="text-md font-semibold text-gray-800 mb-2">Progress</h4>
            <div className="flex items-center space-x-2 overflow-x-auto">
              {stages.map((stage, index) => (
                <div key={stage} className="flex items-center space-x-2">
                  <div
                    className={`px-3 py-1 rounded-full text-sm font-medium 
                      ${
                        index <= getStageIndex(selectedApp.status)
                          ? 'bg-indigo-600 text-white'
                          : 'bg-gray-200 text-gray-600'
                      }`}
                  >
                    {stage}
                  </div>
                  {index < stages.length - 1 && (
                    <span className="text-gray-400">→</span>
                  )}
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ApplicationStatus;
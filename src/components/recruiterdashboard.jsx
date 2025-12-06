import React, { useState } from "react";
import { motion } from "framer-motion";
import JobPostForm from "./jobpostform";
import JobList from "./JobList";
import ViewApplications from "./ViewApplications";

const RecruiterDashboard = () => {
  const [activeTab, setActiveTab] = useState("post");
  const [selectedJobId, setSelectedJobId] = useState(null);

  const renderContent = () => {
    switch (activeTab) {
      case "post":
        return <JobPostForm />;
      case "jobs":
        return <JobList onSelectJob={setSelectedJobId} />;
      case "applications":
        return selectedJobId ? (
          <ViewApplications jobId={selectedJobId} />
        ) : (
          <div className="p-6 text-gray-500">Please select a job to view applications.</div>
        );
      default:
        return <div className="p-6">Select an option</div>;
    }
  };

  return (
    <div className="flex flex-col h-screen">
      {/* Header */}
      <motion.div
        initial={{ opacity: 0, y: -30 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 1 }}
        className="text-3xl font-bold text-center text-indigo-700 py-6 bg-indigo-100 shadow"
      >
        Recruiter Dashboard - Zidio Connect
      </motion.div>

      {/* Main Area */}
      <div className="flex flex-1 overflow-hidden">
        {/* Sidebar */}
        <div className="w-1/5 bg-gray-100 p-4 shadow-md space-y-4 overflow-hidden">
          {["post", "jobs", "applications"].map((tab) => (
            <button
              key={tab}
              onClick={() => setActiveTab(tab)}
              className={`w-full text-left px-4 py-2 rounded transition ${
                activeTab === tab
                  ? "bg-indigo-200 font-semibold"
                  : "hover:bg-gray-200"
              }`}
            >
              {{
                post: "Post Job/Internship",
                jobs: "View Job Listings",
                applications: "View Applications",
              }[tab]}
            </button>
          ))}
        </div>

        {/* Scrollable Content */}
        <div className="w-4/5 h-full overflow-y-auto bg-white p-4">
          {renderContent()}
        </div>
      </div>
    </div>
  );
};

export default RecruiterDashboard;
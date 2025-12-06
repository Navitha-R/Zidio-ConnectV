import React, { useState } from "react";
import { motion } from "framer-motion";
import ProfileManagement from "./ProfileManagement";
import ResumeUpload from "./resume";
import ApplicationStatus from "./applicationstatus";
import JobApply from "./JobApply";

const StudentDashboard = ({ studentId }) => {
  const [activeTab, setActiveTab] = useState("profile");

  const renderContent = () => {
    switch (activeTab) {
      case "profile":
        return <ProfileManagement studentId={studentId} />;
      case "resume":
        return <ResumeUpload studentId={studentId} />;
      case "jobs":
        return <JobApply studentId={studentId} />;  // ✅ Pass here
      case "status":
        return <ApplicationStatus studentId={studentId} />;
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
        className="flex-none text-3xl font-bold text-center text-indigo-700 py-6 bg-indigo-100 shadow"
      >
        Welcome to Zidio-Connect
      </motion.div>

      {/* Main area */}
      <div className="flex flex-1 overflow-hidden">
        {/* Sidebar */}
        <div className="w-1/5 flex-none bg-gray-100 p-4 space-y-4 shadow-md h-full overflow-hidden">
          {["profile", "resume", "jobs", "status"].map((tab) => (
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
                profile: "Profile Management",
                resume: "Resume Upload",
                jobs: "Jobs / Internships",
                status: "Application Status",
              }[tab]}
            </button>
          ))}
        </div>

        {/* Content */}
        <div className="w-4/5 flex-1 bg-white h-full overflow-y-auto">
          {renderContent()}
        </div>
      </div>
    </div>
  );
};

export default StudentDashboard;
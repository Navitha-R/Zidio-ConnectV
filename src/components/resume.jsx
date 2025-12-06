import { useState, useRef, useEffect, useCallback } from "react";

const ResumeUpload = ({ studentId }) => {
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState("");
  const objectUrlRef = useRef(null);

  const numericStudentId = Number(studentId);
  const validStudentId = numericStudentId > 0;

  const fetchResume = useCallback(async () => {
    try {
      const res = await fetch(`http://localhost:8080/getResume?studentId=${numericStudentId}`);
      if (res.ok) {
        const blob = await res.blob();
        const contentDisposition = res.headers.get("Content-Disposition");
        let fileName = "resume";
        if (contentDisposition && contentDisposition.includes("filename=")) {
          fileName = contentDisposition.split("filename=")[1].replaceAll('"', "").trim();
        }
        const resumeFile = new File([blob], fileName, { type: blob.type });

        if (objectUrlRef.current) URL.revokeObjectURL(objectUrlRef.current);
        objectUrlRef.current = URL.createObjectURL(resumeFile);
        setFile(resumeFile);
      } else {
        console.warn("No existing resume found for this student.");
        setFile(null);
      }
    } catch (err) {
      console.error("Failed to fetch resume:", err);
      setFile(null);
    }
  }, [numericStudentId]);

  useEffect(() => {
    if (!validStudentId) {
      console.warn("No valid studentId provided, skipping resume fetch");
      return;
    }

    fetchResume();

    return () => {
      if (objectUrlRef.current) {
        URL.revokeObjectURL(objectUrlRef.current);
      }
    };
  }, [fetchResume, validStudentId]);

  const handleFileChange = (e) => {
    const selected = e.target.files[0];
    if (
      selected &&
      [
        "application/pdf",
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
      ].includes(selected.type)
    ) {
      setFile(selected);
      setMessage("");

      if (objectUrlRef.current) URL.revokeObjectURL(objectUrlRef.current);
      objectUrlRef.current = URL.createObjectURL(selected);
    } else {
      setFile(null);
      setMessage("Please upload a PDF or DOC/DOCX file.");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!file) {
      setMessage("No file selected.");
      return;
    }

    if (!validStudentId) {
      setMessage("Student ID is missing — cannot upload.");
      return;
    }

    const formData = new FormData();
    formData.append("resume", file);
    formData.append("studentId", numericStudentId);

    try {
      const response = await fetch("http://localhost:8080/uploadResume", {
        method: "PUT",
        body: formData,
      });

      if (response.ok) {
        setMessage("Resume uploaded successfully!");
        fetchResume(); // Refresh preview
      } else {
        setMessage("Failed to upload resume.");
      }
    } catch (error) {
      console.error("Upload error:", error);
      setMessage("An error occurred while uploading the resume.");
    }
  };

  const handleDownload = () => {
    if (!objectUrlRef.current) return;
    const link = document.createElement("a");
    link.href = objectUrlRef.current;
    link.download = file?.name || "resume";
    link.click();
  };

  return (
    <div className="max-w-lg mx-auto bg-white rounded-lg shadow p-6 mt-8">
      <h2 className="text-2xl font-semibold text-gray-800 mb-4">Upload Your Resume</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <label className="block text-gray-700 font-medium">
          Select File
          <input
            type="file"
            accept=".pdf,.doc,.docx"
            onChange={handleFileChange}
            className="mt-2 w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4
                       file:rounded-md file:border-0
                       file:text-sm file:font-semibold
                       file:bg-indigo-100 file:text-indigo-700
                       hover:file:bg-indigo-200
                       focus:outline-none focus:ring-2 focus:ring-indigo-400"
          />
        </label>

        {file && (
          <div className="flex items-center space-x-4">
            <p className="text-green-600 text-sm">
              Selected: <strong>{file.name}</strong>
            </p>
            <button
              type="button"
              onClick={handleDownload}
              className="text-indigo-600 hover:text-indigo-800 text-sm underline"
            >
              Download
            </button>
            <button
              type="button"
              onClick={() => window.open(objectUrlRef.current, "_blank")}
              className="text-indigo-600 hover:text-indigo-800 text-sm underline"
            >
              {file.type === "application/pdf" ? "View" : "Open"}
            </button>
          </div>
        )}

        {message && <p className="text-red-600 text-sm">{message}</p>}

        <button
          type="submit"
          className="w-full bg-indigo-600 text-white py-2 rounded-md hover:bg-indigo-700 transition"
        >
          Upload Resume
        </button>
      </form>

      {file && file.type === "application/pdf" && (
        <div className="mt-6">
          <object
            data={objectUrlRef.current}
            type="application/pdf"
            width="100%"
            height="400px"
            className="border"
          >
            <p>
              PDF preview not supported.{" "}
              <a href={objectUrlRef.current} download>
                Download PDF
              </a>{" "}
              to view.
            </p>
          </object>
        </div>
      )}
    </div>
  );
};

export default ResumeUpload;
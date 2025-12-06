import { useState, useEffect } from 'react';
import axios from 'axios';

const useFetchJobs = () => {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const controller = new AbortController(); // for request cancellation

    const fetchJobs = async () => {
      try {
        const response = await axios.get('http://localhost:8080/viewjobs', {
          signal: controller.signal,
        });
        setJobs(response.data);
      } catch (err) {
        if (axios.isCancel(err)) return; // ignore abort errors
        console.error('Error fetching jobs:', err);
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchJobs();

    // Cleanup on component unmount
    return () => controller.abort();
  }, []);

  return { jobs, loading, error, setJobs };
};

export default useFetchJobs;
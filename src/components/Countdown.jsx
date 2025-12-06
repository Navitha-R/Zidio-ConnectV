import { useMemo } from 'react';

const Countdown = ({ deadline }) => {
  const timeLeft = useMemo(() => {
    const difference = +new Date(deadline) - +new Date();
    if (difference <= 0) return null;

    return {
      days: Math.floor(difference / (1000 * 60 * 60 * 24)),
      hours: Math.floor((difference / (1000 * 60 * 60)) % 24),
      minutes: Math.floor((difference / 1000 / 60) % 60),
      seconds: Math.floor((difference / 1000) % 60),
    };
  }, [deadline]);

  return timeLeft
    ? <span className="font-semibold text-indigo-700">
        {timeLeft.days}d {timeLeft.hours}h {timeLeft.minutes}m {timeLeft.seconds}s
      </span>
    : <span className="text-red-600 font-semibold">Deadline Passed</span>;
};

export default Countdown;
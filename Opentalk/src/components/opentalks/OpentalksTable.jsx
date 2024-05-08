import React, { useState } from "react";
import { useEffect } from "react";
import { parseISO } from "date-fns";
import DateSlider from "../common/DateSlider";
import moment from "moment";

const OpentalksTable = ({ opentalkInfo, handleOpenTalkCancellation }) => {
  console.log("Props in OpentalksTable:", opentalkInfo);
  const [filteredOpentalks, setFilteredOpentalks] = useState(opentalkInfo);

  const filterOpentalks = (startTime, endTime) => {
    console.log(opentalkInfo);
    let filtered = opentalkInfo;
    if (startTime && endTime) {
      filtered = opentalkInfo.filter((opentalk) => {
        const opentalkStartTime = parseISO(opentalk.startTime);
        const opentalkEndTime = parseISO(opentalk.endTime);
        return (
          opentalkStartTime >= startTime &&
          opentalkEndTime <= endTime &&
          opentalkEndTime > startTime
        );
      });
    }
    setFilteredOpentalks(filtered);
  };

  useEffect(() => {
    console.log(opentalkInfo);
    setFilteredOpentalks(opentalkInfo);
  }, [opentalkInfo]);

  return (
    <section className="p-4">
      <DateSlider
        onDateChange={filterOpentalks}
        onFilterChange={filterOpentalks}
      />

      <table className="table table-bordered table-hover shadow">
        <thead>
          <tr>
            <th>S/N</th>
            <th>Open Talk ID</th>
            <th>Open Talk Topic</th>
            <th>Host Name</th>
            <th>Company Branch</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Participants</th>
            <th>LinkMeeting</th>
            <th>Slide</th>
            <th colSpan={2}>Actions</th>
          </tr>
        </thead>
        <tbody className="text-center">
          {filteredOpentalks?.map((opentalk, index) => (
            <tr key={opentalk?.id}>
              <td>{index + 1}</td>
              <td>{opentalk.id}</td>
              <td>{opentalk.topic}</td>
              <td>{opentalk.host?.name}</td>
              <td>{opentalk.companyBranchName}</td>
              <td>{opentalk.startTime}</td>
              <td>{opentalk.endTime}</td>
              <td>{opentalk.participants.length}</td>
              <td>{opentalk.linkMeeting}</td>
              {opentalk.slide ? (
                <td>
                  <a href={opentalk.slide} name={opentalk.topic}>
                    {opentalk.topic}
                  </a>
                </td>
              ) : (
                <td>No slide</td>
              )}

              <td>
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => handleOpenTalkCancellation(opentalk.id)}
                  disabled={moment(opentalk.startTime).isBefore(moment())}
                >
                  Cancel
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {filterOpentalks.length === 0 && (
        <p>No booking found for the selected date</p>
      )}
    </section>
  );
};

export default OpentalksTable;

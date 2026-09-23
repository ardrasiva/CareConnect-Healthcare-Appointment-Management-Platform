import {
    useEffect,
    useState
} from "react";

import api from "../api/axios";


function Appointments() {

    const [appointments, setAppointments] =
        useState([]);


    useEffect(() => {

        loadAppointments();

    }, []);

    
    const loadAppointments = async () => {

        try {

            const response =
                await api.get(
                    "/appointments"
                );

            setAppointments(
                response.data
            );

        } catch (error) {

            console.error(
                "Unable to load appointments.",
                error
            );

        }

    };


    // ADD handleCancel HERE
    const handleCancel = async (id) => {

        const confirmed =
            window.confirm(
                "Are you sure you want to cancel this appointment?"
            );

        if (!confirmed) {
            return;
        }

        try {

            await api.delete(
                `/appointments/${id}`
            );

            loadAppointments();

        } catch (error) {

            console.error(
                "Unable to cancel appointment.",
                error
            );

        }

    };



    const today =
        new Date()
            .toISOString()
            .split("T")[0];


    const upcomingAppointments =
        appointments.filter(
            appointment =>
                appointment.appointmentDate >= today
        );


    const pastAppointments =
        appointments.filter(
            appointment =>
                appointment.appointmentDate < today
        );


    return (

        <div>

            <h2 className="mb-4">
                My Appointments
            </h2>


            <h4 className="mb-3">
                Upcoming Appointments
            </h4>


            {upcomingAppointments.length === 0 && (

                <p>
                    No upcoming appointments.
                </p>

            )}


            {upcomingAppointments.map(
                appointment => (

                    <div
                        key={appointment.id}
                        className="card mb-3"
                    >

                        <div className="card-body">

                            <h5>
                                {
                                    appointment.doctor?.name
                                }
                            </h5>


                            <p>

                                Date:
                                {" "}

                                {
                                    appointment.appointmentDate
                                }

                            </p>


                            <p>

                                Time:
                                {" "}

                                {
                                    appointment.appointmentTime
                                }

                            </p>


                            <p>

                                Status:
                                {" "}

                                {
                                    appointment.status
                                }

                            </p>


                            {appointment.status ===
                                "BOOKED" && (

                                <button
                                    className="btn btn-danger"
                                    onClick={() =>
                                        handleCancel(
                                            appointment.id
                                        )
                                    }
                                >
                                    Cancel Appointment
                                </button>

                            )}

                        </div>

                    </div>

                )
            )}


            <h4 className="mb-3 mt-5">
                Past Appointments
            </h4>


            {pastAppointments.length === 0 && (

                <p>
                    No past appointments.
                </p>

            )}


            {pastAppointments.map(
                appointment => (

                    <div
                        key={appointment.id}
                        className="card mb-3"
                    >

                        <div className="card-body">

                            <h5>
                                {
                                    appointment.doctor?.name
                                }
                            </h5>


                            <p>

                                Date:
                                {" "}

                                {
                                    appointment.appointmentDate
                                }

                            </p>


                            <p>

                                Time:
                                {" "}

                                {
                                    appointment.appointmentTime
                                }

                            </p>


                            <p>

                                Status:
                                {" "}

                                {
                                    appointment.status
                                }

                            </p>

                        </div>

                    </div>

                )
            )}

        </div>
    );
}


export default Appointments;
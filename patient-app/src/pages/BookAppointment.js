import {
    useEffect,
    useState
} from "react";

import {
    useNavigate
} from "react-router-dom";

import api from "../api/axios";
function BookAppointment() {

    const [doctors, setDoctors] =
        useState([]);


    const [doctorId, setDoctorId] =
        useState("");


    const [appointmentDate, setAppointmentDate] =
        useState("");


    const [appointmentTime, setAppointmentTime] =
        useState("");


    const [error, setError] =
        useState("");


    const navigate =
        useNavigate();


    useEffect(() => {

        loadDoctors();

    }, []);

const handleSubmit = async (event) => {

    event.preventDefault();

    setError("");


    try {

        await api.post(
            "/appointments",
            {
                doctorId:
                    Number(doctorId),

                appointmentDate:
                    appointmentDate,

                appointmentTime:
                    appointmentTime
            }
        );


        navigate(
            "/appointments"
        );

    } catch (error) {

        setError(
            error.response?.data?.message
            ||
            "Unable to book appointment."
        );

    }

};
    const loadDoctors = async () => {

        try {

            const response =
                await api.get(
                    "/doctors"
                );

            setDoctors(
                response.data
            );

        } catch (error) {

            console.error(
                "Unable to load doctors.",
                error
            );

        }

    };


    return (

    <div className="row justify-content-center">

        <div className="col-md-6">

            <h2 className="mb-4">
                Book Appointment
            </h2>


            {error && (

                <div className="alert alert-danger">

                    {error}

                </div>

            )}


            <form
                onSubmit={handleSubmit}
            >

                <div className="form-group">

                    <label>
                        Doctor
                    </label>


                    <select
                        className="form-control"
                        value={doctorId}
                        onChange={
                            event =>
                                setDoctorId(
                                    event.target.value
                                )
                        }
                        required
                    >

                        <option value="">
                            Select Doctor
                        </option>


                        {doctors.map(
                            doctor => (

                                <option
                                    key={doctor.id}
                                    value={doctor.id}
                                >

                                    {doctor.name}
                                    {" - "}
                                    {
                                        doctor.specialization
                                    }

                                </option>

                            )
                        )}

                    </select>

                </div>


                <div className="form-group">

                    <label>
                        Appointment Date
                    </label>


                    <input
                        type="date"
                        className="form-control"
                        value={appointmentDate}
                        onChange={
                            event =>
                                setAppointmentDate(
                                    event.target.value
                                )
                        }
                        required
                    />

                </div>


                <div className="form-group">

                    <label>
                        Appointment Time
                    </label>


                    <input
                        type="time"
                        className="form-control"
                        value={appointmentTime}
                        onChange={
                            event =>
                                setAppointmentTime(
                                    event.target.value
                                )
                        }
                        required
                    />

                </div>


                <button
                    type="submit"
                    className="btn btn-primary"
                >
                    Book Appointment
                </button>

            </form>

        </div>

    </div>
);
}


export default BookAppointment;
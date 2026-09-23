import {
    useEffect,
    useState
} from "react";

import api from "../api/axios";
import DoctorCard
    from "../components/DoctorCard";
function DoctorList() {

    const [doctors, setDoctors] =
        useState([]);

    useEffect(() => {

        loadDoctors();

    }, []);

    const loadDoctors = async () => {

        const response =
            await api.get("/doctors");

        setDoctors(response.data);
    };

    return (

        <div>

            <h2 className="mb-4">
                Our Doctors
            </h2>

            <div className="row">

                {doctors.map(
    doctor => (

        <div
            className="col-md-4 mb-4"
            key={doctor.id}
        >

            <DoctorCard
                doctor={doctor}
            />

        </div>

    )
)}

            </div>

        </div>
    );
}

export default DoctorList;
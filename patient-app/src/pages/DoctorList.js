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


    const [search, setSearch] =
        useState("");


    useEffect(() => {

        loadDoctors();

    }, []);


    const loadDoctors = async () => {

        const response =
            await api.get("/doctors");

        setDoctors(response.data);

    };


    const filteredDoctors =
        doctors.filter(
            doctor =>

                doctor.name
                    .toLowerCase()
                    .includes(
                        search.toLowerCase()
                    )

                ||

                doctor.specialization
                    .toLowerCase()
                    .includes(
                        search.toLowerCase()
                    )
        );


    return (

        <div>

            <h2 className="mb-4">
                Our Doctors
            </h2>


            {/* SEARCH BAR */}

            <div className="mb-4">

                <input
                    type="text"
                    className="form-control"
                    placeholder="Search doctors by name or specialization"
                    value={search}
                    onChange={
                        event =>
                            setSearch(
                                event.target.value
                            )
                    }
                />

            </div>


            {/* DOCTORS */}

            <div className="row">

                {filteredDoctors.map(
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


            {/* NO RESULTS */}

            {filteredDoctors.length === 0 && (

                <p className="text-center mt-4">

                    No doctors found.

                </p>

            )}

        </div>
    );
}


export default DoctorList;
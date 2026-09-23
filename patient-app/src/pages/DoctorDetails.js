import {
    useEffect,
    useState
} from "react";

import {
    useParams
} from "react-router-dom";

import api from "../api/axios";


function DoctorDetails() {

    const { id } = useParams();

    const [doctor, setDoctor] =
        useState(null);

    useEffect(() => {

        loadDoctor();

    }, [id]);

    const loadDoctor = async () => {

        const response =
            await api.get(
                `/doctors/${id}`
            );

        setDoctor(response.data);
    };

    if (!doctor) {

        return (
            <p>
                Loading doctor details...
            </p>
        );
    }

    return (

        <div>

            <h2>
                {doctor.name}
            </h2>

            <p>

                <strong>
                    Specialization:
                </strong>

                {" "}

                {doctor.specialization}

            </p>

            <p>

                <strong>
                    Qualification:
                </strong>

                {" "}

                {doctor.qualification}

            </p>

            <p>

                <strong>
                    Experience:
                </strong>

                {" "}

                {doctor.experience}
                {" "}
                years

            </p>

            <p>

                <strong>
                    Consultation Fee:
                </strong>

                {" ₹"}

                {doctor.consultationFee}

            </p>

        </div>
    );
}

export default DoctorDetails;
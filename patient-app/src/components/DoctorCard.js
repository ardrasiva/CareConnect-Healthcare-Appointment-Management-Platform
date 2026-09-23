import {
    Link
} from "react-router-dom";


function DoctorCard({ doctor }) {

    return (

        <div className="card h-100">

            <div className="card-body">

                <h5 className="card-title">
                    {doctor.name}
                </h5>

                <p className="card-text">
                    {doctor.specialization}
                </p>

                <p className="card-text">
                    {doctor.qualification}
                </p>

                <p className="card-text">
                    Experience:
                    {" "}
                    {doctor.experience}
                    {" "}
                    years
                </p>

                <Link
                    to={`/doctors/${doctor.id}`}
                    className="btn btn-primary"
                >
                    View Details
                </Link>

            </div>

        </div>
    );
}

export default DoctorCard;
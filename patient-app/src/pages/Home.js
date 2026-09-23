import {
    Link
} from "react-router-dom";


function Home() {

    return (

        <div>

            <div className="jumbotron">

                <h1 className="display-4">
                    Hospital Booking
                </h1>

                <p className="lead">
                    Find doctors and manage
                    your appointments easily.
                </p>

                <hr className="my-4" />

                <p>
                    Browse our doctors and
                    book an appointment.
                </p>

                <Link
                    to="/doctors"
                    className="btn btn-primary"
                >
                    View Doctors
                </Link>

            </div>

        </div>
    );
}

export default Home;
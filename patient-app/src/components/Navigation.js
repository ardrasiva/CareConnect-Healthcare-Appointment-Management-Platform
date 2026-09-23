import {
    Link,
    useNavigate
} from "react-router-dom";

import {
    useSelector,
    useDispatch
} from "react-redux";

import api from "../api/axios";
function Navigation() {

    const isLoggedIn =
        useSelector(
            state =>
                state.auth.isLoggedIn
        );


    const dispatch =
        useDispatch();


    const navigate =
        useNavigate();


    const handleLogout = async () => {

    try {

        await api.post("/logout");

    } catch (error) {

        console.error(
            "Logout request failed.",
            error
        );

    }


    localStorage.removeItem(
        "token"
    );


    dispatch({

        type: "LOGOUT"

    });


    navigate("/login");

};


    return (

        <nav className="navbar navbar-expand-lg navbar-light bg-light">

            <div className="container">

                <Link
                    to="/"
                    className="navbar-brand"
                >
                    CareConnect
                </Link>


                <div className="navbar-nav">

                    <Link
                        to="/"
                        className="nav-link"
                    >
                        Home
                    </Link>


                    <Link
                        to="/doctors"
                        className="nav-link"
                    >
                        Doctors
                    </Link>


                    {!isLoggedIn && (

                        <>
                            <Link
                                to="/login"
                                className="nav-link"
                            >
                                Login
                            </Link>

                            <Link
                                to="/register"
                                className="nav-link"
                            >
                                Register
                            </Link>
                        </>

                    )}


                    {isLoggedIn && (

                        <>
                            <Link
                                to="/appointments"
                                className="nav-link"
                            >
                                Appointments
                            </Link>


                            <Link
                                to="/change-password"
                                className="nav-link"
                            >
                                Change Password
                            </Link>


                            <button
                                className="btn btn-link nav-link"
                                onClick={
                                    handleLogout
                                }
                            >
                                Logout
                            </button>
                        </>

                    )}

                </div>

            </div>

        </nav>
    );
}


export default Navigation;
import {
    NavLink,
    useNavigate
} from "react-router-dom";

import {
    useSelector,
    useDispatch
} from "react-redux";

import api from "../api/axios";

import "./Navigation.css";


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

        <nav className="patient-navbar">

            <div className="patient-navbar-container">


                {/* LOGO */}

                <NavLink
                    to="/"
                    className="patient-logo"
                >


                    <span>
                        Care<span>Connect</span>
                    </span>

                </NavLink>


                {/* NAVIGATION */}

                <div className="patient-nav-links">


                    {/* HOME */}

                    <NavLink
                        to="/"
                        end
                        className={({ isActive }) =>
                            isActive
                                ? "patient-nav-link active"
                                : "patient-nav-link"
                        }
                    >
                        Home
                    </NavLink>


                    {/* DOCTORS */}

                    <NavLink
                        to="/doctors"
                        className={({ isActive }) =>
                            isActive
                                ? "patient-nav-link active"
                                : "patient-nav-link"
                        }
                    >
                        Doctors
                    </NavLink>


                    {/* LOGGED OUT */}

                    {!isLoggedIn && (

                        <>

                            <NavLink
                                to="/login"
                                className={({ isActive }) =>
                                    isActive
                                        ? "patient-nav-link active"
                                        : "patient-nav-link"
                                }
                            >
                                Login
                            </NavLink>


                            <NavLink
                                to="/register"
                                className={({ isActive }) =>
                                    isActive
                                        ? "patient-register active"
                                        : "patient-register"
                                }
                            >
                                Register
                            </NavLink>

                        </>

                    )}


                    {/* LOGGED IN */}

                    {isLoggedIn && (

                        <>

                            <NavLink
                                to="/appointments"
                                className={({ isActive }) =>
                                    isActive
                                        ? "patient-nav-link active"
                                        : "patient-nav-link"
                                }
                            >
                                Appointments
                            </NavLink>


                            <NavLink
                                to="/appointments/book"
                                className={({ isActive }) =>
                                    isActive
                                        ? "patient-nav-link active"
                                        : "patient-nav-link"
                                }
                            >
                                Book Appointment
                            </NavLink>


                            <NavLink
                                to="/change-password"
                                className={({ isActive }) =>
                                    isActive
                                        ? "patient-nav-link active"
                                        : "patient-nav-link"
                                }
                            >
                                Change Password
                            </NavLink>


                            <button
                                className="patient-logout"
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
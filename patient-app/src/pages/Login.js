import { useState } from "react";

import {
    useDispatch
} from "react-redux";

import {
    useNavigate
} from "react-router-dom";

import api from "../api/axios";

import "./Login.css";


function Login() {

    const [email, setEmail] =
        useState("");

    const [password, setPassword] =
        useState("");

    const [error, setError] =
        useState("");

    const [showPassword, setShowPassword] =
        useState(false);

    const dispatch =
        useDispatch();

    const navigate =
        useNavigate();


    const handleSubmit = async (event) => {

        event.preventDefault();

        setError("");

        try {

            const response =
                await api.post(
                    "/login",
                    {
                        email,
                        password
                    }
                );

            const token =
                response.data.token;


            localStorage.setItem(
                "token",
                token
            );


            dispatch({

                type: "LOGIN",

                payload: token

            });


            navigate(
                "/appointments"
            );

        } catch (error) {

            setError(
                error.response?.data?.message
                ||
                "Invalid email or password."
            );

        }

    };


    return (

        <div className="login-page">

            <div className="login-card">


                {/* LEFT SIDE */}

                <div className="login-image">

                    <div className="login-image-overlay">

                        <div className="login-image-content">

                            

                            <h2>
                                CareConnect
                            </h2>

                            <p>
                                Making healthcare simpler,
                                one appointment at a time.
                            </p>

                        </div>

                    </div>

                </div>


                {/* RIGHT SIDE */}

                <div className="login-form-section">

                    <div className="login-form-container">


                        <div className="login-top-icon">

                            <i className="bi bi-heart-pulse"></i>

                        </div>


                        <p className="login-welcome">
                            WELCOME BACK
                        </p>


                        <h1>
                            Login
                        </h1>


                        <p className="login-description">
                            Sign in to continue to CareConnect
                        </p>


                        {/* ERROR */}

                        {error && (

                            <div className="login-error">

                                <i className="bi bi-exclamation-circle"></i>

                                <span>
                                    {error}
                                </span>

                            </div>

                        )}


                        <form
                            onSubmit={handleSubmit}
                        >


                            {/* EMAIL */}

                            <div className="login-field">

                                <label>
                                    Email
                                </label>

                                <div className="login-input">

                                    <i className="bi bi-envelope"></i>

                                    <input
                                        type="email"
                                        value={email}
                                        onChange={
                                            event =>
                                                setEmail(
                                                    event.target.value
                                                )
                                        }
                                        placeholder="Enter your email"
                                        required
                                    />

                                </div>

                            </div>


                            {/* PASSWORD */}

                            <div className="login-field">

                                <label>
                                    Password
                                </label>

                                <div className="login-input">

                                    <i className="bi bi-lock"></i>

                                    <input
                                        type={
                                            showPassword
                                                ? "text"
                                                : "password"
                                        }
                                        value={password}
                                        onChange={
                                            event =>
                                                setPassword(
                                                    event.target.value
                                                )
                                        }
                                        placeholder="Enter your password"
                                        required
                                    />


                                    <button
                                        type="button"
                                        className="password-eye"
                                        onClick={() =>
                                            setShowPassword(
                                                !showPassword
                                            )
                                        }
                                    >

                                        <i
                                            className={
                                                showPassword
                                                    ? "bi bi-eye-slash"
                                                    : "bi bi-eye"
                                            }
                                        ></i>

                                    </button>

                                </div>

                            </div>


                            {/* LOGIN BUTTON */}

                            <button
                                type="submit"
                                className="login-button"
                            >

                                Login

                                <i className="bi bi-arrow-right"></i>

                            </button>


                        </form>


                        <p className="login-register-text">

                            Don't have an account?

                            <a href="/register">
                                Create an account
                            </a>

                        </p>


                    </div>

                </div>

            </div>

        </div>

    );
}


export default Login;
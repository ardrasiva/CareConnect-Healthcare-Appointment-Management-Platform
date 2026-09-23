import { useState } from "react";

import {
    useDispatch
} from "react-redux";

import {
    useNavigate
} from "react-router-dom";

import api from "../api/axios";


function Login() {

    const [email, setEmail] =
        useState("");

    const [password, setPassword] =
        useState("");

    const [error, setError] =
        useState("");


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

        <div className="row justify-content-center">

            <div className="col-md-5">

                <h2 className="mb-4">
                    Login
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
                            Email
                        </label>

                        <input
                            type="email"
                            className="form-control"
                            value={email}
                            onChange={
                                event =>
                                    setEmail(
                                        event.target.value
                                    )
                            }
                            required
                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Password
                        </label>

                        <input
                            type="password"
                            className="form-control"
                            value={password}
                            onChange={
                                event =>
                                    setPassword(
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
                        Login
                    </button>

                </form>

            </div>

        </div>

    );
}


export default Login;
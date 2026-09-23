import { useState } from "react";

import api from "../api/axios";


function Register() {

    const [form, setForm] = useState({

        firstName: "",
        lastName: "",
        email: "",
        password: "",
        phoneNumber: "",
        dateOfBirth: "",
        gender: "",
        address: ""

    });


    const [message, setMessage] =
        useState("");

    const [error, setError] =
        useState("");


    const handleChange = (event) => {

        setForm({

            ...form,

            [event.target.name]:
                event.target.value

        });

    };


    const handleSubmit = async (event) => {

        event.preventDefault();

        setMessage("");
        setError("");

        try {

            const response =
                await api.post(
                    "/register",
                    form
                );

            setMessage(
                response.data.message
            );

        } catch (error) {

            setError(
                error.response?.data?.message
                ||
                "Registration failed."
            );

        }

    };


    return (

        <div className="row justify-content-center">

            <div className="col-md-7">

                <h2 className="mb-4">
                    Create Account
                </h2>


                {message && (

                    <div className="alert alert-success">

                        {message}

                    </div>

                )}


                {error && (

                    <div className="alert alert-danger">

                        {error}

                    </div>

                )}


                <form
                    onSubmit={handleSubmit}
                >

                    <div className="form-row">

                        <div className="form-group col-md-6">

                            <label>
                                First Name
                            </label>

                            <input
                                type="text"
                                name="firstName"
                                className="form-control"
                                value={
                                    form.firstName
                                }
                                onChange={
                                    handleChange
                                }
                                required
                            />

                        </div>


                        <div className="form-group col-md-6">

                            <label>
                                Last Name
                            </label>

                            <input
                                type="text"
                                name="lastName"
                                className="form-control"
                                value={
                                    form.lastName
                                }
                                onChange={
                                    handleChange
                                }
                                required
                            />

                        </div>

                    </div>


                    <div className="form-group">

                        <label>
                            Email
                        </label>

                        <input
                            type="email"
                            name="email"
                            className="form-control"
                            value={
                                form.email
                            }
                            onChange={
                                handleChange
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
                            name="password"
                            className="form-control"
                            value={
                                form.password
                            }
                            onChange={
                                handleChange
                            }
                            required
                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Phone Number
                        </label>

                        <input
                            type="text"
                            name="phoneNumber"
                            className="form-control"
                            value={
                                form.phoneNumber
                            }
                            onChange={
                                handleChange
                            }
                            required
                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Date of Birth
                        </label>

                        <input
                            type="date"
                            name="dateOfBirth"
                            className="form-control"
                            value={
                                form.dateOfBirth
                            }
                            onChange={
                                handleChange
                            }
                            required
                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Gender
                        </label>

                        <select
                            name="gender"
                            className="form-control"
                            value={
                                form.gender
                            }
                            onChange={
                                handleChange
                            }
                            required
                        >

                            <option value="">
                                Select
                            </option>

                            <option value="Male">
                                Male
                            </option>

                            <option value="Female">
                                Female
                            </option>

                        </select>

                    </div>


                    <div className="form-group">

                        <label>
                            Address
                        </label>

                        <textarea
                            name="address"
                            className="form-control"
                            value={
                                form.address
                            }
                            onChange={
                                handleChange
                            }
                            required
                        />

                    </div>


                    <button
                        type="submit"
                        className="btn btn-primary"
                    >
                        Register
                    </button>

                </form>

            </div>

        </div>

    );
}


export default Register;
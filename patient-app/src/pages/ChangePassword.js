import {
    useState
} from "react";

import api from "../api/axios";


function ChangePassword() {

    const [
        currentPassword,
        setCurrentPassword
    ] = useState("");


    const [
        newPassword,
        setNewPassword
    ] = useState("");


    const [
        confirmPassword,
        setConfirmPassword
    ] = useState("");


    const [error, setError] =
        useState("");


    const [message, setMessage] =
        useState("");


    // Password visibility states

    const [
        showCurrentPassword,
        setShowCurrentPassword
    ] = useState(false);


    const [
        showNewPassword,
        setShowNewPassword
    ] = useState(false);


    const [
        showConfirmPassword,
        setShowConfirmPassword
    ] = useState(false);


    const handleSubmit = async (event) => {

        event.preventDefault();

        setError("");

        setMessage("");


        if (
            newPassword !==
            confirmPassword
        ) {

            setError(
                "New passwords do not match."
            );

            return;
        }


        try {

            const response =
                await api.put(
                    "/change-password",
                    {
                        currentPassword:
                            currentPassword,

                        newPassword:
                            newPassword
                    }
                );


            setMessage(
                response.data.message
                ||
                "Password updated successfully."
            );


            setCurrentPassword("");

            setNewPassword("");

            setConfirmPassword("");


        } catch (error) {

            setError(
                error.response?.data?.message
                ||
                "Unable to update password."
            );

        }

    };


    return (

        <div className="row justify-content-center">

            <div className="col-md-6">

                <h2 className="mb-4">
                    Change Password
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


                    {/* CURRENT PASSWORD */}

                    <div className="form-group mb-3">

                        <label>
                            Current Password
                        </label>

                        <div className="input-group">

                            <input
                                type={
                                    showCurrentPassword
                                        ? "text"
                                        : "password"
                                }
                                className="form-control"
                                value={
                                    currentPassword
                                }
                                onChange={
                                    event =>
                                        setCurrentPassword(
                                            event.target.value
                                        )
                                }
                                required
                            />

                            <button
                                type="button"
                                className="btn btn-outline-secondary"
                                onClick={() =>
                                    setShowCurrentPassword(
                                        !showCurrentPassword
                                    )
                                }
                            >

                                <i
                                    className={
                                        showCurrentPassword
                                            ? "bi bi-eye-slash"
                                            : "bi bi-eye"
                                    }
                                >
                                </i>

                            </button>

                        </div>

                    </div>


                    {/* NEW PASSWORD */}

                    <div className="form-group mb-3">

                        <label>
                            New Password
                        </label>

                        <div className="input-group">

                            <input
                                type={
                                    showNewPassword
                                        ? "text"
                                        : "password"
                                }
                                className="form-control"
                                value={
                                    newPassword
                                }
                                onChange={
                                    event =>
                                        setNewPassword(
                                            event.target.value
                                        )
                                }
                                required
                            />

                            <button
                                type="button"
                                className="btn btn-outline-secondary"
                                onClick={() =>
                                    setShowNewPassword(
                                        !showNewPassword
                                    )
                                }
                            >

                                <i
                                    className={
                                        showNewPassword
                                            ? "bi bi-eye-slash"
                                            : "bi bi-eye"
                                    }
                                >
                                </i>

                            </button>

                        </div>

                    </div>


                    {/* CONFIRM PASSWORD */}

                    <div className="form-group mb-3">

                        <label>
                            Confirm New Password
                        </label>

                        <div className="input-group">

                            <input
                                type={
                                    showConfirmPassword
                                        ? "text"
                                        : "password"
                                }
                                className="form-control"
                                value={
                                    confirmPassword
                                }
                                onChange={
                                    event =>
                                        setConfirmPassword(
                                            event.target.value
                                        )
                                }
                                required
                            />

                            <button
                                type="button"
                                className="btn btn-outline-secondary"
                                onClick={() =>
                                    setShowConfirmPassword(
                                        !showConfirmPassword
                                    )
                                }
                            >

                                <i
                                    className={
                                        showConfirmPassword
                                            ? "bi bi-eye-slash"
                                            : "bi bi-eye"
                                    }
                                >
                                </i>

                            </button>

                        </div>

                    </div>


                    {/* SUBMIT BUTTON */}

                    <button
                        type="submit"
                        className="btn btn-primary"
                    >
                        Change Password
                    </button>


                </form>

            </div>

        </div>

    );
}


export default ChangePassword;
import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";

function CheckAuth({ children }) {

    const isLoggedIn =
        useSelector(
            state => state.auth.isLoggedIn
        );

    const isLoading =
        useSelector(
            state => state.auth.isLoading
        );

    if (isLoading) {

        return (
            <div className="text-center mt-5">
                Checking login...
            </div>
        );

    }

    if (!isLoggedIn) {

        return (
            <Navigate
                to="/login"
                replace
            />
        );

    }

    return children;
}

export default CheckAuth;
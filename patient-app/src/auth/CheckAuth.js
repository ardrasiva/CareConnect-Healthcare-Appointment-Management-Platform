import {
    Navigate
} from "react-router-dom";

import {
    useSelector
} from "react-redux";


function CheckAuth({ children }) {

    const isLoggedIn =
        useSelector(
            state =>
                state.auth.isLoggedIn
        );


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
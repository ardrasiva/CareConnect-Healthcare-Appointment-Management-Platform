import {
    useEffect
} from "react";

import {
    useDispatch
} from "react-redux";


function AutoLogin() {

    const dispatch =
        useDispatch();


    useEffect(() => {

        const token =
            localStorage.getItem(
                "token"
            );


        if (token) {

            dispatch({

                type: "LOGIN",

                payload: token

            });

        }

    }, [dispatch]);


    return null;

}


export default AutoLogin;
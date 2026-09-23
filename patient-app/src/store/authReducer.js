const initialState = {

    token: null,

    isLoggedIn: false

};


function authReducer(
    state = initialState,
    action
) {

    switch (action.type) {

        case "LOGIN":

            return {

                token: action.payload,

                isLoggedIn: true

            };


        case "LOGOUT":

            return {

                token: null,

                isLoggedIn: false

            };


        default:

            return state;

    }

}


export default authReducer; 
const initialState = {
    token: null,
    isLoggedIn: false,
    isLoading: true
};

function authReducer(
    state = initialState,
    action
) {
    switch (action.type) {

        case "LOGIN":
            return {
                token: action.payload,
                isLoggedIn: true,
                isLoading: false
            };

        case "LOGOUT":
            return {
                token: null,
                isLoggedIn: false,
                isLoading: false
            };

        case "AUTH_CHECKED":
            return {
                ...state,
                isLoading: false
            };

        default:
            return state;
    }
}

export default authReducer;
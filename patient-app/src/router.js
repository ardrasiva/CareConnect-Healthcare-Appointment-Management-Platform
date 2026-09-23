import {
    createBrowserRouter
} from "react-router-dom";
import CheckAuth from "./auth/CheckAuth";
import App from "./App";
import Appointments from "./pages/Appointments";
import Home
    from "./pages/Home";

import DoctorList
    from "./pages/DoctorList";

import Register
    from "./pages/Register";

import Login
    from "./pages/Login";

import DoctorDetails
    from "./pages/DoctorDetails";
const router =
    createBrowserRouter([

        {
            path: "",

            element: <App />,

            children: [

    {
        path: "",
        element: <Home />
    },

    {
        path: "doctors",
        element: <DoctorList />
    },

    {
        path: "doctors/:id",
        element: <DoctorDetails />
    },

    {
        path: "register",
        element: <Register />
    },

    {
        path: "login",
        element: <Login />
    },

    {
        path: "appointments",
        element: (
            <CheckAuth>
                <Appointments />
            </CheckAuth>
        )
    }

]
        }

    ]);


export default router;
import {
    createBrowserRouter
} from "react-router-dom";
import CheckAuth from "./auth/CheckAuth";
import App from "./App";
import Appointments from "./pages/Appointments";
import ChangePassword
    from "./pages/ChangePassword";
import Home
    from "./pages/Home";
import BookAppointment
    from "./pages/BookAppointment";
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
    },
    {
    path: "appointments/book",

    element: (
        <CheckAuth>
            <BookAppointment />
        </CheckAuth>
    )
},
{
    path: "change-password",

    element: (
        <CheckAuth>
            <ChangePassword />
        </CheckAuth>
    )
}

]
        }

    ]);


export default router;
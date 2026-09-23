import {
    Outlet
} from "react-router-dom";

import Header
    from "./components/Header";

import Navigation
    from "./components/Navigation";

import Footer
    from "./components/Footer";

import AutoLogin
    from "./components/AutoLogin";


function App() {

    return (

        <>

            <AutoLogin />

            <div className="d-flex flex-column min-vh-100">

                <Header />

                <Navigation />

                <main className="container py-4 flex-grow-1">

                    <Outlet />

                </main>

                <Footer />

            </div>

        </>

    );
}


export default App;
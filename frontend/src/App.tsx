import {Box} from "@mui/material";
import {Route, Routes} from "react-router-dom";
import NavBar from "./components/NavBar";
import SideNav from "./components/SideNav";
import {useLayoutSelector} from "./redux/selector.ts";
import routes from "./routes/routes.tsx";
import {RouteType} from "./routes/RouteType.ts";
import { ToastContainer} from 'react-toastify';

function App() {
    const {slim} = useLayoutSelector();
    return (
        <Box className='app'>
            <NavBar/>
            <SideNav isSlim={slim}/>
            <div className='app__content'>
                <Routes>
                    {routes.map((route) => route.type == RouteType.COLLAPSE ? (route.subItems?.map((i) => <Route path={i.path} element={i.element} key={i.key}/>)): (<Route path={route.path} element={route.element} key={route.key}/>))}
                </Routes>
            </div>
            <ToastContainer position={"top-right"}/>
        </Box>
    )
}

export default App

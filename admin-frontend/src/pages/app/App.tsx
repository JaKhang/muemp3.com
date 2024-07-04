import "./App.css";
import HeadBar from "@layout/HeadBar";
import SideBar from "@layout/Sidebar";
import { Box, Icon } from "@mui/material";
import { Route, Routes } from "react-router-dom";
import ThemeCustomization from "@themes/index";
import routes from "@routes/index";
import AuthorizationFilter from "../../filters/AuthorizationFilter";
import { Role } from "@constants/UserRole";

function App() {
  return (
    <AuthorizationFilter isAuthenticated roles={[Role.ADMIN]}>
      <div className="app__wrapper">
        <HeadBar />
        <Box
          sx={{
            gridArea: "main-content",
            padding: 2,
            overflow: "auto"
          }}>
          <Routes>
            {routes.map((route) => (
              <Route
                path={route.path}
                element={route.element}
                key={route.path}
              />
            ))}
          </Routes>
        </Box>
        <SideBar />
      </div>
    </AuthorizationFilter>
  );
}

export default App;

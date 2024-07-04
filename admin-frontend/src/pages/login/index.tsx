import React, { StrictMode } from "react";
import ReactDOM from "react-dom/client";
import { Provider } from "react-redux";
import { BrowserRouter } from "react-router-dom";
import Login from "./Login";
import ThemeCustomization from "@themes/index";
import store from "@redux/store";
import { SnackbarProvider } from "notistack";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <BrowserRouter>
    <Provider store={store}>
      <StrictMode>
        <ThemeCustomization>
          <SnackbarProvider autoHideDuration={1000}>
            <Login />
          </SnackbarProvider>
        </ThemeCustomization>
      </StrictMode>
    </Provider>
  </BrowserRouter>
);

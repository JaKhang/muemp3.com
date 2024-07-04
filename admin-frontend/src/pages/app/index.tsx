import React, { StrictMode } from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { Provider } from "react-redux";
import { BrowserRouter } from "react-router-dom";
import ThemeCustomization from "@themes/index.tsx";
import store from "@redux/store.ts";
import { SnackbarProvider } from "notistack";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <BrowserRouter>
    <Provider store={store}>
      {/* <StrictMode> */}
      <ThemeCustomization>
        <SnackbarProvider autoHideDuration={1000}>
          <App />
        </SnackbarProvider>
      </ThemeCustomization>
      {/* </StrictMode> */}
    </Provider>
  </BrowserRouter>
);

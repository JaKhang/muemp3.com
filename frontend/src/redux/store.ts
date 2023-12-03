import {configureStore} from "@reduxjs/toolkit";
import {TypedUseSelectorHook, useDispatch, useSelector} from "react-redux";
import {layoutSlice} from "../features/layout";


const store = configureStore({
    reducer: {
        layout: layoutSlice.reducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware(),
})
export type RootState = ReturnType<typeof store.getState>;
export const useAppDispatch: () => typeof store.dispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
export default store
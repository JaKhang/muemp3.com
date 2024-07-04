import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import store from "./store";

export type RootState = ReturnType<typeof store.getState>;
export const useAppDispatch: () => typeof store.dispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;

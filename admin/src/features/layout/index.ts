import {createSlice} from "@reduxjs/toolkit";
import {Key} from "../../constants";

interface LayoutState{
    slim: boolean
}

const slim = localStorage.getItem(Key.SLIM);


const initialState : LayoutState = {
    slim : Boolean(slim),
}
export const layoutSlice = createSlice({
    name: "layout",
    initialState,
    reducers: {
        setSlim(state){
            state.slim = !state.slim
            localStorage.setItem(Key.SLIM, String(state.slim))
        }
    },
    extraReducers: {}
})
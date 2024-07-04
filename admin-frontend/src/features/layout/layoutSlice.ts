import { Key } from "@constants/Key";
import {PayloadAction, createSlice} from "@reduxjs/toolkit";

interface LayoutState{
    slim: boolean
    openItems: string[],
    defaultId: string,
}

let slim = localStorage.getItem(Key.SLIM);

const initialState : LayoutState = {
    slim : slim == 'true',
    openItems: ['dashboard'],
    defaultId: 'dashboard',
}

 const layoutSlice = createSlice({
    name: "layout",
    initialState,
    reducers: {
        setSlim(state, action: PayloadAction<boolean>){
            state.slim = action.payload
            //@ts-ignore
            localStorage.setItem(Key.SLIM, action.payload)
        },
        activeItem(state, action: PayloadAction<string[]>){
            state.openItems = action.payload
        }
    },
    extraReducers: {}
})

export default layoutSlice;
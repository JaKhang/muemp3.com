import {useAppSelector} from "./store";


export const useLayoutSelector = () => {
    return useAppSelector(state => state.layout)
}


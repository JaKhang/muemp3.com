import { useAppSelector } from "."



export const useLayoutSelector = () => {
    return useAppSelector(state => state.layout)
}




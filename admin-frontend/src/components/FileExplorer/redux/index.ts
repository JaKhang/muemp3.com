import explorerSlice from "./explorerSlice";
import { useAppSelector } from "./store";

export const useExplorerAction = () => explorerSlice.actions;

export const useExplorerSelector = () =>
  useAppSelector((state) => state.explorer);

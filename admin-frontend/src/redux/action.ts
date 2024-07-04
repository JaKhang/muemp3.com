import authSlice from "@features/auth/authSlice";
import layoutSlice from "../features/layout/layoutSlice";

export const useLayoutAction = () => {
  return layoutSlice.actions;
};

export const useAuthAction = () => {
  return authSlice.actions;
};

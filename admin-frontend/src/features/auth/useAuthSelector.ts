import { useAppSelector } from "@redux/store.ts";

export default () => {
  return useAppSelector((state) => state.auth);
};

import { Role } from "@constants/UserRole";
import { useEffect, useState } from "react";
import useAuthSelector from "@features/auth/useAuthSelector";
import { Key } from "@constants/Key";
import { useAppDispatch } from "@redux/store";
import { localWithAccessToken } from "@features/auth/authSlice";
import Loader from "../components/Loader";
import ErrorResponse from "@models/ErrorResponse";

interface AuthorizationFilterProps {
  roles: Role[];
  isAuthenticated: boolean;
  children?: JSX.Element;
}

const AuthorizationFilter = (props: AuthorizationFilterProps) => {
  const { principle } = useAuthSelector();
  const { isAuthenticated, children, roles } = props;
  const dispatch = useAppDispatch();

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!principle) {
      const token = localStorage.getItem(Key.ACCESS_TOKEN);
      if (token)
        dispatch(localWithAccessToken(token))
          .unwrap()
          .then((principle) => {
            if (!roles.includes(principle.role)) {
              localStorage.setItem(
                Key.MESSAGE,
                JSON.stringify({
                  type: "error",
                  message: "Permission denied",
                })
              );
              location.replace("/login");
            }
          })
          .catch((e: ErrorResponse) => {
            localStorage.setItem(Key.ERROR, JSON.stringify(e));
            location.replace("/login");
          })
          .finally(() => {
            setLoading(false);
          });
      else if (isAuthenticated) {
        localStorage.setItem(
          Key.MESSAGE,
          JSON.stringify({ type: "error", message: "Must be Authentication" })
        );
        location.replace("/login");
      }
    }
  }, []);

  return <>{loading ? <Loader /> : children}</>;
};

export default AuthorizationFilter;

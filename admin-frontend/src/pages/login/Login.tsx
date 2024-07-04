import {Key} from "@constants/Key";
import ErrorResponse from "@models/ErrorResponse";
import {LoginRequest} from "@models/LoginRequest";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import {
    Box,
    Button,
    Card,
    FormHelperText, Grid,
    IconButton,
    InputLabel,
    Link,
    OutlinedInput,
    Typography,
} from "@mui/material";
import authService from "@services/AuthService";
import {enqueueSnackbar} from "notistack";
import {useEffect, useState} from "react";
import {Controller, SubmitHandler, useForm} from "react-hook-form";

const Login = () => {
    const [visibility, setVisibility] = useState(false);

    useEffect(() => {
        const jsonMessage = localStorage.getItem(Key.MESSAGE);
        if (jsonMessage) {
            const message = JSON.parse(jsonMessage);
            localStorage.removeItem(Key.MESSAGE);
            enqueueSnackbar(message.message, {
                variant: message.type,
            });
        }
    }, []);


    const loginForm = useForm<LoginRequest>({
        mode: "all" && "onBlur",
    });


    const handleLogin: SubmitHandler<LoginRequest> = (data: LoginRequest) => {
        console.log(data);

        authService
            .loginWithLocal(data)
            .then((res) => {
                localStorage.setItem(Key.ACCESS_TOKEN, res.data.token);
                location.replace("/");
            })
            .catch((error: ErrorResponse) => {
                enqueueSnackbar(error.message, {variant: "error"})
            });
    };

    return (
        <Box
            sx={{
                width: "100%",
                height: "100vh",
                position: "relative",
            }}>
            <Box
                sx={{
                    position: "absolute",
                    backgroundImage: "url(/login-background.webp)",
                    backgroundRepeat: "no-repeat",
                    backgroundSize: "cover  ",
                    filter: "blur(5px)",
                    top: 0,
                    left: 0,
                    right: 0,
                    bottom: 0,
                }}></Box>
            <Card
                sx={{
                    width: "480px",
                    padding: "32px",
                    position: "absolute",
                    top: "50%",
                    left: "50%",
                    transform: "translate(-50%, -50%)",
                }}>
                <Box marginBottom={3}>
                    <Typography fontSize={28} fontWeight={500}>
                        Login
                    </Typography>
                </Box>
                <Box component="form" onSubmit={loginForm.handleSubmit(handleLogin)}>
                    <Grid container>
                        <Grid md={12} marginBottom={2}>
                            <InputLabel htmlFor="email-input" sx={{marginBottom: "2px"}}>
                                Email
                            </InputLabel>
                            <Controller
                                name="email"
                                control={loginForm.control}
                                rules={{
                                    pattern: {
                                        //@ts-ignore
                                        value: /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/,
                                        message: "Email is invalid !",
                                    },
                                    required: "Email must not be blank !",
                                }}
                                render={({field}) => (
                                    <OutlinedInput
                                        type="email"
                                        fullWidth
                                        placeholder="Email"
                                        error={!!loginForm.formState.errors.email?.message}
                                        {...field}
                                    />
                                )}
                            />
                            <FormHelperText id="email-message" error={!!loginForm.formState.errors.email?.message}>
                                {loginForm.formState.errors.email?.message}
                            </FormHelperText>
                        </Grid>
                        <Grid md={12} marginBottom={3}>
                            <InputLabel htmlFor="password-input" sx={{marginBottom: "2px"}}>
                                Password
                            </InputLabel>
                            <Box position="relative">
                                <Controller
                                    name="password"
                                    control={loginForm.control}
                                    rules={{
                                        required: "Password must not be blank !",
                                        minLength: {
                                            value: 6,
                                            message: "Password must have 6 character",
                                        },
                                    }}
                                    render={({field}) => (
                                        <OutlinedInput
                                            placeholder="Password"
                                            id="password-input"
                                            type={visibility ? "text" : "password"}
                                            fullWidth
                                            error={!!loginForm.formState.errors.password?.message}
                                            {...field}
                                        />
                                    )}
                                />
                                <IconButton
                                    onClick={() => setVisibility(!visibility)}
                                    sx={{
                                        position: "absolute",
                                        right: 1,
                                        top: "50%",
                                        transform: "translateY(-50%)",
                                        color: (theme) => theme.palette.grey[400],
                                    }}>
                                    {visibility ? <VisibilityOffIcon/> : <VisibilityIcon/>}
                                </IconButton>
                            </Box>
                            <FormHelperText id="password-message"
                                            error={!!loginForm.formState.errors.password?.message}>
                                {loginForm.formState.errors.password?.message}
                            </FormHelperText>
                        </Grid>
                        <Grid md={12} marginBottom={2}>
                            <Button
                                fullWidth
                                variant="contained"
                                color="primary"
                                type="submit">
                                Login
                            </Button>
                        </Grid>
                        <Grid md={12}>
                            <Link>Forgot password ?</Link>
                        </Grid>
                    </Grid>
                </Box>
            </Card>
        </Box>
    );
};

export default Login;

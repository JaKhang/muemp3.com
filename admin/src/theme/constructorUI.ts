import {createTheme} from "@mui/material/styles";

const ctTheme = createTheme({
    palette: {
        primary: {
            main: '#8833FF',
            dark: '#7919FF',
            light: '#974DFF',
        },
        success: {
            main: '#29CC39',
            dark: '#13BF24',
            light: '#45E655',
            contrastText: '#ffff'
        },
        error: {
            main: '#e62e2e',
            dark: '#CC1414',
            light: '#f24a4a'
        },
        info: {
            main: '#33BFFF',
            dark: '#17A5E6',
            light: '#4DC7FF'
        },
        warning: {
            main: '#FFCB33',
            dark: '#E6B117',
            light: '#FFD559'
        },
        text: {
            primary: '#4D5E80',
            disabled: "#ADB8CC",

        },
        grey: {
            "50": "#0D111A",
            "100": "#1A2233",
            "200": "#26334D",
            "300": "#334466",
            "400": "#4D5E80",
            "500": "#6B7A99",
            "600": "#7D8FB3",
            "700": "#ADB8CC",
            "800": "#DADEE6",
            "900": "#F7F8FA"
        },

        background: {
            default: "#F7F8FA",
            paper: "#fff",

        }
    },
    components: {
        MuiButton: {
            defaultProps: {
                variant: "contained"
            }
        },
        MuiIcon: {
            styleOverrides: {
                root: {
                    color: "inherit"
                }
            }
        },
        MuiListItemIcon: {
            styleOverrides: {
                root: {
                    color: "inherit"
                }
            }
        },
        MuiIconButton: {
            styleOverrides: {
                root: {
                    color: "#C3CAD9"
                }
            }
        },
        MuiTextField: {
            styleOverrides: {
                root: {
                    "fieldset": {
                        borderColor: "#F2F3F5",
                        borderWidth: "2px"
                    },
                    "&:hover fieldset": {
                        color: "#7D8FB3",
                        borderColor: "rgba(218, 222, 230, 1)",


                    }
                }
            },
            defaultProps: {}
        },
        MuiOutlinedInput: {
            styleOverrides: {}
        },
        MuiInputLabel: {
            styleOverrides: {
                root: {
                    color: "#7D8FB3"
                }
            }
        },
        MuiInputBase: {
            styleOverrides: {
                root: {
                    boxShadow: "0px 2px 5px 0px rgba(38, 51, 77, 0.03)",
                    // borderColor: "transparent",
                    backgroundColor: "#fff",
                    borderColor: "#F2F3F5",


                }
            }
        },
        MuiFormControl: {
            styleOverrides: {
                root: {
                    "fieldset": {
                        borderColor: "#F2F3F5",
                        borderWidth: "2px"
                    }

                }
            }
        },
        MuiBackdrop: {
            styleOverrides: {
                root: {
                    backdropFilter: "blur(2px)",
                    backgroundColor: "rgba(15, 23, 42, 0.25)"

                }
            }
        }
    },
    shape: {
        borderRadius: 4
    },
    typography: {
        fontFamily: [
            'Roboto',
            '-apple-system',
            'BlinkMacSystemFont',
            '"Segoe UI"',
            '"Helvetica Neue"',
            'Arial',
            'sans-serif',
            '"Apple Color Emoji"',
            '"Segoe UI Emoji"',
            '"Segoe UI Symbol"',
        ].join(','),
        fontSize: 14,
        fontWeightLight: 300,
        fontWeightRegular: 400,
        fontWeightMedium: 500,
        fontWeightBold: 600,
        body1: {
            fontSize: 14,
            fontWeight: 500,
            color: "#4D5E80",
            textAlign: "left",
            lineHeight: "30px"
        },
        button: {
            fontSize: 14,
            fontWeight: 900,
            lineHeight: "30px",
            textTransform: "unset"

        }
    },
    spacing: 4,
    breakpoints: {
        values: {
            xs: 0,
            sm: 600,
            md: 900,
            lg: 1274,
            xl: 1600,
        }
    }

});

export default ctTheme;
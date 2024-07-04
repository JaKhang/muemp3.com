import { Key } from "@constants/Key";
import UserPrinciple from "@models/UserPrinciple";
import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import authService from "@services/AuthService";

export interface AuthSate {
  principle: UserPrinciple | null;
  loading: boolean;
}

const initialState: AuthSate = {
  principle: null,
  loading: false,
};

export const localWithAccessToken = createAsyncThunk(
  "auth/getPrinciple",
  async (accessToken: string, thunkAPI) => {
    try {
      const loginResponse = await authService.getPrinciple(accessToken);
      return loginResponse.data;
    } catch (e) {
      return thunkAPI.rejectWithValue(e);
    }
  }
);

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    logout(state) {
      state.principle = null;
      localStorage.removeItem(Key.ACCESS_TOKEN);
    },
  },
  extraReducers(builder) {
    builder
      .addCase(
        localWithAccessToken.fulfilled,
        (state, action: PayloadAction<UserPrinciple>) => {
          state.loading = false;
          state.principle = action.payload;
        }
      )
      .addCase(localWithAccessToken.pending, (state) => {
        state.loading = true;
      })
      .addCase(localWithAccessToken.rejected, (state) => {
        state.loading = false;
      });
  },
});

export default authSlice;

import ErrorResponse from "@models/ErrorResponse.ts";
import { ExplorerResponse } from "@models/ExplorerResponse.ts";
import { Direction, Page } from "@models/Sort.ts";
import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import fileService, { FileQuery } from "@services/FileService.ts";
import { ViewMode } from "../ViewMode.ts";
import { FileType } from "@models/FileType.ts";

interface ExplorerState {
  page: Page;
  data: ExplorerResponse;
  selectedIds: string[];
  loading: boolean;
  showDetails: boolean;
  viewMode: ViewMode;
  query: FileQuery;
  multiple?: boolean;
  clipboard?: {
    ids: string[];
    copy: boolean;
  };
}

const initialState: ExplorerState = {
  //@ts-ignore
  data: undefined,
  loading: false,
  page: {
    page: 1,
    limit: 16,
    sort: {
      direction: Direction.DESC,
      property: "createdAt",
    },
  },
  query: {
    name: "",
    deleted: false,
    fileType: null,
  },
  selectedIds: [],
  showDetails: false,
  viewMode: ViewMode.GRID,
};

export const getRoot = createAsyncThunk<
  ExplorerResponse,
  void,
  {
    state: ExplorerState;
    rejectValue: ErrorResponse;
  }
>("explorer/getRoot", async () => {
  const res = await fileService.findRoot();
  return res.data;
});

export const getFolder = createAsyncThunk<
  ExplorerResponse,
  string,
  {
    state: { explorer: ExplorerState };
    rejectValue: ErrorResponse;
  }
>("explorer/getFolder", async (id, thunkAPI) => {
  const {
    explorer: { page, data, query },
  } = thunkAPI.getState();

  const res = await fileService.findFolder(id, { ...page, page: 1 }, query);
  return res.data;
});

export const loadMore = createAsyncThunk<
  ExplorerResponse,
  void,
  {
    state: { explorer: ExplorerState };
    rejectValue: ErrorResponse;
  }
>("explorer/loadMore", async (_arg, thunkAPI) => {
  const {
    explorer: { page, data, query },
  } = thunkAPI.getState();

  const res = await fileService.findFolder(
    data.current.id,
    { ...page, page: page.page + 1 },
    query
  );

  return res.data;
});

export const moveTo = createAsyncThunk<
  void,
  void,
  {
    state: {
      explorer: ExplorerState;
    };
    rejectValue: ErrorResponse;
  }
>("explorer/moveTo", async (_arg, thunkAPI) => {
  const { clipboard, data } = thunkAPI.getState().explorer;

  if (!!clipboard)
    fileService.moveFile(clipboard.ids, data.current.id, clipboard.copy);
});

const explorerSlice = createSlice({
  name: "explorer",
  initialState,
  reducers: {
    setViewMode(state, payload: PayloadAction<ViewMode>) {
      state.viewMode = payload.payload;
    },
    setPage(state, action: PayloadAction<Page>) {
      state.page = action.payload;
    },
    setSelectedId(state, { payload }: PayloadAction<string>) {
      if (state.selectedIds.includes(payload)) {
        state.selectedIds = state.selectedIds.filter((id) => id != payload);
      } else {
        if (state.multiple) state.selectedIds = [...state.selectedIds, payload];
        else state.selectedIds = [payload];
      }
    },
    setMultiple(state, action: PayloadAction<boolean>) {
      state.multiple = action.payload;
    },
    setFileType(state, action: PayloadAction<FileType | null>) {
      state.query = { ...state.query, fileType: action.payload };
    },
    setQueryName(state, action: PayloadAction<string>) {
      state.query = { ...state.query, name: action.payload };
    },
    selectAll(state) {
      if (state.selectedIds.length == state.data.files.length)
        state.selectedIds = [];
      else state.selectedIds = state.data.files.map((file) => file.id);
    },
    addToClipboard(state, action: PayloadAction<boolean>) {
      state.clipboard = {
        ids: state.selectedIds,
        copy: action.payload,
      };
    },
  },
  extraReducers(builder) {
    builder
      .addCase(getRoot.pending, (state) => {
        state.loading = true;
      })
      .addCase(getRoot.fulfilled, (state, { payload }) => {
        state.loading = false;
        state.data = payload;
      });

    builder
      .addCase(loadMore.pending, (state) => {})
      .addCase(loadMore.fulfilled, (state, { payload }) => {
        payload.files = [...state.data.files, ...payload.files];
        state.data = payload;
        state.page = { ...state.page, page: state.page.page + 1 };
      });

    builder
      .addCase(getFolder.pending, (state) => {
        state.loading = true;
      })
      .addCase(getFolder.fulfilled, (state, { payload }) => {
        state.data = payload;
        state.loading = false;
        state.page = { ...state.page, page: 1 };
        state.selectedIds = [];
      });

    builder
      .addCase(moveTo.pending, (state) => {
        state.loading = true;
      })
      .addCase(moveTo.fulfilled, (state) => {
        state.loading = false;
        state.clipboard = undefined;
      });
  },
});

export default explorerSlice;

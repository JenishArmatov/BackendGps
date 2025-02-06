import { configureStore } from "@reduxjs/toolkit";
import { apiSlice } from "./apiSlice";
import tokenReducer from "./slices/tokenSlices";

const store = configureStore({
  reducer: {
    api: apiSlice.reducer,
    token: tokenReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(apiSlice.middleware),
});

export default store;

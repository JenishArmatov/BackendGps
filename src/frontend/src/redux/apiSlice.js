import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

const baseQuery = fetchBaseQuery({
  baseUrl: "https://gps.rjvgcthdbccomp.com.kg/auth/",
  prepareHeaders: (headers) => {
    const token = localStorage.getItem("token");
    if (token) {
      headers.set("Authorization", `Bearer ${token}`);
    }
    headers.set("Content-Type", "application/json"); 
    return headers;
  },
});

export const apiSlice = createApi({
  reducerPath: "api",
  baseQuery,
  endpoints: (builder) => ({
    login: builder.mutation({
      query: (credentials) => {
        console.log("Запрос отправляется:", credentials);
        return {
          url: "sign-in", 
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(credentials), 
        };
      },
      async onQueryStarted(arg, { queryFulfilled }) {
        console.log("Отправленный запрос:", arg);
        try {
          const { data } = await queryFulfilled;
          console.log("Ответ сервера:", data);
        } catch (error) {
          console.error("Ошибка запроса:", error);
        }
      },
    }),
  }),
});

export const { useLoginMutation } = apiSlice;

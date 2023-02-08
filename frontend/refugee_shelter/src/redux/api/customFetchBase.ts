import {
  BaseQueryFn,
  FetchArgs,
  fetchBaseQuery,
  FetchBaseQueryError,
} from "@reduxjs/toolkit/query";
import { Mutex } from "async-mutex";
import { logout } from "../features/authSlice";

const baseUrl = `http://localhost:8081/api/v1/`;

// Create a new mutex
const mutex = new Mutex();

const baseQuery = fetchBaseQuery({
  baseUrl,
});

const customFetchBase: BaseQueryFn<
  string | FetchArgs,
  unknown,
  FetchBaseQueryError
> = async (args: any, api, extraOptions) => {
  // wait until the mutex is available without locking it
  await mutex.waitForUnlock();
  let result = await baseQuery(args, api, extraOptions);
  if ((result.error?.data as any)?.error_message === "You are not logged in") {
    if (!mutex.isLocked()) {
      const release = await mutex.acquire();
      console.log("args = ", args.headers.Authorization);
      try {
        const refreshResult: any = await baseQuery(
          {
            headers: {
              Authorization:
                "Bearer " + localStorage.getItem("refresh_token") || "",
            },
            url: "token/refresh",
          },
          api,
          extraOptions
        );

        console.log("REfresth result = ", refreshResult);

        if (refreshResult.data) {
          console.log("RETRY");
          // Retry the initial query
          args.headers.Authorization =
            "Bearer " + refreshResult.data["access_token"];
          result = await baseQuery(args, api, extraOptions);
        } else {
          console.log("LOGOUT");
          api.dispatch(logout());
          window.location.href = "/login";
        }
      } finally {
        // release must be called once the mutex should be released again.
        release();
      }
    } else {
      // wait until the mutex is available without locking it
      await mutex.waitForUnlock();
      result = await baseQuery(args, api, extraOptions);
    }
  }

  return result;
};

export default customFetchBase;

import { toast } from "react-toastify";

const handleServerResponse = (result: any, msg: string) => {
  if (result.isSuccess) {
    toast.success(msg, {
      position: "top-center",
    });
    return 0;
  }
  if (result.isError) {
    if (Array.isArray((result.error as any).data.error)) {
      (result.error as any).data.error.forEach((el: any) =>
        toast.error(el.message, {
          position: "top-center",
        })
      );
    } else {
      console.log(result.error);
      toast.error((result.error as any).data.message, {
        position: "top-center",
      });
    }
  }
  return -1;
};

export default handleServerResponse;

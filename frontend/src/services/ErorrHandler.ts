import {toast} from "react-toastify";

class ErrorHandler {

    handle(rs: any){
        console.log(
            rs
        )
        toast.error<string>("Lỗi không xác định")

    }

}


export default new ErrorHandler();
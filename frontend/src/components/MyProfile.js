import {useQuery} from "react-query";
import { isLoggedIn } from "axios-jwt";
import {axiosInstance} from "./Api";
import Profile from "./Profile";
import Register from "./Register";

function MyProfile() {


    return (
        <div>
            {isLoggedIn() ?
                <Profile/>
            :
                <Register/>
            }
        </div>
    )
}

export default MyProfile
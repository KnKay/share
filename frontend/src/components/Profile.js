import {useQuery} from "react-query";

import {axiosInstance} from "./Api";


function Profile() {

    const {isLoading, error, data, isFetching} = useQuery("profileData", () =>
        axiosInstance.get(
            "profile/"
        ).then((res) => res.data)
    );
    const { isLoading: userLoading, error :userError, data: user} = useQuery("profileData", () =>
    axiosInstance.get(
        "profile/"
    ).then((res) => res.data)
    );

    if (isLoading) return "Loading...";

    if (error) return "An error has occurred: " + error.message;

    console.debug(data)
    console.debug(user)

    return (
        <div className={"flex-1"}>
            <div className={"mx-auto"}>
                <h1 style={{textAlign: "center"}}>
                    Profile
                </h1>
            </div>
            Profile Name: {data.user.username}
        </div>
    )
}

export default Profile

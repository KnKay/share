import {useQuery} from "react-query";
import { isLoggedIn } from "axios-jwt";
import { useParams } from "react-router-dom";

import {axiosInstance} from "./Api";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import GroupAssets from "./GroupAssets";

function AssetGroup(){
    const { id } = useParams();
    const {isLoading, error, data, isFetching} = useQuery("itemData", () =>
        axiosInstance.get(
            "assetgroup/"+id
        ).then((res) => res.data)
    );
    console.log(data)

    console.debug(id)

    if (isLoading) return "Loading...";

    if (error) return "An error has occurred: " + error.message;

    return (
        <div className={"col mr-3"}>
            <h1>{data.name}</h1>
            <p>{data.description}</p>
            <GroupAssets></GroupAssets>
        </div>

    )
}

export default AssetGroup

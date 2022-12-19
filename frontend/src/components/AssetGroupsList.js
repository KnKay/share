import {useQuery} from "react-query";
import { isLoggedIn } from "axios-jwt";

import {axiosInstance} from "../axiosAPI";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";

/*
At the moment this is a simple list.
This should become a carousel or something more elegant in future.
But this is too much design at the moment.
*/

function AssetGroupsList(){

    const {isLoading, error, data, isFetching} = useQuery("itemData", () =>
        axiosInstance.get(
            "assetgroup/"
        ).then((res) => res.data)
    );


    if (isLoading) return "Loading...";

    if (error) return "An error has occurred: " + error.message;

    return (
        <div className={"col mr-3"}>
            <DataTable value={data.results} stripedRows className={"p-1"}>
                <Column field="name" header="Name" className={"p-1"} />
                <Column field="description" header="Beschreibung" className={"p-1"} />
            </DataTable>
            {isLoggedIn() ?
                <div>
                    <p>Admin actions</p>
                </div>
                :
                <div/>
            }
        </div>
    )
}

export default AssetGroupsList

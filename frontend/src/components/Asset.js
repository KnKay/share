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

function Asset(){

    const {isLoading, error, data, isFetching} = useQuery("itemData", () =>
        axiosInstance.get(
            "asset/"
        ).then((res) => res.data)
    );


    if (isLoading) return "Loading...";

    if (error) return "An error has occurred: " + error.message;

    return (
        <div className={"col mr-3"}>
            <DataTable value={data.results} stripedRows className={"p-1"}>
                <Column field="name" header="Name" className={"p-1"} />
                <Column field="description" header="Beschreibung" className={"p-1"} />
                {isLoggedIn() ?
                <Column field="description" header="Beschreibung" className={"p-1"} />
                :
                <div/>
            }
            </DataTable>

        </div>
    )
}

export default Asset

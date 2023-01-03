import {useQuery} from "react-query";
import { useParams } from "react-router-dom";
import {axiosInstance} from "./Api";

import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";

function GroupAssets() {
    const { id } = useParams();
    const {isLoading, error, data, isFetching} = useQuery("itemAssets", () =>
    axiosInstance.get(
        "assetgroup/"+id+"/assets"
    ).then((res) => res.data)
    );

    console.log(data)

    if (isLoading) return "Loading...";

    if (error) return "An error has occurred: " + error.message;

    return (
        <div className={"flex-1"}>
            <DataTable value={data} stripedRows className={"p-1"}>
                <Column field="name" header="Name" className={"p-1"} />
                <Column field="description" header="Beschreibung" className={"p-1"} />
            </DataTable>
        </div>
    )
}

export default GroupAssets

import {useQuery} from "react-query";
import { isLoggedIn } from "axios-jwt";

import {axiosInstance} from "./Api";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import React from 'react';

import { Button } from 'primereact/button';
import {Link} from 'react'
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
    console.log(data)

    if (isLoading) return "Loading...";

    if (error) return "An error has occurred: " + error.message;

    const actionBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                    <a href={"/groups/"+rowData.id}>
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-success mr-2"  />
                    </a>
            </React.Fragment>
        );
    }

    return (
        <div className={"col mr-3"}>
            <DataTable value={data} stripedRows className={"p-1"}>
                <Column field="name" header="Name" className={"p-1"} />
                <Column field="description" header="Beschreibung" className={"p-1"} />
                <Column body={actionBodyTemplate} exportable={false} className={"p-1"}></Column>
            </DataTable>
        </div>

    )
}

export default AssetGroupsList

import { DataTable } from "primereact/datatable";
import { useState } from "react";
import {axiosInstance} from "./Api";
import {Column} from "primereact/column";
import {useQuery} from "react-query";
import { Checkbox } from 'primereact/checkbox';

function Transactions() {

    const [data, setData] = useState()
    const {isLoading, error} = useQuery("itemData", () =>
    axiosInstance.get(
        "transaction"
    ).then((res) => setData(res.data))
    );


    if (isLoading) return "Loading"
    if (error) return error.message;
    const columns = [
        { field: 'owner.username', header: 'Name' },
        { field: 'asset.name', header: 'asset' },
        { field: 'date_from', header: 'von' },
        { field: 'date_to', header: 'bis' },
        { field: 'accepted', header: 'angenommen' }
    ];

    const onCellEditComplete = (e) => {
        let { rowData, newValue, field, originalEvent: event } = e;

    }


    const acceptedEditor = (options) => {
        return <Checkbox inputId="binary" checked={options.accepted} onChange={(e) => options.editorCallback(e.value)} />
    }
    const actionBodyTemplate = (rowData) => {
        console.debug(rowData.data)
        window.location.href = "/transactions/"+rowData.data.id
    }

    return(

    <DataTable value={data} stripedRows className={"p-1"} onRowClick={actionBodyTemplate}>
            <Column field="asset.name" header="Asset" className={"p-1"} />
            <Column field="date_from" header="von" className={"p-1"} />
            <Column field="date_to" header="bis" className={"p-1"} />
            <Column field="owner.username" header="Nutzer" className={"p-1"} />
            <Column field="accepted" header="Angenommen" className={"p-1"}  body={acceptedEditor}
            editor={(options) => acceptedEditor(options)} onCellEditComplete={onCellEditComplete} />

    </DataTable>


    )
}

export default Transactions

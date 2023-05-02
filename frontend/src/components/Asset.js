import {useQuery} from "react-query";
import { useParams } from "react-router-dom";

import {axiosInstance} from "./Api";

import { Calendar } from 'primereact/calendar';
import { useState } from "react";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import { Button } from 'primereact/button';

function Asset(){
    const { id } = useParams();
    const [data, setData] = useState({});
    const [view, setView] = useState("calendar")
    const {isLoading, error, isFetching} = useQuery("itemData", () =>
        axiosInstance.get(
            "asset/"+id
        ).then((res) => {
            let data = res.data
            let transactions =[]
            data.transactions.forEach(
                transaction => {
                    let from = new Date(transaction.date_from)
                    let to = new Date(transaction.date_to)
                    let days = (to - from)/ (1000 * 60 * 60 * 24)
                    for(let count = 0; days > count; count++){
                        let ms = from.getTime() + (86400000*count);
                        var tomorrow = new Date(ms);
                        transactions.push(tomorrow)
                    }
                }
            )
            data.days = transactions
            setData(data)
        })
    );

    if (isLoading) return "Loading...";
    if (error) return "An error has occurred: " + error.message;

    const click = (e) => {
        console.log(e)
        setView(!view)
    };
    const calendar = () =>{
        return view
    }
    const request = ()=>{
        window.location.href="/assets/"+id+"/request"
    }

    return (
            <div className={"col mr-3"}>
                <h1>{data.name}</h1>
                <p>{data.description}</p>
                {calendar() ?
                <Calendar id="calendar" value={data.days} selectionMode="multiple" inline  numberOfMonths={3} />
                :
                <DataTable value={data.transactions} stripedRows className={"p-1"}>
                    <Column field="date_from" header="From" className={"p-1"} />
                    <Column field="date_to" header="To" className={"p-1"} />
                </DataTable>
                }
                <Button className="mt-5" onClick={click} label="Change view" style={{width: "100%"}}/>
                <Button className="mt-5" onClick={request} label="Request" style={{width: "100%"}}/>
            </div>
    )
}

export default Asset
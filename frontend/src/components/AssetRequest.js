import {useQuery} from "react-query";
import { useParams } from "react-router-dom";

import {axiosInstance} from "./Api";

import { Calendar } from 'primereact/calendar';
import { useState } from "react";
import { Button } from 'primereact/button';
import {useMutation} from "react-query";
import {Card} from "primereact/card";
import { isLoggedIn } from "axios-jwt";

function AssetRequest(){
    const { id } = useParams();
    const [data, setData] = useState({"date_from":"", "data_to":"","asset":id});
    const mutation = useMutation(e => {
        e.preventDefault()

        let my_data = {"asset":  parseInt(id), "date_from":data.date_from.toISOString()  , "date_to":data.date_to.toISOString()}
        console.log(my_data)

        return axiosInstance.post("transaction/", my_data).then(window.location.href="/assets/"+id)
    })

    if (!isLoggedIn()){
        window.location.href="/login";
    }
    return (

                <Card style={{width: "25em"}} className={"mx-auto mt-5 pt-0"}>
                    <h3 style={{textAlign: "center"}} className={"mt-0"}>Asset Request</h3>
                    <h1>{data.name}</h1>
                <p>{data.description}</p>
                    <form onSubmit={mutation.mutate}>
                    <Calendar id="time24" value={data.date_from} onChange={(e) => setData({...data, date_from : e.value, date_to: e.value})} showTime />
                    <Calendar id="time24" value={data.date_to} onChange={(e) => setData({...data, date_to : e.value})} showTime />
                    <Button className="mt-5" type="submit" label="Submit" style={{width: "100%"}}/>
                    </form>
                </Card>

    )
}

export default AssetRequest
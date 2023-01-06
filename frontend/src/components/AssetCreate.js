import React, { useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Card } from 'primereact/card';
import { useParams } from "react-router-dom";
import { Button } from 'primereact/button';
import { Dropdown } from 'primereact/dropdown';
import {axiosInstance} from "./Api";
import {useQuery} from "react-query";
import {useMutation} from "react-query";
function AssetCreate() {

    const { id } = useParams();

    const [data, setData] = useState({"name":"","description":"","asset_group":parseInt(id)});

    const {data: groups, error: groupError, isLoading: groupLoading } = useQuery("groups", () => axiosInstance.get(
        "assetgroup/"
    ).then((res) => res.data));

    const mutation = useMutation(e => {
        e.preventDefault()
        return axiosInstance.post("asset/", data)
        // OnSuccess....
    })

    if (groupLoading) return "Loading...";

    if (groupError ) return "An error has occurred";
console.log(id)
console.log(data)
    return (
        <div id={"asset-container"}  className={"flex-1"}>
            <Card style={{width: "25em"}} className={"mx-auto mt-5 pt-0"}>
                    <h3 style={{textAlign: "center"}} className={"mt-0"}>Asset Creator</h3>
                    <form onSubmit={mutation.mutate}>
                        <InputText type={"name"} id="name" placeholder="name"
                                   style={{width: "100%", textAlign: "left"}} value={data.name}
                                   onChange={(e) => setData({...data, name:e.target.value})}/>

                        <InputTextarea type={"description"} id="description" placeholder="description"
                                   style={{width: "100%", textAlign: "left"}} value={data.description}
                                   onChange={(e) => setData({...data, description:e.target.value})}/>

                        <Dropdown optionLabel="name" optionValue="id"
                        value={data.asset_group} options={groups} onChange={(e) => setData({...data, asset_group: e.target.value})} placeholder="asset_group"/>

                        <Button className="mt-5" type="submit" label="Submit" style={{width: "100%"}}/>
                        </form>
                </Card>
          </div>

    )
}

export default AssetCreate
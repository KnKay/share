import {useQuery} from "react-query";
import { useParams } from "react-router-dom";
import { useState } from "react";
import {axiosInstance} from "./Api";
import {useMutation} from "react-query";
import { Button } from 'primereact/button';
import {Card} from "primereact/card";

function Transaction(){
    const { id } = useParams();
    const [data, setData] = useState(null);
    const { isLoading: userLoading, error :userError, data: user} = useQuery("profileData", () =>
    axiosInstance.get(
        "profile/"
    ).then((res) => res.data)
    );

    const {isLoading, error, isFetching} = useQuery("itemData", () =>
        axiosInstance.get(
            "transaction/"+id
        ).then((res) =>setData(res.data))
    );

    const mutation = useMutation(e => {
        console.log("send")
        console.debug(data)
        axiosInstance.put("transaction/"+id+"/", data)
    })

    if (isLoading) return "Loading...";
    if (error) return "An error has occurred: " + error.message;


    const click = (e) => {
        setData({...data, accepted: true})
        mutation.mutate()
    };

    const owner = () =>{
        let is_owner = data.asset.owner.username === user.user.username
        let can_accept = !data.accepted
        return is_owner && can_accept
    }

    const accepted = () =>{
        if (data.accepted)
            return "Accepted"
        return "Not yet Accepted"
    }

    return(
        <Card style={{width: "25em"}} className={"mx-auto mt-5 pt-0"}>
            <h3>{data.asset.name}</h3>
            <p>{data.date_from}</p>
            <p>{data.date_to}</p>
            <p>{accepted()}</p>
            {owner() ?

            <Button className="mt-5" onClick={click} label="Zusagen" style={{width: "100%"}}/>
            :
            <p></p>
            }
        </Card>
    )
}
export default Transaction
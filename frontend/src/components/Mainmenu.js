import React, {Component} from "react";
import {PrimeIcons} from "primereact/api";
import {Menu} from "primereact/menu";
import {logout} from "./Api";

class Mainmenu extends Component {

    menu_items = [
        {label: 'Profile', icon: PrimeIcons.USER, url: "/profile"},
        {label: 'Groups', icon: PrimeIcons.USERS, url: "/groups"},
        {label: 'Assets', icon: PrimeIcons.USERS, url: "/assets"},
        {label: 'Log out', icon: PrimeIcons.SIGN_OUT, command: logout},
        {
            label: 'Admin',
            items: [
                {url: "http://127.0.0.1:8000/admin", label: 'Django', icon: PrimeIcons.COG},
            ]
        },
    ];

    render () {
        return (
            <div className={"col-fixed pr-1 mr-3"} style={{width: "200px"}}>
                <Menu model={this.menu_items} />
            </div>
        );
    }

}

export default Mainmenu;

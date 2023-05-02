import React from "react";
import {PrimeIcons} from "primereact/api";
import { Menubar } from 'primereact/menubar';
import {logout} from "./Api";
import { InputText } from 'primereact/inputtext';
import { isLoggedIn } from "axios-jwt";

const Mainmenu = () => {
    const user_items = [

        {
            label: 'Mein',
            items: [
                {label: 'Profile', icon: PrimeIcons.USER, url: "/profile"},
                {label: 'Anfragen', icon: PrimeIcons.USER, url: "/transactions"},
                {label: 'Log out', icon: PrimeIcons.SIGN_OUT, command: logout},
            ]
        },

    ]
    const register =[
        {label: 'Register', icon: PrimeIcons.USER, url: "/profile"},
        {label: 'Login', icon: PrimeIcons.USER, url: "/login"},
    ]

    var menu_items = [
        {label: 'Kategorien', icon: PrimeIcons.USERS, url: "/groups"},
        {label: 'Alles', icon: PrimeIcons.USERS, url: "/assets"},
        {
            label: 'Admin',
            items: [
                {url: "http://127.0.0.1:8000/admin", label: 'Django', icon: PrimeIcons.COG},
            ]
        }
    ];
    {isLoggedIn() ?
        menu_items = menu_items.concat(user_items)
        :
        menu_items = menu_items.concat(register)
    }
    const end = <InputText placeholder="Search" type="text" />;
    return (
            <Menubar model={menu_items} end={end}/>
    );

}

export default Mainmenu;

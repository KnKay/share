import "primereact/resources/themes/lara-light-blue/theme.css"; //theme
import "primereact/resources/primereact.min.css"; //core css
import "primeicons/primeicons.css"; //icons
import "primeflex/primeflex.css"

import logo from './logo.svg';
import './App.css';

import Login  from './components/Login';
import Test from './components/Test'
import Mainmenu from "./components/Mainmenu";
import Register from "./components/Register"

import { isLoggedIn } from "axios-jwt";
import {Route, Routes} from "react-router-dom";

function App() {
  return (
    <div className="App">
      <div className={"flex flex-column"}
                 style={{width: "100%", height: "100%", position: "absolute"}}>

        <div className={"grid m-0"}>
          <Mainmenu />
        </div>
          <div className={"grid m-0"}>
            <p>Hi</p>
            <Register/>
        </div>

      </div>

    </div>
  );
}

export default App;

import "primereact/resources/themes/lara-light-blue/theme.css"; //theme
import "primereact/resources/primereact.min.css"; //core css
import "primeicons/primeicons.css"; //icons
import "primeflex/primeflex.css"

import logo from './logo.svg';
import './App.css';

import Login  from './components/Login';
import Test from './components/Test'
import Mainmenu from "./components/Mainmenu";
import { isLoggedIn } from "axios-jwt";

function App() {
  return (
    <div className="App">
      <div className={"flex flex-column"}
                 style={{width: "100%", height: "100%", position: "absolute"}}>
        {isLoggedIn()
        ?<div className={"grid m-0"}>
          <Mainmenu />
          <Test />
        </div>
        : <Login />
        }
        </div>

    </div>
  );
}

export default App;

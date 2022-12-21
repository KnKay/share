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
import Profile from "./components/Profile";
import AssetList from "./components/AssetList"

import { isLoggedIn } from "axios-jwt";
import {Route, Routes} from "react-router-dom";
import AssetGroupsList from "./components/AssetGroupsList";
import {QueryClient, QueryClientProvider} from 'react-query'

function App() {
  const queryClient = new QueryClient()
  return (
    <div className="App">
      <div className={"flex flex-column"}
                 style={{width: "100%", height: "100%", position: "absolute"}}>

        <div className={"grid m-0"}>
          <Mainmenu />
        </div>
          <div className={"grid m-0"}>
            <QueryClientProvider client={queryClient}>
              <Routes>
                  <Route path="/" element={<Test/>}/>
                  <Route path="/profile" element={<Profile/>}/>
                  <Route path="/groups" element={<AssetGroupsList/>}/>
                  <Route path="/assets" element={<AssetList/>}/>
              </Routes>
            </QueryClientProvider>
        </div>
      </div>
    </div>
  );
}

export default App;

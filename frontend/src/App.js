import "primereact/resources/themes/lara-light-blue/theme.css"; //theme
import "primereact/resources/primereact.min.css"; //core css
import "primeicons/primeicons.css"; //icons
import "primeflex/primeflex.css"

import logo from './logo.svg';
import './App.css';

import Test from './components/Test'
import Mainmenu from "./components/Mainmenu";
import AssetList from "./components/AssetList"
import AssetGroup from "./components/AssetGroup";

import {Route, Routes} from "react-router-dom";
import AssetGroupsList from "./components/AssetGroupsList";
import {QueryClient, QueryClientProvider} from 'react-query'
import MyProfile from "./components/MyProfile";
import Login from "./components/Login"
import Asset from "./components/Asset";
import AssetEditor from "./components/AssetEdit";
import AssetCreate from "./components/AssetCreate";
import AssetRequest from "./components/AssetRequest";
import Transactions from "./components/Transactions";
import Transaction from "./components/Transaction";

function App() {
  const queryClient = new QueryClient()
  return (
    <div className="App">
      <Mainmenu />
      <div className={"flex flex-column"} style={{width: "100%", height: "100%", position: "absolute"}}>
          <div className={"grid m-0"}>
            <QueryClientProvider client={queryClient}>
              <Routes>
                  <Route path="/" element={<Test/>}/>
                  <Route path="/profile" element={<MyProfile/>}/>
                  <Route path="/groups" element={<AssetGroupsList/>}/>
                  <Route path="/groups/:id" element={<AssetGroup/>}/>
                  <Route path="/groups/:id/add" element={<AssetCreate/>}/>
                  <Route path="/assets" element={<AssetList/>}/>
                  <Route path="/assets/:id" element={<Asset/>}/>
                  <Route path="/assets/:id/edit" element={<AssetEditor/>}/>
                  <Route path="/assets/:id/request" element={<AssetRequest/>}/>
                  <Route path="/transactions" element={<Transactions/>}/>
                  <Route path="/transactions/:id" element={<Transaction/>}/>
                  <Route path="/login" element={<Login/>}/>
              </Routes>
            </QueryClientProvider>
        </div>
      </div>
      <footer id="footer">
        <Test></Test>
        </footer>
    </div>
  );
}

export default App;

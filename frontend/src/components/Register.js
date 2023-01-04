import {Component} from "react";

import {Card} from "primereact/card";
import {InputText} from 'primereact/inputtext';
import {Button} from 'primereact/button';

import { register } from "./Api";

class Register extends Component {

  constructor(props) {
    super(props);
    this.state = {
      email:"",
      password:"",
      username:""
  };

    this.handleSubmit = this.handleSubmit.bind(this);
    }

    async handleSubmit(event) {
        event.preventDefault();

        await register(this.state.username, this.state.password, this.state.email)
        // window.location.reload()
        console.debug(this.state)
    }
    render() {
        return(
          <div id={"login-container"} style={{
            background: "url(login-bg.jpeg) no-repeat scroll center center transparent",
            }} className={"flex-1"}>
            <Card style={{width: "25em"}} className={"mx-auto mt-5 pt-0"}>
                    <h3 style={{textAlign: "center"}} className={"mt-0"}>Register</h3>
                    <form onSubmit={this.handleSubmit}>
                        <InputText type={"username"} id="username" placeholder="Username"
                                   style={{width: "100%", textAlign: "left"}} value={this.state.username}
                                   onChange={e => this.setState({username: e.target.value})}/>
                        <InputText type={"password"} id="password" placeholder="Password"
                                   style={{width: "100%", textAlign: "left"}} value={this.state.password}
                                   onChange={e => this.setState({password: e.target.value})}/>
                        <InputText type={"email"} id="email" placeholder="Email"
                                   style={{width: "100%", textAlign: "left"}} value={this.state.email}
                                   onChange={e => this.setState({email: e.target.value})}/>
                        <Button className="mt-5" type="submit" label="Register" style={{width: "100%"}}/>
                    </form>
                </Card>
          </div>
        )
    }
}

export default Register

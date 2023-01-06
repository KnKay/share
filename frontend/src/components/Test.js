import {Component} from "react";
import { isLoggedIn } from "axios-jwt";

class Test extends Component {
    render() {
        return(
            <div>
                {isLoggedIn() ?
                <p>Logged in</p>
                :
                <p>Logged out</p>
                }
            </div>
        )
    }
}

export default Test

import React from "react";
import io from 'socket.io-client';
import './App.css';

class Dashboard extends React.Component {

    state = {
        current_price: "",
        current_time: "",
        current_product: ""
    }

    componentDidMount() {
        
        var socket = io.connect("http://localhost:5000")
        
        socket.on('connect', function (){
            socket.send("Socket connected from react")
        })
        
        
        socket.on("message", (record) => {
            
            console.log("Data received from Flask server:", record)
            record = JSON.parse(record);
              
              this.setState({
                current_price: record["price"],
                current_time: record["time"],
                current_product: record["product_id"]
              })

              socket.send("Message received by react client")
            
        });
    

    }

    

    render() {
        return (
            <div>
                <div>{"Product: " + this.state.current_product}</div>
                <div>{"Price: " + this.state.current_price}</div>
                <div>{"Time: " + this.state.current_time}</div>
            </div>
        )
    }
}

export default Dashboard;
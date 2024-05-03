import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.css';
// const express = require("express")
// const cors = require("cors")
// const app = express()
// app.use(
// cors({
//  origin: "*",
//  methods:["GET", "POST", "PUT", "DELETE"],
// })
// )
// const cors=require("cors");
// const corsOptions ={
//    origin:'*', 
//    credentials:true,            //access-control-allow-credentials:true
//    optionSuccessStatus:200,
// }
// app.use(cors(corsOptions))
// const lists = require("./lists.json")
// app.use(express.json())
// const PORT = process.env.PORT || 5000

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

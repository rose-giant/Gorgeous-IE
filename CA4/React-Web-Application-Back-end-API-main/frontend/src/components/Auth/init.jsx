import React, { useContext, useEffect, useState } from "react"
import Login from "./login"
import Register from "./register"
import "./init.css"
import axios from "axios"
import { Context } from "../../App"

export default function InitUser() {
    const [userState, setUserState] = useState()
    const [activeButton, setActiveButton] = useState('login')
    const [users, setUsers] = useState([])
    const [signedIn, setSignedIn] = useContext(Context)

    const handleLoginClick = () => {
        setUserState("login")
        setActiveButton("login")
    }

    const handleRegisterClick = () => {
        setUserState("register")
        setActiveButton("register")
    }

    const user = {
        username: "razi",
        password: "bazi",
        email:"razibazi@sazi",
        address: {
            city: "save",
            country: "neverland"
        }
    }

    useEffect(() => {
        axios.get("http://91.107.137.117:55/users")
          .then(response => {
            setUsers(response.data);
          })
          .catch(error => {
            console.error("Error fetching restaurants:", error);
          });
          
          setUsers([user])
          setSignedIn("razi")
      }, [])

    return(
        <>
        <div className="activators">
            <button
                className={activeButton === 'login' ? 'activeLogin' : 'inactiveButton'}
                onClick={handleLoginClick} >
                Login
            </button>

            <button
                className={activeButton === 'register' ? 'activeRegister' : 'inactiveButton'}
                onClick={handleRegisterClick}>
                Register
            </button>
        </div>

        {userState === "login" ? <Login /> : <Register />}  
        </>
    )
}
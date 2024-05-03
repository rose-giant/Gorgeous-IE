import React from "react"

export default function Login() {

    return(
        <>
            <form className="form-cotrol" action="">
                <label htmlFor="username">Username</label>
                <input className="input-group" type="text" />

                <label htmlFor="password">Password</label>
                <input className="input-group" type="password" />

                <button type="submit">Login</button>
            </form>
        </>
    )
}
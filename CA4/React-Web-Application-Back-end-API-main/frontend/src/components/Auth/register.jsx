import React from "react"

export default function Register() {
    return(
        <>
             <form className="form-cotrol" action="">
                <label htmlFor="username">Username</label>
                <input className="input-group" type="text" />

                <label htmlFor="email">Email</label>
                <input className="input-group" type="email" />

                <label htmlFor="country">Country</label>
                <input className="input-group" type="text" />

                <label htmlFor="city">City</label>
                <input className="input-group" type="text" />

                <label htmlFor="password">Password</label>
                <input className="input-group" type="password" />

                <div className="radios">
                        <label htmlFor="I'm a new Customer">Customer</label>
                        <input className="input-group" type="radio" id="c"/>

                        <label htmlFor="Manager">Manager</label>
                        <input className="input-group" type="radio" id="m"/>
                </div>
                <button type="submit">Register</button>
            </form>
        </>
    )
}
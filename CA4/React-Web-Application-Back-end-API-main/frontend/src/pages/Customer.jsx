import React from "react"
import Customer from "../components/User/customer"

export default function UserCustomer({user}) {
    return(
        <>
            <Customer user={user}/>
        </>
    )
}
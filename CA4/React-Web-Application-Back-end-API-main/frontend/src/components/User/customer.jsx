import React from "react"
import "./customer.css"

export default function Customer({user}) {
    return(
        <>
            <section class="container main">
                <div class="inline-cell notif">
                    <div class="content">
                        <div className="left">
                            <p class="text">Your reservations are also emailed to </p>
                            &nbsp;
                            <a class="red" href={"mailto:"+user.email}> {user.email} </a>
                        </div>
                        
                        <div className="right">
                            <p class="address">
                            Address: {user.address.city},&nbsp;{user.address.country}
                            </p>
                            <button>Logout</button>
                        </div>
                    </div>
                </div>

                <div class="reservations">
                    <table class="table center">
                        <thead>
                            <tr class="header">
                            <th colspan="5">My Reservations</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                            <td class="green">2024-06-22 16:00</td>
                            <td>
                                <a class="red" href="./../Restaurant/rest.html">Ali Daei Dizy</a>
                            </td>
                            <td class="green">Table-12</td>
                            <td class="green">4 Seats</td>
                            <td>
                                <a class="red" href="customerSecondPage.html">Cancel</a>
                            </td>
                            </tr>
                            <tr>
                                <td class="gray">2024-02-22 16:00</td>
                                <td>
                                    <a class="red" href="./../Restaurant/rest.html">Ali Daei Dizy</a>
                                </td>
                                <td class="gray">Table-12</td>
                                <td class="gray">4 Seats</td>
                                <td>
                                    <a class="red" href="customerSecondPage.html">Add Comment</a>
                                </td>
                            </tr>
                            <tr>
                                <td class="gray">2024-02-22 16:00</td>
                                <td>
                                    <a class="red" href="./../Restaurant/rest.html">Ali Daei Dizy</a>
                                </td>
                                <td class="gray">Table-12</td>
                                <td class="gray">4 Seats</td>
                                <td>
                                    <a class="red" href="customerSecondPage.html">Add Comment</a>
                                </td>
                            </tr>
                            <tr>
                                <td class="gray">2024-02-22 16:00</td>
                                <td>
                                    <a class="red" href="./../Restaurant/rest.html">Ali Daei Dizy</a>
                                </td>
                                <td class="gray">Table-12</td>
                                <td class="gray">4 Seats</td>
                                <td>
                                    <a class="red" href="customerSecondPage.html">Add Comment</a>
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
            </section>
        </>
    )
}
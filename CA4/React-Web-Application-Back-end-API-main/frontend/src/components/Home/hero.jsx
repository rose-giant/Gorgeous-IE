import React from "react"

export default function Hero() {
    return(
        <div className="container">
            <div class="hero">
                    <img class="cover" src="./covers/pizzaaa.png" alt=""/>
                    <div class="inner-main">
                        <img class="logo" src="./logo/logo.png" alt=""/>

                        <div class="bottom-nav">
                            <form action="./Search/Search.html" method="">
                                <select name="Location" id="location" >
                                    <option>America</option>
                                    <option>Europe</option>
                                    <option>Asia</option>
                                    <option>Africa</option>
                                </select>
                                <select name="Restaurant" id="restaurant">
                                    <option>Asian</option>
                                    <option>American</option>
                                    <option>European</option>
                                    <option>African</option>
                                </select>
                                <input type="text" placeholder="type restaurant..." id="search" />
                                <button class="search-btn">Search</button>
                            </form>
                        </div>
                    </div>
            </div>
        </div>
    )
}
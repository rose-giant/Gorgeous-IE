import React from "react"
import Nav from './../components/Nav/nav'
import Hero from './../components/Home/hero'
import "./../components/Home/home.css"
import RestaurantList from "./../components/Home/restaurantlist"

export default function HomePage() {
    return (
        <>
        <div className="homepage">
            <Nav />
            <Hero />
            <RestaurantList />
        </div>
            
        </>
    )
}
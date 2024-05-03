import React, { useEffect, useState } from "react"
import axios from "axios"
import RestaurantCard from "./restaurantcard"
import RestaurantDetail from "../Restaurant/detail"

export default function RestaurantList() {
    const [restaurants, setRestaurants] = useState([])
    const restaurant = {
        address: {
          city: "Pittsburgh",
          country: "US",
          street: "620 William Penn Place"
        },
        description: "At our gastropub, we don't distinguish between commoners and kings; we just want to feed the good people of Pittsburgh. In the restaurant, seasonal menus add a modern flair to classic comforts, complemented by a robust selection of local beers and craft spirits. It's all served in an industrial-inspired setting in downtown Pittsburgh. Come and join us for an uncommonly good time.",
        endTime: "23:00",
        image: "https://resizer.otstatic.com/v2/photos/xlarge/1/31676318.webp",
        managerUsername: "ali",
        name: "The Commoner",
        startTime: "07:00",
        type: "American"}

    useEffect(() => {
        axios.get("http://localhost:8080/restaurants")
          .then(response => {
            setRestaurants(response.data);
          })
          .catch(error => {
            console.error("Error fetching restaurants:", error);
          });
          
          // setRestaurants([restaurant])
      }, restaurants, restaurant)

    return(
        <div>
        <ul>
          {restaurants.map((restaurant, index) => (
            <li key={index}>
              {/* <RestaurantCard restaurant={restaurant} /> */}
              <RestaurantDetail restaurant={restaurant}/>
            </li>
          ))}
      </ul>
      </div>
    )
}
import React from "react"
import RestaurantDetail from "../components/Restaurant/detail"

export default function RestaurantPage({restaurant}) {
    //the router must contain the restaurant name and the name will
    //be found from the api list:
    const [restaurant, setRestaurant] = useState()
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
        axios.get("http://91.107.137.117:55/restaurants")
          .then(response => {
            response.data.forEach(restaurant => {
                if (restaurant.name === "raz") {
                  console.log("Found Raz!");
                  setRestaurant(restaurant)
                }
            })
          })
          .catch(error => {
            console.error("Error fetching restaurants:", error);
          });
          
      }, restaurant)

    return(
        <>
            <RestaurantDetail restaurant={restaurant} />
        </>
    )
}
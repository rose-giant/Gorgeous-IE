import React, { useState } from 'react'
import ReviewList from "./components/Reviews/reviewList"
import InitUser from './components/Auth/init'
import 'bootstrap/dist/css/bootstrap.css'
// import Manager from './components/User/Manager'
import Nav from './components/Nav/nav'
import Footer from './components/Footer/footer'
import RestaurantDetail from "./components/Restaurant/detail"
import RestaurantList from "./components/Home/restaurantlist"

export const Context = React.createContext()

const App = () => {
  const [signedIn, setSignedIn] = useState("")
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
    
  return (
    <Context.Provider value={[signedIn, setSignedIn]} className="App">
      <Nav />
      {/* <ReviewList restaurantName={"The Commoner"}/> */}
      {/* <InitUser />
      <Manager /> */}
      <RestaurantList />
      {/* <RestaurantDetail restaurant={restaurant}/> */}
      <Footer />
    </Context.Provider>
  );
};

export default App;

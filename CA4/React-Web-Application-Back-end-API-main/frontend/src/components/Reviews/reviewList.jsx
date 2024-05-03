import React from "react"
import ReviewItem from "./reviewItem"
import axios from "axios"
import { useState, useEffect } from "react"
import "./reviews.css"
import ReviewModal from "./../Helpers/testmodal"

export default function ReviewList({ restaurantName }) {
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [reviews, setreviews] = useState([])
    const review = {
        ambianceRate:4,
        comment:"Enjoyed a pleasant dinner at Sullivan's. The steak was cooked perfectly, and the service was attentive. The ambiance was nice and cozy. Overall, a good experience.",
        foodRate:4,
        overallRate:4,
        restaurantName:"The Commoner",
        serviceRate:3,
        username:"MohammadMehdi_Jafari"
    }

    useEffect(() => {
        axios.get("http://91.107.137.117:55/reviews")
          .then(response => {
            setreviews(response.data)
          })
          .catch(error => {
            console.error("Error fetching reviews:", error);
          });

          setreviews([review])
          setreviews(reviews => {
            return reviews.filter(review => review.restaurantName === restaurantName)
          })
      }, [])

    return(
        <>
            <div class="review-sec">
                <div class="overview">
                    <div class="left">
                        <p class="title">What 160 people are saying</p>
                        <div class="rating">
                            {/* <div class="stars">
                                <img class="staricon" src="./../assets/icons/svg/pinkstar.svg" alt="">
                                <img class="staricon" src="./../assets/icons/svg/pinkstar.svg" alt="">
                                <img class="staricon" src="./../assets/icons/svg/pinkstar.svg" alt="">
                                <img class="staricon" src="./../assets/icons/svg/pinkstar.svg" alt="">
                                <img class="staricon" src="./../assets/icons/svg/star-rate-rating-outline-svgrepo-com.svg" alt="">
                            </div> */}

                            <div class="rating-sub">
                                4 based on recent ratings
                            </div>
                        </div>
                        <button onClick={() => setIsModalOpen(true)}>Add Review</button>
                        <ReviewModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
                    </div>
                </div>

                <div className="reviews">
                <ul>
                    {reviews.map((review, index) => (
                        <li key={index}>
                            <ReviewItem review={review}/>
                        </li>
                    ))}
                </ul>
                </div>
            </div>            
        </>
    )
}
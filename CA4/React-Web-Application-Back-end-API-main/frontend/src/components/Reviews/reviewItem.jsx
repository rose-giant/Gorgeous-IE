import React from "react"
import "./reviews.css"

export default function ReviewItem({review}) {
    return(
        <>
            <div class="left">
                <div class="profile">
                    {review.username[0]}
                </div>

                <div class="comment">
                    <p class="username">
                        {review.username}
                    </p>

                    <ul class="review-detail">
                        <li>Overall &nbsp;<span>{review.overallRate}</span>.</li>
                        <li>Food &nbsp;<span>{review.foodRate}</span>.</li>
                        <li>Service &nbsp;<span>{review.serviceRate}</span>.</li>
                        <li>Ambiance &nbsp;<span>{review.ambianceRate}</span></li>
                    </ul>

                    <p class="comment-body">
                        {review.comment}
                    </p>
                </div>
            </div>

            <div class="right">
                {/* <div class="stars">
                    <img class="staricon" src="./../assets/icons/svg/pinkstar.svg" alt="">
                    <img class="staricon" src="./../assets/icons/svg/pinkstar.svg" alt="">
                    <img class="staricon" src="./../assets/icons/svg/pinkstar.svg" alt="">
                    <img class="staricon" src="./../assets/icons/svg/pinkstar.svg" alt="">
                    <img class="staricon" src="./../assets/icons/svg/star-rate-rating-outline-svgrepo-com.svg" alt="">
                </div> */}

                <p class="date">
                    Dined on February 17, 2024
                </p>
            </div>
        </>
    )
}
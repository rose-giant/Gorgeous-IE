import React from "react"

const ReviewModal = ({ isOpen, onClose }) => {
    if (!isOpen) return null;
    return (
      <div
        style={{
          position: 'fixed',
          top: 0,
          left: 0,
          width: '100%',
          height: '100%',
          backgroundColor: 'rgba(0, 0, 0, 0.5)',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <div
          style={{
            backgroundColor: 'white',
            padding: '20px',
            borderRadius: '8px',
            maxWidth: '80%',
          }}
        >
          <button onClick={onClose} style={{ float: 'right' }}>Close</button>
          <h2>Add Review</h2>
          {/* Add your review form or content here */}
        </div>
      </div>
    );
  };

export default ReviewModal
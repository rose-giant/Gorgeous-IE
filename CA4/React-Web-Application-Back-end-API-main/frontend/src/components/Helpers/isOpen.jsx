
export const IsOpen = (start, end) => {
        const currentTime = new Date();
        const startTime = new Date(currentTime.toDateString() + " " + start);
        const endTime = new Date(currentTime.toDateString() + " " + end);
        return currentTime >= startTime && currentTime <= endTime;
}

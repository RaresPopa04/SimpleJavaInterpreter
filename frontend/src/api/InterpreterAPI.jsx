import axios from "axios"

const axiosInstance = axios.create({
    baseURL: "http://localhost:8081/api",
    headers: {
        "Content-Type": "application/json"
    }
});

export const executeProgram = async (code) =>{
    const response = await axiosInstance.post("/interpret", {code})
    return response.data
}
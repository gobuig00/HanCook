import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Profile.css';
import Donut from '../Donut';
import Table from '../Table';
import { useNavigate } from 'react-router-dom';
import Footer from '../Footer';

export default function Profile() {
    const navigate = useNavigate();
    const [profile, setProfile] = useState({});

    const newKey = (keyList, valueList) => {
        let newKeyList = [];
        for (let i = 0; i < keyList.length; i++) {
            let strKey = `${keyList[i]} ${valueList[i]}%`;
            newKeyList.push(strKey);
        }
        return newKeyList;
    };

    const getToday = () => {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        return today;
    };
      
    const isWithinLastWeek = (date) => {
        const today = getToday();
        const oneWeekAgo = new Date(today - 7 * 24 * 60 * 60 * 1000);
        return date >= oneWeekAgo && date <= today;
    };
    
    const createProfile = (responseData) => {
        const today = getToday();
        const todaysData = responseData.filter((entry) => new Date(entry.createdAt) >= today);
      
        const totalCalories = todaysData.reduce((total, entry) => total + entry.kcal, 0);
        const nutrition = {
            carbs: todaysData.reduce((total, entry) => total + entry.carb, 0),
            protein: todaysData.reduce((total, entry) => total + entry.protein, 0),
            fat: todaysData.reduce((total, entry) => total + entry.fat, 0),
        };
        const other = {
            'cholesterol (mg)': todaysData.reduce((total, entry) => total + entry.cholesterol, 0),
            'sugar (g)': todaysData.reduce((total, entry) => total + entry.sugar, 0),
        };
      
        const lastWeeksData = responseData.filter((entry) => isWithinLastWeek(new Date(entry.createdAt)));
        const ingestedFood = lastWeeksData.reduce((acc, entry) => {
            const kcalPer100g = entry.kcal / (entry.servingSize / 100);
            acc[entry.foodName] = kcalPer100g;
            return acc;
        }, {});
      
        return {
            name: responseData[0].user.name,
            totalCalories,
            nutrition,
            other,
            ingestedFood,
        };
    };
      

    useEffect(() => {
        fetchProfile();
        console.log(profile);
    }, [profile]);

    const fetchProfile = async () => {
        try {
        const token = localStorage.getItem("hancook-token");

        if (token) {
            const response = await axios.get("http://localhost:8080/record/get", {
                headers: {
                    'Authorization': `Bearer ${token}`,
                }
            });
            
            console.log(response.data)
            setProfile(createProfile(response.data));
        }
        } catch (error) {
            // navigate('/login')
        }
    };
    
    return (
        <div className='profile-container'>
            {profile.name ? (
                <>
                    <div className='profile-header'>
                        <h1>{profile.name}</h1><p>'s Profile</p>
                    </div>
                    <div className='profile-section'>
                        <h2>Daily Record</h2>
                        <div className='profile-calories'>
                            <span className='profile-sub-title'> kcal / day</span>
                            <div style={{margin: '3vh'}}>
                                <span className='profile-big-font'>{profile.totalCalories}</span>
                                <span className='profile-kcal'>kcal</span>
                            </div>
                        </div>
                        <div className='profile-nutrition'>
                                {/* <Donut
                                    keyList = {newKey(Object.keys(profile.nutrition), Object.values(profile.nutrition) )}
                                    valueList = {Object.values(profile.nutrition)}
                                    title = 'Daily Nutrition Data'
                                    // centerText ='210kcal'
                                /> */}
                        </div>
                        <div className='profile-other'>
                            <div className='profile-sub-title'>Other Ingredients</div>
                            <Table
                                body={profile.other}
                            />
                        </div>
                    </div>
                    <div className='profile-section'>
                        <h2>Ingested Food</h2>
                        <div className='profile-ingested-food'>
                            <p htmlFor="ingestedTable">(kcal/100g)</p>
                            <Table
                                head= {['000','8999']}
                                body={profile.ingestedFood}
                            />
                        </div>
                    </div>
                    <footer>
                        <Footer />
                    </footer>
                </>
            ) : (
                <div>Loading...</div>
            )}
        </div>
    );    
};

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

    const getToday = () => {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        return today;
    };
      
    const isWithinLastWeek = (date) => {
        const today = getToday();
        const oneWeekAgo = new Date(today - 7 * 24 * 60 * 60 * 1000);
        return date >= oneWeekAgo && date < new Date(today.valueOf() + 24 * 60 * 60 * 1000);
    };
    
    const createProfile = (responseData) => {
        const today = getToday();
        const todaysData = responseData.filter((entry) => new Date(entry.eatDate) >= today);
          
        const totalCalories = todaysData.reduce((total, entry) => total + entry.kcal, 0);
        const nutrition = {
            carbs: todaysData.reduce((total, entry) => total + entry.carb, 0),
            protein: todaysData.reduce((total, entry) => total + entry.protein, 0),
            fat: todaysData.reduce((total, entry) => total + entry.fat, 0),
        };
        const other = {
            'cholesterol (mg)': todaysData.reduce((total, entry) => total + entry.cholesterol, 0),
            'sugar (g)': todaysData.reduce((total, entry) => total + entry.sugar, 0),
            'salt (mg)': todaysData.reduce((total, entry) => total + entry.salt, 0),
        };
        
        const lastWeeksData = responseData
            .filter((entry) => isWithinLastWeek(new Date(entry.eatDate)))
            .sort((a, b) => new Date(b.eatDate) - new Date(a.eatDate)); // 최신 날짜가 먼저 오도록 정렬

        const ingestedFood = lastWeeksData.map((entry) => {
            const kcalPer100g = entry.kcal / (entry.servingSize / 100);
            const foodName = entry.foodName;
            return [foodName, kcalPer100g];
        });

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
    }, []);

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
            navigate('/login')
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
                                <Donut
                                    keyList = {Object.keys(profile.nutrition)}
                                    valueList = {Object.values(profile.nutrition)}
                                    title = 'Daily Nutrition Data'
                                    // centerText ='210kcal'
                                />
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
                            <Table
                                head={['No.', 'Dish', 'kcal/100g']}
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

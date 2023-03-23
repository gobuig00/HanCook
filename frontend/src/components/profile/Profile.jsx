import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Profile.css';
import Donut from '../Donut';
import Table from '../Table';

export default function Profile() {
    const profile = {
        name : 'Tom',
        totalCalories : 2184,
        nutrition : {
            carbonhydrate: 50,
            protein: 40,
            fat: 10,
        },
        other : {
            cholesterol: 42.28,
            salt: 3828,
            sugar: 156.4
        },
        ingestedFood : {
            food1 : 120.51,
            food2 : 185.67,
            food3 : 60.56,
            food4 : 120.55,
        }
    }
    
    // const [profile, setProfile] = useState({  });

    // useEffect(() => {
    //     fetchProfile();
    // }, []);

    // const fetchProfile = async () => {
    //     try {
    //     const token = localStorage.getItem("authToken");

    //     if (token) {
    //         const response = await axios.get("http://localhost:8080/profile", {
    //         headers: {
    //             Authorization: `Bearer ${token}`,
    //         },
    //         });

    //         setProfile(response.data);
    //     }
    //     } catch (error) {
    //     console.error("Error fetching profile:", error);
    //     }
    // };

    return (
        <div className='profile-container'>
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
                    <div className='profile-sub-title'>Daily Nutrition Data</div>
                    <Donut 
                        
                    />
                </div>
                <div className='profile-other'>
                    <div className='profile-sub-title'>Other Ingredients</div>
                    <Table/>
                </div>
            </div>
            <div className='profile-section'>
                <h2>Ingested Food</h2>
                <div className='profile-ingested-food'>
                    <p htmlFor="ingestedTable">(kcal/100g)</p>
                    <Table label="(kcal/100g)"/>
                </div>
            </div>
        </div>
    );
};


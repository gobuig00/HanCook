import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Profile.css';
import Donut from '../Donut';
import Table from '../Table';
import { Tab } from 'react-bootstrap';

export default function Profile() {
    const [profile, setProfile] = useState({  });

    useEffect(() => {
        fetchProfile();
    }, []);

    const fetchProfile = async () => {
        try {
        // 인증 토큰을 가져옵니다. (로컬 스토리지, 쿠키 등에서)
        const token = localStorage.getItem("authToken");

        // 토큰이 있는 경우에만 요청을 보냅니다.
        if (token) {
            const response = await axios.get("http://localhost:8080/profile", {
            headers: {
                Authorization: `Bearer ${token}`,
            },
            });

            setProfile(response.data);
        }
        } catch (error) {
        console.error("Error fetching profile:", error);
        }
    };

    return (
        <div className='profile-container'>
            <div className='profile-header'>
                <span className=''><h1>{profile.name}</h1>'s Profile</span>
            </div>
            <div className='profile-daily-record'>
                <h2>Daily Record</h2>
                <div className='profile-calories'>
                    <span className='profile-sub-title'> kcal / day</span>
                    <span className='profile-big-font'>{profile.totalCalories}</span>
                    <span className='profile-kcal'>kcal</span>
                </div>
                <div className='profile-nutrition'>
                    <div className='profile-sub-title'>Daily Nutrition Data</div>
                    <Donut />
                </div>
                <div className='profile-other'>
                    <div className='profile-sub-title'>Other Ingredients</div>
                    <Table/>
                </div>
            </div>
            <div className='profile-ingested-food'>
                <div className='profile-sub-title'>Ingested Food</div>
                <label htmlFor="ingestedTable">(kcal/100g)</label>
                <Table label="(kcal/100g)"/>
            </div>
        </div>
    );
};


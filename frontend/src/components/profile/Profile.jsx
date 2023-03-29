import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Profile.css';
import Donut from '../Donut';
import Table from '../Table';
import { useNavigate } from 'react-router-dom';
import Footer from '../Footer';

export default function Profile() {
    const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('hancook-token');
    if (!token) {
      navigate('/login')
    }
  }, [navigate]);

    const profile = {
        name : 'Tom',
        totalCalories : 2184,
        nutrition : {
            carbs: 50,
            protein: 40,
            fat: 10,
        },
        other : {
            'cholesterol (mg)': 42.28,
            'salt (mg)': 3828,
            'sugar (g)': 156.4
        },
        ingestedFood : {
            food1 : 120.51,
            food2 : 185.67,
            food3 : 60.56,
            'seafood Spring Onion Pancake' : 120.55,
        }
    }
    
    const newKey = (keyList, valueList) => {
        let newKeyList = [];
        for (let i = 0; i < keyList.length; i++) {
          let strKey = `${keyList[i]} ${valueList[i]}%`;
          newKeyList.push(strKey);
        }
        return newKeyList;
    };

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
                        <Donut
                            keyList = {newKey(Object.keys(profile.nutrition), Object.values(profile.nutrition) )}
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
                    <p htmlFor="ingestedTable">(kcal/100g)</p>
                    <Table
                        body={profile.ingestedFood}
                    />
                </div>
            </div>
            <footer>
                <Footer />
            </footer>
        </div>
    );
};

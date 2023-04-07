import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Profile.css';
import Donut from '../Donut';
import Table from '../Table';
import { useNavigate } from 'react-router-dom';
import Footer from '../Footer';
import Button from 'react-bootstrap/Button';
  


export default function Profile() {
    const navigate = useNavigate();
    const [profile, setProfile] = useState(null);

    useEffect(() => {
    const token = localStorage.getItem('hancook-token');
    if (!token) {
      navigate('/login')
    }
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('hancook-token')}`,
        'Content-Type': 'application/json',
      };
    axios.get(`${process.env.REACT_APP_API_URL}/record/get`, { headers: headers })
              .then((response) => {
            const responseData = response.data
              const today = getToday();
              const todaysData = responseData.filter((entry) => new Date(entry.eatDate) >= today);
              console.log(todaysData)
              
              const totalCalories = todaysData.reduce((total, entry) => total + entry.kcal, 0);
              const nutrition = {
                  carbs: todaysData.reduce((total, entry) => total + entry.carb, 0),
                  fat: todaysData.reduce((total, entry) => total + entry.fat, 0),
                  protein: todaysData.reduce((total, entry) => total + entry.protein, 0),
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
              setProfile({
                name: responseData[0].user.name,
                totalCalories,
                nutrition,
                other,
                ingestedFood,
            })
            console.log(profile)
            }).catch (() => {
                console.log('err');
              })
  }, []);

    const getToday = () => {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        return today;
    };

    const logOut = () => {
        localStorage.removeItem('hancook-token');
        navigate('/login')
    }

    // function useFetchProfile() {
    //     const headers = {
    //       'Authorization': `Bearer ${localStorage.getItem('hancook-token')}`,
    //       'Content-Type': 'application/json',
    //     };
      
    //     useEffect(() => {
    //           axios.get(`${process.env.REACT_APP_API_URL}/record/get`, { headers: headers })
    //           .then(res=>createProfile(res.data))
    //           .catch (() => {
    //             console.log('err');
    //           })
    //         })
    //   }
      
    const isWithinLastWeek = (date) => {
        const today = getToday();
        const oneWeekAgo = new Date(today - 7 * 24 * 60 * 60 * 1000);
        return date >= oneWeekAgo && date < new Date(today.valueOf() + 24 * 60 * 60 * 1000);
    };
    
    // const createProfile = (responseData) => {
    //         const today = getToday();
    //         const todaysData = responseData.filter((entry) => new Date(entry.eatDate) >= today);
    //         console.log(todaysData)
            
    //         const totalCalories = todaysData.reduce((total, entry) => total + entry.kcal, 0);
    //         const nutrition = {
    //             carbs: todaysData.reduce((total, entry) => total + entry.carb, 0),
    //             fat: todaysData.reduce((total, entry) => total + entry.fat, 0),
    //             protein: todaysData.reduce((total, entry) => total + entry.protein, 0),
    //         };
    //         const other = {
    //             'cholesterol (mg)': todaysData.reduce((total, entry) => total + entry.cholesterol, 0),
    //             'sugar (g)': todaysData.reduce((total, entry) => total + entry.sugar, 0),
    //             'salt (mg)': todaysData.reduce((total, entry) => total + entry.salt, 0),
    //         };
            
    //         const lastWeeksData = responseData
    //             .filter((entry) => isWithinLastWeek(new Date(entry.eatDate)))
    //             .sort((a, b) => new Date(b.eatDate) - new Date(a.eatDate)); // 최신 날짜가 먼저 오도록 정렬

    //         const ingestedFood = lastWeeksData.map((entry) => {
    //             const kcalPer100g = entry.kcal / (entry.servingSize / 100);
    //             const foodName = entry.foodName;
    //             return [foodName, kcalPer100g];
    //         });

    //         console.log({
    //             name: responseData[0].user.name,
    //             totalCalories,
    //             nutrition,
    //             other,
    //             ingestedFood,
    //         })

    //         return {
    //             name: responseData[0].user.name,
    //             totalCalories,
    //             nutrition,
    //             other,
    //             ingestedFood,
    //         };
    // };
    
    
    return (
        <div className='profile-container'>
            {/* {profile !== null ? (
                <> */}
                    <div className='profile-header'>
                        {profile ? (<>
                        <h1>{profile.name}</h1><p>'s Profile</p>
                        </>) : ('')
                    }
                    </div>
                    <div className='profile-section'>
                        <h2>Daily Record</h2>
                        <div className='profile-calories'>
                            <span className='profile-sub-title'> kcal / day</span>
                            <div style={{margin: '3vh'}}>
                                {profile ? (<span className='profile-big-font'>{profile.totalCalories}</span>) : ('')}
                                
                                <span className='profile-kcal'>kcal</span>
                            </div>
                        </div>
                        <div className='profile-nutrition'>
                            {profile ? (<Donut
                                keyList = {['carbs', 'fat', 'protein']}
                                valueList = {profile.nutrition.carbs ? Object.values(profile.nutrition) : [0, 0, 0]}
                                title = {profile.nutrition.carbs ? 'Daily Nutrition Data' : 'There is No Nutrition Data'}
                                centerText ={profile.nutrition.carbs ? `${profile.totalCalories} kcal` : ''}
                            />) : ('')}
                            
                        </div>
                        <div className='profile-other'>
                            <div className='profile-sub-title'>Other Ingredients</div>
                            {profile ? (<Table
                                body={profile.other}
                            />) : ('')}
                            
                        </div>
                    </div>
                    <div className='profile-section'>
                        <h2>Ingested Food</h2>
                        <div className='profile-ingested-food'>
                            {profile ? (<Table
                                head={['No.', 'Dish', 'kcal/100g']}
                                body={profile.ingestedFood}
                            />) : ('')}
                            
                        </div>
                    </div>
                    <div className='logout-button-container'>
                        <Button variant="danger" className='logout-button' onClick={logOut}>Log Out</Button>
                    </div>
                    <footer>
                        <Footer />
                    </footer>
                {/* </> */}
            {/* ) : (
                <div>Loading...</div>
            ) */}
        </div>
        
    );    
};

import { React, useState, useEffect } from 'react';
import './SignUp.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import logo from '../../images/logo.png';

export default function SignUp() {
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('hancook-token');
        if (token) {
            navigate('/main')
        }
    }, [navigate]);

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordCheck, setPasswordCheck] = useState('');
    const [match, setMatch] = useState(true);
    const [name, setName] = useState('');
    const [gender, setGender] = useState('FEMALE');
    const [genderClick, setGenderClick] = useState(false)
    
    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
        setMatch(event.target.value === passwordCheck);
    };

    const handlePasswordCheckChange = (event) => {
        setPasswordCheck(event.target.value);
        setMatch(event.target.value === password);
    };

    const handleNameChange = (event) => {
        setName(event.target.value);
    };
    const handleGenderClick = (event) => {
        setGenderClick(!genderClick);
        if (genderClick) {
            setGender('MALE')
        } else {
            setGender('FEMALE')
        }
    }

    const handleGenderChange = (event) => {
        setGender(event.target.value);
    };

    const moveToMain = () => {
        navigate('/')
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        const data = {
            email,

        }
        try {
            const response = await axios.post(`${process.env.REACT_APP_API_URL}/user/join`, {
                email,
                password,
                name,
                gender,
            });

            if (response.status === 201) {
                // 회원가입 성공 시 할 일
   
                navigate('/login');
            } else {
                // 회원가입 실패 시 할 일

            }
        } catch (error) {
           
        }

       
    }
    return (
        <div className="signup-container">
            <div className='signup-header'>
                <img src={logo} className='signup-logo' alt="로고" onClick={moveToMain}/>
            </div>
            <div className="signup-inside">
                <h1 className="signup-title">Sign up</h1>
                <form onSubmit={handleSubmit} className="signup-form">
                    <div className='signup-inner-form'>
                        <i className="material-icons">alternate_email</i>
                        <input
                            className='signup-custom-placeholder'
                            type="email"
                            id="email"
                            name="email"
                            placeholder='Enter your Email'
                            value={email}
                            onChange={handleEmailChange}
                            required
                        />
                    </div>
                    <div className='signup-inner-form'>
                        <i className="material-icons">person</i>
                        <input
                            className='signup-custom-placeholder'
                            type="text"
                            id="name"
                            name="name"
                            placeholder='Enter your name'
                            value={name}
                            onChange={handleNameChange}
                            required
                        />
                    </div>
                    <div className='signup-inner-form'>
                        <i className="material-icons">wc</i>
                        <input
                            className='signup-custom-placeholder'
                            type="text"
                            id="gender"
                            name="gender"
                            placeholder='Select your gender'
                            value={gender}
                            onClick={handleGenderClick}
                            onChange={handleGenderChange}
                            required
                            readOnly
                        />
                    </div>
                    <div className='signup-inner-form'>
                        <i className='material-icons'>vpn_key</i>
                        <input
                            className='signup-custom-placeholder'
                            type="password"
                            id="password"
                            name="password"
                            placeholder='Enter your PW'
                            value={password}
                            onChange={handlePasswordChange}
                            required
                        />
                    </div>
                    <div className='signup-inner-form'>
                        <i className='material-icons'>done_all</i>
                        <input
                            className='signup-custom-placeholder'
                            type="password"
                            id="password-check"
                            name="password-check"
                            placeholder='Enter your PW again'
                            value={passwordCheck}
                            onChange={handlePasswordCheckChange}
                            required
                        />
                        {!match && ( <p className='warning-text'>Passwords do not match!</p> )}
                    </div>
                    
                    <button type="submit" className='signup-btn'>
                        <i className='material-icons'>group_add</i>
                        JOIN NOW
                    </button>
                    
                </form>
            </div>

            <hr/>

            <div className="signup-inside">
                <div className='signup-lower'>
                    If you have an account,  <a href="login" className='no-underline'>Sign In</a>
                </div>
            </div>
        </div>
    );
}


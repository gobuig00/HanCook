import { React, useState, useEffect } from 'react';
import axios from 'axios';
import './Login.css';
import { useNavigate } from 'react-router-dom';
import logo from '../../images/logo.png';
import Toast from 'react-bootstrap/Toast';
import ToastContainer from 'react-bootstrap/ToastContainer';

export default function Login() {
    const navigate = useNavigate();
    const [show, setShow] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('hancook-token');
        if (token) {
            navigate('/main')
        }
    }, [navigate]);

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post(`${process.env.REACT_APP_API_URL}/user/login`, {
                email,
                password,
            });

            if (response.status === 200) {
                // 로그인 성공 시 할 일
                localStorage.setItem('hancook-token', response.data.accessToken);
                navigate('/main');
            } else {
                // 로그인 실패 시 할 일
                setShow(true)
            }
        } catch (error) {
            console.error('로그인 오류 : ', error);
        }
    };

    const handleClickJoinBtn = () => {
        navigate('/SignUp');
    }
    

    return (
        <div className="login-container">
            <div className='login-header'>
                <img src={logo} className='login-logo' alt="로고" />
            </div>
            <div className="login-inside">
                <h1 className="login-title">Sign In</h1>
                <form onSubmit={handleSubmit} className="login-form">
                    <div className='login-inner-form'>
                        <i className="material-icons">alternate_email</i>
                        <input
                            className='login-custom-placeholder'
                            type="email"
                            id="email"
                            name="email"
                            placeholder='Enter your Email'
                            value={email}
                            onChange={handleEmailChange}
                            required
                        />
                    </div>
                    <div className='login-inner-form'>
                        <i className='material-icons'>vpn_key</i>
                        <input
                            className='login-custom-placeholder'
                            type="password"
                            id="password"
                            name="password"
                            placeholder='Enter your PW'
                            value={password}
                            onChange={handlePasswordChange}
                            required
                        />
                    </div>
                    <a href="#" className='login-no-underline'>forgot PW?</a>
                    <button type="submit" className='login-btn'>Sign In</button>
                </form>
            </div>

            <hr/>

            <div className="login-inside">
                <h1 className="login-title">Sign up</h1>
                <label htmlFor="joinBtn">If you don't have an account</label>
                <button id='joinBtn' className='login-btn' onClick={handleClickJoinBtn}>
                    <i className='material-icons'>group_add</i>
                    JOIN NOW FOR FREE
                </button>
            </div>
            <ToastContainer position='bottom-center' className='camera-toast'>
                <Toast onClose={() => setShow(false)} show={show} delay={3000} autohide bg='dark'>
                <Toast.Body>Email or password is incorrect</Toast.Body>
                </Toast>
            </ToastContainer>
        </div>
    );
}


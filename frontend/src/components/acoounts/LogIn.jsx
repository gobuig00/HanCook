import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post("http://localhost:8080/user/login", {
                email,
                passowrd,
            });

            if (response.status === 200) {
                // 로그인 성공 시 할 일
                console.log('로그인 성공', response.data);
                navigate('/main');
            } else {
                // 로그인 실패 시 할 일
                console.log('로그인 실패', response.data);
            }
        } catch (error) {
            console.error('로그인 오류 : ', error);
        }
    };
    

    return (
        <div className="login-container">
            <h1>Sign In</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="email">Email</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    value={email}
                    onChange={handleEmailChange}
                    required
                />
                <label htmlFor="password">Password</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    value={password}
                    onChange={handlePasswordChange}
                    required
                />
                <button type="submit">Sign In</button>
            </form>
        </div>
    );
}


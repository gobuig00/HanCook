import { React, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function SignUp() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [gender, setGender] = useState('');
    const navigate = useNavigate();
    
    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleGenderChange = (event) => {
        setGender(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response = await axios.post("http://localhost:8080/user/join", {
                email,
                password,
                name,
                gender,
            });

            if (response.status === 200) {
                // 회원가입 성공 시 할 일
                console.log('회원가입 성공', response.data);
                navigate('/login');
            } else {
                // 회원가입 실패 시 할 일
                console.log('회원가입 실패', response.data);
            }
        } catch (error) {
            console.error('회원가입 오류 : ', error);
        }

        console.log('회원가입 정보: ', { email, password, name, gender })
    }
    return (
        <div className="signup-container">
            <h1>Sign Up</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="email">E-mail</label>
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

                <label htmlFor="name">Name</label>
                <input
                    type="text"
                    id="name"
                    name="name"
                    value={name}
                    onChange={handleNameChange}
                    required
                />

                <label htmlFor="gender">Gender</label>
                <select id="gender" name="gender" value={gender} onChange={handleGenderChange} required>
                    <option value="">Select</option>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                </select>

                <button type="submit">Join</button>
            </form>
        </div>
    );
}


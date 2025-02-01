import React,{useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './style.css';

import api from '../../services/api'

import logo from '../../assets/webD.png'
import padlock from '../../assets/computer.png'


export default function Login() {

    const[username,setUsername] = useState('');
    const[password,setPassword] = useState('');

    const history = useNavigate();

    async function login(e){
      e.preventDefault();

      const data = {
        username,
        password,
      };

      try {
        const response = await api.post('auth/signin',data);
        console.log(response)
        localStorage.setItem('username',username);
        localStorage.setItem('accessToken',response.data.token);

        history('/books');

      } catch (err) {
        alert('Login failed, Try agains!');  

       }

    };

    return(
      <div className = "login-container">
      <section className="form">
        <img src = {logo}  width = "200px" height =" auto" alt = "Wesllen Logo"/>
            <form onSubmit={login}>
                <h1>Access your Account</h1>
                <input 
                  placeholder='Username'
                  value = {username}
                  onChange = {e => setUsername (e.target.value)}
                />
                <input 
                  type="password" placeholder='Password'
                  value = {password}
                  onChange = {e => setPassword (e.target.value)}
                />

                <button className = "button" type ="submit">Login</button>
            </form>

      </section>
      <img src={padlock} width = "550px" height =" 550px" alt= "Login"/>

      </div>
    ) ;
  }
  
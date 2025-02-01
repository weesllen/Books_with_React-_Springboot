import React,{useState,useEffect} from "react";

import{Link,useNavigate} from 'react-router-dom';
import{FiPower,FiEdit,FiTrash2} from 'react-icons/fi';

import './style.css';
import api from '../../services/api'

import logo from '../../assets/webD.png';

export default function Book(){

    const [books,setBooks] = useState([]);
    const [page,setPage] = useState([]);
    
    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const history = useNavigate();

    async function logout(){
        localStorage.clear();
        history('/');
    }

    async function editBook(id){
        try {
            history(`book/new/${id}`)
        } catch (error) {
            alert('Edit failed, Try again!!!');
        }

    }
    async function deleteBook(id){
        try {
            await api.delete(`api/book/v1/${id}`,{
                headers: {
                    Authorization: `Bearer ${accessToken}`
               }
            })
            setBooks(books.filter(book => book.id !== id))
        } catch (err) {
            alert('Delete failed, Try Again!!');
        }

    }

    async function fetchMoreBooks() {
       const response = await api.get('api/book/v1',{
            headers: {
                Authorization: `Bearer ${accessToken}`
           },
           params: {
            page: page,
            limit: 4,
            direction: 'asc'
           }
    });
         setBooks([...books,...Response.data._embedded.bookVoes])
         setPage(page + 1);
    }


    useEffect(() =>{
        fetchMoreBooks();
    },[])

    return (
        <div className="book-container">
            <header>
                <img src ={logo} width = "90px" height =" auto" alt = "Wesllen"/> 
                <spam> Welcome <strong>Wesllen</strong>!</spam>
                <Link className="button" to= "book/new/0">Add New Book</Link>
                <button onClick={logout} type="button">
                <FiPower size= {18} color="#251FC5"/>
                </button>
            </header>

            <h1> Registered Books </h1>
            <ul>
               {books.map(book => (
                 <li key={book.id}>
                 <strong> Title:</strong>
                 <p>{book.title}</p>
                 <strong> Author:</strong>
                 <p>{book.author}</p>
                 <strong> Price:</strong>
                 <p>{Intl.NumberFormat('pt-BR', {style: 'currency', currency: 'BRL'}).format(book.price)}</p>
                 <strong> Release Date:</strong>
                 <p>{Intl.DateTimeFormat('pt-BR').format(new Date(book.launchDate))}</p>

                 <button onClick={() => editBook(book.id)} type ="button">
                     <FiEdit size={20} color="#251FCS"></FiEdit>
                 </button>

                 <button onClick={() => deleteBook(book.id)} type ="button">
                     <FiTrash2 size={20} color="#251FCS"></FiTrash2>
                 </button>
             
             </li>
               ))}
            </ul>
            <button className="button" onClick={fetchMoreBooks} type="button">Load More</button>
        </div>
    );
}
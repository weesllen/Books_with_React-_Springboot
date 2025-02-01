import React,{useState,useEffect} from 'react';
import {useNavigate,Link,useParams } from 'react-router-dom';
import './style.css';

import api from '../../services/api'

import logo from '../../assets/webD.png';
import { FiArrowLeft } from "react-icons/fi";

export default function NewBook(){

    const[id,setId] = useState(null);
    const[author,setAuthor] = useState('');
    const[launchDate,setLaunchDate] = useState('');
    const[price,setPrice] = useState('');
    const[title,setTitle] = useState('');

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const {bookId} = useParams();

    const history = useNavigate();

    async function loadBook(){
      try {
        const response = await api.get(`api/book/v1/${id}`,{
          headers: {
              Authorization: `Bearer ${accessToken}`
         }
      })
      let adjustDate = response.data.launchDate.slipt("T",10)[0];

      setId(response.data.id);
      setTitle(response.data.title);
      setAuthor(response.data.author);
      setPrice(response.data.price);
      setLaunchDate(adjustDate);

        } catch (error) {
        alert('Recovering Failed, Try again!!!')
        history('/books')
      }
    }

    useEffect(() => {
      if (bookId === '0') return;
       else loadBook();    
    },[bookId])

    async function saveOrUpdate(e){
      e.preventDefault();

      const data = {
        title,
        author,
        launchDate,
        price,
      }

      try {
        if (bookId === '0') {
          await api.post('api/book/v1',data,{ 
          headers: {
               Authorization: `Bearer ${accessToken}`
          }
          });
        }

          else {
          data.id = id;
          await api.put('api/book/v1',data,{ 
            headers: {
                 Authorization: `Bearer ${accessToken}`
            }
          });
        }

        history('/books');

      } catch (error) {
        alert('Error while Recording !! Try again.')
      }
      const authorization = {
        Authorization: `Bearer ${accessToken}`
      }

}
    return(
         <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logo} width="250px" height="auto" alt="Wesllen"/>
                    <h1>{bookId === 0 ? 'Add New ' : 'Update '}Book</h1>
                    <p>Enter the book information and click on {bookId === 0 ? "'Add'" : "'Update'"}!</p>   
                    <Link className="back-link" to ="/books">
                       <FiArrowLeft size={16} color="#251fc5"/> 
                         Home 
                    </Link>                    
                </section>
                <form onSubmit={saveOrUpdate}>
                    <input 
                    placeholder = "Title"
                    value={title}
                    onChange={ e => setTitle(e.target.value)}
                    />
                    <input 
                    placeholder = "Author"
                    value={author}
                    onChange={ e => setAuthor(e.target.value)}
                    />
                    <input 
                    placeholder = "Date"
                    value={launchDate}
                    onChange={ e => setLaunchDate(e.target.value)}
                    />
                    <input 
                    placeholder = "Price"
                    value={price}
                    onChange={ e => setPrice(e.target.value)}
                    />

                    <button className="button" type="submit">Add{bookId === 0 ? 'Add' : 'Update'}</button>
                </form>
            </div>
         </div>
         );
}
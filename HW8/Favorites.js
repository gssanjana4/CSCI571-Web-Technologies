import React, { Component } from 'react';
import FavoritesCard from './FavoritesCard'
import { ToastContainer, toast,Zoom } from 'react-toastify';



export class Favorites extends Component {
    constructor(){
        super();
        this.state = {
            array: [],
            show:false
        }
    }

    
    componentDidMount() {
            var localStorageKeys = Object.keys(localStorage)
            var result = []
            localStorageKeys = localStorageKeys.filter(key => key!=='checked')
            result = localStorageKeys.map((keys) =>
            {
               return JSON.parse(localStorage.getItem(keys));
            });
            this.setState({array:result})
      }
      removeFav = (idremove,title) =>{
        toast('Removing '+title, {
            position: "top-center",
            autoClose: 1800,
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            transition:Zoom,
            bodyClassName: 'toaster'
           });
        localStorage.removeItem(idremove);
        this.componentDidMount();
    }

    render(){
        
        console.log('saa',this.state.array)
        console.log(this.state.array.length,'lengtharray')
        
        return (
            <>     
            {
            this.state.array.length===0?<h4 style={{marginLeft:'.8em',marginTop:'0.55em',textAlign:'center'}}>You have no saved articles</h4>:<h4 style={{marginLeft:'.7em',marginTop:'1em'}}>Favorites</h4>
            }
            <div className={'favGrid'}>

           {this.state.array.map(record => {
                console.log(record.length,'recordlngth')
                return <FavoritesCard record = {record} removeFav = {this.removeFav}/>
            })}
             <ToastContainer />
        
            </div>
           </>
        )
        
    }
}

export default Favorites;

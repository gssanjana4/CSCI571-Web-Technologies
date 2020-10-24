import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Header from './components/layout/Header';
import World from './components/World';
import Sports from './components/Sports';
import Business from './components/Business';
import Technology from './components/Technology';
import Politics from './components/Politics';
import Home from './components/Home';
import Details from './components/Details';
import Favorites from './components/Favorites';
import Search from './components/Search';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

class App extends Component {
  constructor(){
    super();
    this.state = {
      switchChecked:localStorage.getItem('checked')==='false'?false:true
    }
  }
  
  valuesw = (checked) => {
    this.setState({switchChecked:checked});
  }

  render(){
    return (
      <Router>
        <Switch>
          <div className="App">    
          <Header valuesw = {this.valuesw}/>

          <Route exact path = "/" render = {props => (<Home {...props} appstate = {this.state}/>)} />
          <Route path = '/sports' render = {props => (<Sports {...props} appstate = {this.state}/>)}/>
          <Route path = '/business' render = {props => (<Business {...props} appstate = {this.state}/>)}/>
          <Route path = '/technology' render = {props => (<Technology {...props} appstate = {this.state}/>)}/>
          <Route path = '/politics' render = {props => (<Politics {...props} appstate = {this.state}/>)}/>
          <Route path = '/world' render = {props => (<World {...props} appstate = {this.state}/>)}/>
          <Route path="/details" render = {props => (<Details {...props} appstate = {this.state}/>)}/>
          <Route path="/favorites" render = {props => (<Favorites {...props} appstate = {this.state}/>)}/>
          <Route path="/search" render = {props => (<Search {...props} appstate = {this.state}/>)}/>

          </div>      
        
        </Switch>
 
      </Router>
    );
  }
}
  
export default App;

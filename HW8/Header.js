import React, { Component } from 'react';
import {Link,withRouter} from 'react-router-dom'
import {Navbar, Nav} from 'react-bootstrap';
import { FaRegBookmark, FaBookmark} from 'react-icons/fa';
import Switch from "react-switch";
import AsyncSelect from 'react-select/async';
import queryString from 'query-string';
import ReactTooltip from 'react-tooltip'

const loadOptions = async (input) => {
  try {
    const response = await fetch(
      `https://api.cognitive.microsoft.com/bing/v7.0/suggestions?mkt=en-US&q=${input}`,
      {
        headers: {
          "Ocp-Apim-Subscription-Key": "1eb208768ea44369b22b84db66e5479b"
        }
      }
    );
    const data = await response.json();
    const resultsRaw = data.suggestionGroups[0].searchSuggestions;
    const ar = resultsRaw.map(result => (result.displayText));
    const arv = ar.map(v => {
      return <Nav.Link className = "resultRow" as = {Link} to={"/search?q="+v} href={"/search?q="+v} >{v}</Nav.Link>
    })
    return arv.map(v => {
      return {value:v,label:v}
    })
  } catch (error) {
    console.error(`Error fetching search ${input}`);
  }
  
  
}

const selectResult = () => {
  //console.log('here',document.getElementsByClassName("result__option--is-focused")[0].firstChild)
document.getElementsByClassName("result__option--is-focused")[0].firstChild.click();
}

const handleActive = (event) => {
  //console.log(event.target,'asl');
  event.target.classList.add('active')
}
class Header extends Component {
    constructor() {
        super();
        var checkedValue;
        if(localStorage.getItem('checked')==='false')
          checkedValue = false;
        else checkedValue = true;
        this.state = { checked: checkedValue,
        sdisplay:'inherit',
      value : null,
      bookmarkicon:<FaRegBookmark size={20} className="bookmarkc"/>};

      }
      componentDidMount(){
        console.log(this.props.location.pathname)
        const v = ['/details','/search','/favorites'].includes(this.props.location.pathname)?'none':'inherit'
        const f1 = document.getElementsByClassName('active')
        const f = ['/details','/search','/favorites'].includes(this.props.location.pathname)?(f1.length!==0?f1[0].classList.remove('active'):''):''
        this.setState({bookmarkicon:this.props.location.pathname==='/favorites'?<FaBookmark size={20} className="bookmarkc"/>:<FaRegBookmark size={20} className="bookmarkc"/>})
        const vl = this.props.location.pathname==='/search'?queryString.parse(this.props.location.search).q:null
        this.setState({sdisplay:v,value:vl!==null?{label:vl,value:vl}:vl});
      }
      componentDidUpdate(prevProps){
        if(this.props.location.pathname!==prevProps.location.pathname){
          this.setState({bookmarkicon:this.props.location.pathname==='/favorites'?<FaBookmark size={20} className="bookmarkc"/>:<FaRegBookmark size={20} className="bookmarkc"/>})
          const v = ['/details','/search','/favorites'].includes(this.props.location.pathname)?'none':'inherit'
          const f1 = document.getElementsByClassName('active')
          const f = ['/details','/search','/favorites'].includes(this.props.location.pathname)?(f1.length!==0?f1[0].classList.remove('active'):''):''
          this.setState({sdisplay:v,value:null})
        }   
        if(this.props.location.pathname==='/search'){
            if(this.props.location.search!==prevProps.location.search){
              const vl = queryString.parse(this.props.location.search).q;
              this.setState({value:{label:vl,value:vl}})
            }
        }     
      }
      
     
      handleChange = (checked) => {

      this.setState({ checked });
      localStorage.setItem('checked',checked);
      this.props.valuesw(checked);
      }
      render() {    
        return (
        <header>
          <div className="displaycheck">
                <Navbar variant="dark" expand='lg' style={{width:'100%'}}>
                <div className='headerClass'>

                 <AsyncSelect
                    cacheOptions
                    loadOptions={loadOptions}
                    defaultOption = ''
                    placeholder = 'Enter Keyword ..'
                    value = {this.state.value}
                    classNamePrefix = 'result'
                    onChange = {selectResult}
                  />
                </div>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto" defaultActiveKey='/' onClick={handleActive}>
      
                      <Nav.Link as = {Link} to="/" href="/">Home</Nav.Link>
                      <Nav.Link as = {Link} to="/world" href="/world">World</Nav.Link>
                      <Nav.Link as = {Link} to="/politics" href="/politics">Politics</Nav.Link>
                      <Nav.Link as = {Link} to="/business" href="/business">Business</Nav.Link>
                      <Nav.Link as = {Link} to="/technology" href="/technology">Technology</Nav.Link>
                      <Nav.Link as = {Link} to="/sports" href="/sports">Sports</Nav.Link>
                    </Nav>
                   
                  <div style={{width:'100%', display:'inherit', justifyContent:'flex-end'}}>
                  <div >
                   <Nav.Link as = {Link} to="/favorites" className = 'icon_bookmark' href="/favorites" data-tip data-for='toolTipForBookmark' onClick={ReactTooltip.hide()}>
                    {this.state.bookmarkicon}</Nav.Link>   
                  </div>
                  

                    <div  className="displayinherit" style={{display:this.state.sdisplay}} >
                          <p className='c1nyt'>NYTimes</p>
                          <Switch checkedIcon={false} uncheckedIcon={false} onColor={'#2693e6'} offColor={'#dfdfdf'} height={22} width={45} onChange={this.handleChange} checked={this.state.checked} />
                          <p className = 'c1g'>Guardian</p>
                    </div>
                    </div>
                    </Navbar.Collapse>
                    </Navbar>
                    <ReactTooltip id='toolTipForBookmark' place="bottom" type="dark" effect="solid" >
                                    <span>Bookmark</span>
                    </ReactTooltip>
              
          </div>
        </header>
        )
    }
}


 export default withRouter(Header);

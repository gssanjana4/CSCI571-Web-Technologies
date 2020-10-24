import React, { Component } from 'react';
import SearchCard from './SearchCard';
import BounceLoader from "react-spinners/BounceLoader";
import { css } from "@emotion/core";
import queryString from 'query-string';

const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;


export class Search extends Component {
    constructor(){
        super();
        this.state = {
            array: [],
            show: false,
            statusl : false,
            statusd : 'none',
        }
    }
    
    componentDidMount() {
        console.log("checked",this.props.appstate.switchChecked)
        this.setState({array:[],statusl:true,statusd:'block'})
        if(this.props.appstate.switchChecked === false){
            fetch('/api/urlnysearch?articlesearch='+queryString.parse(this.props.location.search).q)
            .then(res =>res.json()).then(array => {
                console.log(array)
                const temp = array.response.docs.map(singleResult => {
                    const filteredValue = singleResult.multimedia.filter(imgvalue => 
                        imgvalue.width>=2000 )
                    return {
                        title:singleResult.headline.main,
                        section:singleResult.news_desk,
                        image: filteredValue[0]===undefined?'https://upload.wikimedia.org/wikipedia/commons/0/0e/Nytimes_hq.jpg':"http://www.nytimes.com/"+filteredValue[0].url,
                        date: singleResult.pub_date.substring(0,10),
                        url: singleResult.web_url,
                        id:singleResult.web_url,
                        switch: this.props.appstate.switchChecked      
                    }
                })
                this.setState({array:temp,statusl:false,statusd:'none'}, () => console.log("data",array))
                this.forceUpdate();
            });
        }
        else if(this.props.appstate.switchChecked === true){
           
            fetch('/api/guardiansearch?articlesearch='+queryString.parse(this.props.location.search).q)
            .then(res => res.json()).then(array => {
                const tempguardian = array.response.results.map(singleResult => {
                    var imageguardian
                     try{
                        const len = singleResult.blocks.main.elements[0].assets.length

                           imageguardian = singleResult.blocks.main.elements[0].assets[len-1].file
 
                      }
                      catch(error){
                          imageguardian = 'https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png'
                      }
                     return {
                         title:singleResult.webTitle,
                         section:singleResult.sectionId,
                         image: imageguardian,
                         date: singleResult.webPublicationDate.substring(0,10),
                         url: singleResult.webUrl,
                         id: singleResult.id,
                         switch: this.props.appstate.switchChecked    
                     }
                 })
                 this.setState({array:tempguardian,statusl:false,statusd:'none'}, () => console.log("data",array))
                 this.forceUpdate();
             });

        }
      }

      componentDidUpdate(prevProps){
        if(this.props.location.search!==prevProps.location.search || this.props.location.pathname!==prevProps.location.pathname){
           this.componentDidMount();      
      }
    }
    render() {
        return (
            <>
            <div className="loader" style={{position:'absolute',top:'45%',left:'45%'}}>
            <BounceLoader
                css={override}
                size={36}
                margin={"auto"}
                color={"#123abc"}
                loading={this.state.statusl}
            /><p style={{fontWeight:"500",margin:"0",fontSize:"1.4em",display:this.state.statusd, textAlign:'center'}}>Loading</p>
            </div>
            <h3 style={{marginLeft:'.76em',marginTop:'.50em'}}>Results</h3>
            <div className={'favGrid'}>
                {
                    
                    this.state.array.map((value) => {
                     return <SearchCard record = {value}/>
                })
               }

            </div>
            </>
        )
    }
}

export default Search

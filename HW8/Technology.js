import React, { Component } from 'react';
import Cards from './Cards';
import { css } from "@emotion/core";
import BounceLoader from "react-spinners/BounceLoader";

const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;

export class Technology extends Component {
    constructor(){
        super();
        this.state = {
            array: [],
            statusl : false,
            statusd : 'none'
        }
    }
    componentDidMount() {
        console.log(this.props.appstate.switchChecked)

        if(this.props.appstate.switchChecked === false){
            this.setState({array:[],statusl:true,statusd:'block'})
            fetch('/api/urltechnologyny')
            .then(res =>res.json()).then(array => {
                console.log(array)
                var temp = array.results.map(singleResult => {

                    var filteredValue = []
                    try{
                    filteredValue = singleResult.multimedia.filter(imgvalue => 
                        imgvalue.width>=2000 )
                    }
                    catch(e){
                        filteredValue[0]=undefined
                    }
                    try{
                    return {
                        title:singleResult.title,
                        section:singleResult.section,
                        description: singleResult.abstract,
                        image: filteredValue[0]===undefined?'https://upload.wikimedia.org/wikipedia/commons/0/0e/Nytimes_hq.jpg':filteredValue[0].url,
                        published_date: singleResult.published_date,
                        url: singleResult.url,
                        id:singleResult.url     
                    }
                }
                catch(e){
                    return 'Invalid Record'
                }
                })
                temp = temp.filter(r => r!=='Invalid Record')
                this.setState({array:temp,statusl:false,statusd:'none'}, () => console.log("data",array))
            });
        }
        else if(this.props.appstate.switchChecked === true){
            this.setState({array:[],statusl:true,statusd:'block'})
            fetch('/api/urltechguardian')
            .then(res => res.json()).then(array => {
                console.log(array)
                var tempguardian = array.response.results.map(singleResult => {
                   var imageguardian
                   try{
                    const len = singleResult.blocks.main.elements[0].assets.length

                         imageguardian = singleResult.blocks.main.elements[0].assets[len-1].file

                    }
                    catch(error){
                        imageguardian = 'https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png'
                    }
                    try{
                    return {
                        title:singleResult.webTitle,
                        section:singleResult.sectionId,
                        description: singleResult.blocks.body[0].bodyTextSummary.length>390?singleResult.blocks.body[0].bodyTextSummary.replace(/^([\s\S]{390}[^\s]*)[\s\S]*/, "$1")+"...":singleResult.blocks.body[0].bodyTextSummary,
                        image: imageguardian,
                        published_date: singleResult.webPublicationDate,
                        url: singleResult.webUrl,
                        id: singleResult.id       
                    }
                }
                catch(e){
                    return 'Invalid Record'
                }
                })
                tempguardian = tempguardian.filter(r => r!=='Invalid Record')
                this.setState({array:tempguardian,statusl:false,statusd:'none'}, () => console.log("data",array))
            });
        }
       
      }
      componentDidUpdate(p){
          if(this.props.appstate.switchChecked === p.appstate.switchChecked)
            return
        this.componentDidMount();
      }
    render() {
        console.log(this.state.array)
        return (
            <div>

                <div className="loader" style={{position:'absolute',top:'45%',left:'45%'}}>
                <BounceLoader
                    css={override}
                    size={36}
                    margin={"auto"}
                    color={"#123abc"}
                    loading={this.state.statusl}
                /><p style={{fontWeight:"500",margin:"0",fontSize:"1.4em",display:this.state.statusd, textAlign:'center'}}>Loading</p>
                </div>
                {
                    this.state.array.map((value, index) => {
                    return <Cards nytimesprop={value} checked = {this.props.appstate.switchChecked}/>
                })
               }

            </div>
        )
    }
}

export default Technology;

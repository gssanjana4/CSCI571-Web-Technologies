import React, {Component} from 'react';
import {Card,Accordion, OverlayTrigger, Tooltip} from 'react-bootstrap';
import { ToastContainer, toast,Zoom } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { FacebookShareButton,FacebookIcon, EmailShareButton, TwitterIcon, TwitterShareButton, EmailIcon} from "react-share";
import { FaRegBookmark, FaBookmark} from 'react-icons/fa';
import {FiChevronDown,FiChevronUp} from 'react-icons/fi';
import { css } from "@emotion/core";
import BounceLoader from "react-spinners/BounceLoader";
import Comment from './Comment';


const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;

var descSize

export class Details extends Component {
  
    constructor(){
        super();
        this.state = {
            array: [],
            show: false,
            statusl : false,
            statusd : 'none',
            downButton : 'FiChevronDown'
        }
    }
    

    notify = () => {
        console.log(this.state.array,'lm')
        if(!localStorage.hasOwnProperty(this.state.array[0].id)){
            toast('Saving '+this.state.array[0].title, {
                position: "top-center",
                autoClose: 1800,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                transition:Zoom,
                bodyClassName: 'toaster'
               });
               
                localStorage.setItem(this.state.array[0].id, JSON.stringify({
                    id:this.state.array[0].id,
                    title: this.state.array[0].title,
                    date: this.state.array[0].published_date,
                    section: this.state.array[0].section,
                    switch: this.props.appstate.switchChecked,
                    image: this.state.array[0].image,
                    url:this.state.array[0].url
                  }))
                  this.forceUpdate();
               
        }
        else{
        toast('Removing '+this.state.array[0].title, {
            position: "top-center",
            autoClose: 1800,
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            transition:Zoom,
            bodyClassName: 'toaster'
           });
            localStorage.removeItem(this.state.array[0].id)
            this.forceUpdate();
        }
        
    }


    handleShow = () =>{
        this.setState({show:true})
    }
    handleHide = () =>{
        this.setState({show:false})
    }
    componentDidMount() {
        console.log(this.props.appstate.switchChecked)
        if(this.props.location.state.checked === false){
            this.setState({array:[],statusl:true,statusd:'block'})
            fetch('/api/urlnydetails?article='+this.props.location.search.substring(1,))
            .then(res =>res.json()).then(array => {
                try{
                var ardescr = array.response.docs[0].abstract.split('. ')
                ardescr = ardescr.map(r => {
                    return r+'. '
                })
                ardescr[ardescr.length-1] = ardescr[ardescr.length-1].substr(0,ardescr[ardescr.length-1].length-2)
                descSize = ardescr.length<=4?false:true
                    var filteredValue = array.response.docs[0].multimedia.filter(imgvalue => 
                        imgvalue.width>=2000 )     
                        this.setState({array:[{
                        title:array.response.docs[0].headline.main,
                        section:array.response.docs[0].news_desk,
                        description: ardescr.length>4?ardescr.slice(4,ardescr.length):'',
                        image: filteredValue[0]===undefined?'https://upload.wikimedia.org/wikipedia/commons/0/0e/Nytimes_hq.jpg':"http://www.nytimes.com/"+filteredValue[0].url,
                        published_date: array.response.docs[0].pub_date.substring(0,10 ),
                        url: array.response.docs[0].web_url,
                        id: this.props.location.search.substring(1,),
                        descr: ardescr.length>4?ardescr.slice(0,3):array.response.docs[0].abstract 
                    }],statusl:false,statusd:'none'}) 
                }
                catch(error){
                    console.log("Error")
            } 
                });
        }
        else if(this.props.location.state.checked === true){
            this.setState({array:[],statusl:true,statusd:'block'})
            fetch('/api/guardiandetails?article='+this.props.location.search.substring(1,))
            .then(res => res.json()).then(array => {
                console.log("This is Array:   "+array)
                    const len = array.response.content.blocks.main.elements[0].assets.length
                    var ardescr = array.response.content.blocks.body[0].bodyTextSummary.split('. ')
                    ardescr = ardescr.map(r => {
                        return r+'. '
                    })
                    ardescr[ardescr.length-1] = ardescr[ardescr.length-1].substr(0,ardescr[ardescr.length-1].length-2)
                    descSize = ardescr.length<=4?false:true
                    var imageguardian
                    try{
                        imageguardian = array.response.content.blocks.main.elements[0].assets[len-1].file
                    }
                    catch(error){
                        imageguardian = 'https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png'
                    }
                    this.setState({array:[{
                        title:array.response.content.webTitle,
                        section:array.response.content.sectionId,
                        description: ardescr.length>4?ardescr.slice(4,ardescr.length):'',                        image: imageguardian,
                        published_date: array.response.content.webPublicationDate.substring(0,10),
                        url: array.response.content.webUrl,
                        id: this.props.location.search.substring(1,),
                        descr: ardescr.length>4?ardescr.slice(0,3):array.response.content.blocks.body[0].bodyTextSummary

                    }],statusl:false,statusd:'none'})
                
            });

        }
      }
    render(){
        const changeArrow = () =>{
              
            setTimeout(()=>{
            this.state.downButton === 'FiChevronUp'? window.scroll({top: 0, left: 0, behavior: 'smooth' }):document.getElementById('collapse_text').scrollIntoView({ behavior: 'smooth' })
              this.setState({downButton:this.state.downButton==='FiChevronUp'?'FiChevronDown':'FiChevronUp'})
              },500)
          }
        
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
            {this.state.array.map(record => {
                return (
                <>
               <div className="boxshStyle">
            
               <Card className="cardStyledetails">
                   <Card.Title className="titledetails">{record.title}
                       
                       <div>
                            <p className="datedetails"> {record.published_date}</p>
                           <div className="socialchecksdetails">
                                  <FacebookShareButton
                                   url={record.url}
                                   quote={'CSCI_571_NewsApp'}
                                   className="Demo__some-network__share-button"
                                   >
                                   <OverlayTrigger
                                       placement={'top'}
                                       overlay={
                                       <Tooltip>
                                           Facebook
                                       </Tooltip>
                                       }
                                   >
                                   <FacebookIcon size={26} round />
                                   </OverlayTrigger>
                               </FacebookShareButton>
                               <TwitterShareButton
                                   url={record.url}
                                   hashtags={['CSCI_571_NewsApp']}
                                   className="Demo__some-network__share-button"
                               >
                                   <OverlayTrigger
                                       placement={'top'}
                                       overlay={
                                       <Tooltip>
                                           Twitter
                                       </Tooltip>
                                       }
                                   >
                                   <TwitterIcon size={26} round />
                                   </OverlayTrigger>
                               </TwitterShareButton>
                               <EmailShareButton
                                
                                   url={record.url}
                                   subject={'CSCI_571_NewsApp'}
                                   className="Demo__some-network__share-button"
                               >
                                   <OverlayTrigger
                                       placement={'top'}
                                       overlay={
                                       <Tooltip>
                                           Email
                                       </Tooltip>
                                       }
                                   >
                                    <EmailIcon size={26} round className="emailmargin"/>
                                   </OverlayTrigger>
                               
                               </EmailShareButton>
                             
                               <div onClick={this.notify} className="bookmarkdetails">
                                   <OverlayTrigger
                                       placement={'top'}
                                       overlay={
                                       <Tooltip>
                                           Bookmark
                                       </Tooltip>
                                       }
                                   >
                                    {localStorage.hasOwnProperty(record.id)?<FaBookmark size={23} style={{cursor:'pointer'}}/>:<FaRegBookmark size={23} style={{cursor:'pointer'}} />}
                                   </OverlayTrigger>
                                   </div>
                       </div>
                       </div> 
                   </Card.Title>
                   <ToastContainer /> 
                       <Card.Img variant="top" src={record.image} alt="Image"/>
                       <Card.Body style={{backgroundColor:'white',textAlign:'justify', border:'0px',padding: '0px', margin: '0em'}}>
                           {record.descr}
                       </Card.Body>
                   </Card>
                       <Accordion defaultActiveKey="0" style={{backgroundColor:'white', textAlign:'justify',padding:'0em', border:'0px', marginTop:'1em'}}>
                           <Card style={{display:'block', border: '0px'}}> 
                           <Accordion.Collapse eventKey="1">
                           <Card.Body id='collapse_text' style={{margin: '0px', padding:'1em 0em 0em 0em'}}>
                           {record.description}
                           </Card.Body>             
                           </Accordion.Collapse>
                           {descSize===true?<Accordion.Toggle as='div' variant='link' eventKey='1'  style={{float:'right',cursor:'pointer'}} onClick={changeArrow}>
                           {this.state.downButton==='FiChevronUp'?<FiChevronUp size={29} />:<FiChevronDown size={29} />}                        
                           </Accordion.Toggle>:''}
                           
                           </Card>
                       </Accordion>
               </div> 
               <Comment style={{marginTop:'1em'}} id={this.props.location.search.substring(1,)}/>
               </>)
            })}
            
           </>
        )
    }
}

export default Details;

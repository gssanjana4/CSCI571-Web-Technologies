import React, {Component } from 'react';
import {Card, Badge, Modal} from 'react-bootstrap';
import { MdShare} from 'react-icons/md';
import {Link} from 'react-router-dom';
import Truncate from 'react-truncate';
import { FacebookShareButton,FacebookIcon, EmailShareButton, TwitterIcon, TwitterShareButton, EmailIcon} from "react-share";
export class Cards extends Component {
    
    constructor(){
        super();
        this.state = {
            show:false,
        }
    }
  
    handleShow = () =>{
        this.setState({show:true})
    }
    handleHide = () =>{
        this.setState({show:false})
    }
    render(){
         const l = "/details"
         const s = this.props.nytimesprop.id
        return (
            
              <Card className="cardStyle">
                <div className='imgDivStyle'>
                <Card.Img className="imgStyle" variant="top" src={this.props.nytimesprop.image} /></div>
                <Card.Body className="bodyStyle">
                    <Card.Title> 
                    <Link to={{
              pathname:l,
              search:s,
              state:{
                  checked:this.props.checked
              }
            }} style={{ textDecoration: 'none',color:'black' }}>
               
                      <b>{this.props.nytimesprop.title}</b></Link> <MdShare style={{cursor: 'pointer',zIndex:'1',position:'relative'}} onClick={this.handleShow}/>
                    
                    <Modal
                    show={this.state.show}
        onHide={this.handleHide}
        aria-labelledby="example-custom-modal-styling-title"
      >
        <Modal.Header closeButton>
          <Modal.Title id="example-custom-modal-styling-title" className="titlecard" >
          {this.props.nytimesprop.title}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <p style={{textAlign:'center', marginBottom:'.1em'}}><b>Share via</b></p>
            <div className="socialchecks">
            <div className="socials">
          <FacebookShareButton
            url={this.props.nytimesprop.url}
            quote={'CSCI_571_NewsApp'}
            className="Demo__some-network__share-button"
          >
          
            
            <FacebookIcon size={52} round />
          </FacebookShareButton>  </div>
          <div className="socials">
          <TwitterShareButton
            url={this.props.nytimesprop.url}
            hashtags={['CSCI_571_NewsApp']}
            className="Demo__some-network__share-button"
          >
            <TwitterIcon size={52} round />
          </TwitterShareButton>
          </div>
          <div className="socials">
          <EmailShareButton
            url={this.props.nytimesprop.url}
            subject={'CSCI_571_NewsApp'}
            className="Demo__some-network__share-button"
          >
            <EmailIcon size={52} round />
          </EmailShareButton>
          </div>
          </div>
        </Modal.Body>
       
                    </Modal>
                    </Card.Title>
                    <Card.Text>
                    <Truncate lines={3} ellipsis={<span>...</span>}>
                    {this.props.nytimesprop.description}
                    </Truncate>
                    </Card.Text>
                    <div>   
                    <p>
                      <i style={{fontSize:'1em', fontWeight:'500'}}>{this.props.nytimesprop.published_date.substring(0,10)}</i>
                      <Badge style={{float: 'right', fontSize:'1em'}} variant="info" className={"badgeStyles "+ this.props.nytimesprop.section.toLowerCase()}>{this.props.nytimesprop.section.toUpperCase()}</Badge>                    </p>
                  
                    </div>
                </Card.Body>
               
                </Card> 
              
                    
        )
    }
}



export default Cards;

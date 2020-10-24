import React, { Component } from 'react'
import {Card, Modal, Badge} from 'react-bootstrap';
import { MdShare} from 'react-icons/md';
import {Link} from 'react-router-dom';
import { FacebookShareButton,FacebookIcon, EmailShareButton, TwitterIcon, TwitterShareButton, EmailIcon} from "react-share";

export class SearchCard extends Component {
    constructor(){
        super();
        this.state = {
            show:false
        }
    }
    render() {
        const handleShow = () =>{
            this.setState({show:true})
        }
        const handleHide = () =>{
            this.setState({show:false})
        }
        const l = "/details"
        const s = this.props.record.id
        return (
  
            <Card style={boxshStyle}>
                <Card.Title>
                <Link to={{
            pathname:l,
              search:s,
              state:{
                  checked:this.props.record.switch
              }
          }}  style={{ textDecoration: 'none',color:'black', fontWeight:'bold' }}>  
                {this.props.record.title.length>50?this.props.record.title.replace(/^([\s\S]{50}[^\s]*)[\s\S]*/, "$1")+"...":this.props.record.title}</Link>
                <MdShare style={{cursor: 'pointer',zIndex:'1',position:'relative'}} onClick={() => handleShow()}/>
                <Modal
                    show={this.state.show}
        onHide={()=>handleHide()}
        aria-labelledby="example-custom-modal-styling-title"
      >
        <Modal.Header closeButton>
          <Modal.Title style={titlecss} id="example-custom-modal-styling-title">
          {this.props.record.title}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <p style={{textAlign:'center'}}><b>Share via</b></p>
            <div className="socialchecks">
            <div className="socials">
          <FacebookShareButton
            url={this.props.record.url}
            quote={'CSCI_571_NewsApp'}
            className="Demo__some-network__share-button"
          >       
            
            <FacebookIcon size={52} round />
          </FacebookShareButton>  </div><div className="socials">
          <TwitterShareButton
           url={this.props.record.url}
            hashtags={['CSCI_571_NewsApp']}
            className="Demo__some-network__share-button"
          >
            <TwitterIcon size={52} round />
          </TwitterShareButton>
          </div>
          <div  className="socials">
          <EmailShareButton
            url={this.props.record.url}
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
                    <Card.Img style={imgStyle} variant="top" src={this.props.record.image} alt="Image"/>
                   
                    <Card.Body style={{margin: '0px', padding: '.3em'}}>
                    <i>{this.props.record.date}</i>
                    <div style={{float: 'right'}}>
                    <Badge className={"badgeStyles "+ this.props.record.section.toLowerCase()}  variant="info">{this.props.record.section.toUpperCase()}</Badge>
                    </div>
                    </Card.Body>
                </Card>
                
        )
    }
}

const boxshStyle = {
    border:'1px solid #cacaca',
    boxShadow: ' 5px 6px 15px #b8b5b5',
    marginBottom: '1em',
    marginLeft: '1em',
    marginRight:'1em',
    padding: '1em',
    display: 'block'
}

const titlecss = {
    fontSize:'20px',
    padding: '0px',
    margin: '0px'
}

const imgStyle = {
    height: '13.5em',
    width: '100%',
    padding: '.21em',
    border:'.5px solid #cacaca',
    borderRadius: '.3em',
}

export default SearchCard;

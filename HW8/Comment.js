import React, { Component } from 'react';
import commentBox from 'commentbox.io';

export class Comment extends Component {
    componentDidMount() {
        commentBox('5631900068610048-proj');
    }
    render() {
        return (
            <div class="commentbox" id={this.props.id}></div>
        )
    }
}

export default Comment

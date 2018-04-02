import React, {Component} from 'react';
import {connect} from 'react-redux'

import '../less/app.less'
import * as action from '../actions/receipt';
import Header from './Header';


class App extends Component {
    constructor() {
        super();
        this.state = {
            selectId: 1,
            shopCart: [],
        }
    }

    render(){
        return <div>
            <Header/>
        </div>
    }
}

const mapStateToProps = (state) => {
    return {

    }
};

const mapDispatchToProps = (dispatch) => {
    return {

    }
};

export default connect(mapStateToProps, mapDispatchToProps)(App);

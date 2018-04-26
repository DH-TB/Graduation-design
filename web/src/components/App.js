import React, {Component} from 'react';
import {connect} from 'react-redux'
import Carousel from './Carousel';
import LoadMoreList from './hotList';
import '../less/app.less'
import Header from './Header';
import {Layout} from 'antd';
import * as action from '../actions/music';

const {Content, Footer} = Layout;

class App extends Component {
    constructor() {
        super();
        this.state = {
            selectId: 1,
            shopCart: [],
        }
    }

    render(){
        return <Layout className="layout">
                <Header searchMusicByName={this.props.searchMusicByName}
                        searchMusic={this.props.searchMusic}/>
                <Content className='content'>
                    {this.props.children}
                </Content>
                <Footer style={{textAlign: 'center'}}>
                    Ant Design ©2016 Created by Ant UED
                </Footer>
            </Layout>
    }
}

const mapStateToProps = (state) => {
    return {
        searchMusic:state.searchMusic
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        searchMusicByName:(name)=>dispatch(action.searchMusicByName(name))
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(App);

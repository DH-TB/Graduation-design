import React, {Component} from 'react';
import {Layout, Divider, Input, AutoComplete, Icon} from 'antd';
import {Link} from 'react-router-dom';
import logo from '../img/logo.jpg';

import user from '../img/user.png';

const Search = Input.Search;
const {Header, Content, Footer} = Layout;

class Headers extends Component {

    constructor() {
        super()
        this.state = {
            finsMusic: []
        }
    }

    handleSearch(Name) {
        this.props.searchMusicByName(Name);
        console.log(Name);
        const searchMusic = this.props.searchMusic;
        this.setState({
            dataSource: searchMusic
        })
    }

    LinkToLogin() {
        window.location.href = 'http://localhost:3000/login';
    }

    LinkToRegister(){
        window.location.href = 'http://localhost:3000/register';
    }

    render() {
        return (
            <Header className='header'>
                <img src={logo} className='logo-size'/>

                <Search className='search'
                        placeholder="input search text"
                        style={{width: 300}}
                />

                <span className='login-register'>
                        <a onClick={this.LinkToLogin.bind(this)}>登陆</a>
                        <Divider type="vertical"/>
                        <a onClick={this.LinkToRegister.bind(this)}>注册</a>
                        <Divider type="vertical"/>
                        <img src={user} className='user-size'/>
                    </span>

                <AutoComplete
                    className="certain-category-search"
                    dropdownClassName="certain-category-search-dropdown"
                    dropdownMatchSelectWidth={false}
                    dropdownStyle={{width: '300px'}}
                    size="large"
                    style={{width: '100%'}}
                    dataSource={this.state.finsMusic}
                    placeholder="input here"
                    optionLabelProp="value" onChange={this.handleSearch.bind(this)}>
                    <Input ref="followed" suffix={<Icon type="search" className="certain-category-icon"/>}/>
                </AutoComplete>


            </Header>
        )
    }

}

export default Headers;

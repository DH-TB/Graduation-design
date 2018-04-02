import React, {Component} from 'react';
import {Layout, Menu,Input} from 'antd';
import Carousel from './Carousel';
import LoadMoreList from './hotList';

const Search = Input.Search;
const {Header, Content, Footer} = Layout;

class Headers extends Component {
    render() {
        return (
            <Layout className="layout">
                <Header>
                    <div className="logo"/>
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={['2']}
                        style={{lineHeight: '64px'}}
                    >
                        <Menu.Item key="1">nav 1</Menu.Item>
                        <Menu.Item key="2">nav 2</Menu.Item>
                        <Menu.Item key="3">nav 3</Menu.Item>
                        <Menu.Item key="4">
                            <Search
                                placeholder="input search text"
                                onSearch={value => console.log(value)}
                                style={{ width: 200,float:'right' }}
                            />
                        </Menu.Item>

                    </Menu>
                </Header>
                <Content style={{padding: '0 50px'}}>
                    <Carousel />
                    <div style={{background: '#fff', padding: 24, minHeight: 280}}>
                        <h3>热门推荐</h3>
                        <LoadMoreList />
                    </div>
                </Content>
                <Footer style={{textAlign: 'center'}}>
                    Ant Design ©2016 Created by Ant UED
                </Footer>
            </Layout>
        )
    }

}

export default Headers;

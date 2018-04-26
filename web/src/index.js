import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux'
import {createStore, applyMiddleware} from 'redux';
import thunk from 'redux-thunk';
import {BrowserRouter, Route} from 'react-router-dom'
import reducer from './reducers/index'
import './less/index.less'

import App from './components/App';
import Register from './components/Register';
import Login from './components/Login';
import CarouselApp from "./components/Carousel";
import Header from "./components/Header";


const store = createStore(reducer, applyMiddleware(thunk));

ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter>
            <div>
                <Route exact path='/register' component={Register}/>
                <Route exact path='/login' component={Login}/>

                <App>
                    <Route exact path='/' component={CarouselApp}/>
                </App>
            </div>
        </BrowserRouter>
    </Provider>,
    document.getElementById('root'));

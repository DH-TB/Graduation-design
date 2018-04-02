import React,{Component} from 'react';
import {Carousel} from 'antd';
import Carousel1 from '../img/1.png';
import Carousel2 from '../img/2.png';
import Carousel3 from '../img/3.png';
import Carousel4 from '../img/4.png';
import Carousel5 from '../img/5.png';

class CarouselApp extends Component{
    render(){
        return (
            <Carousel autoplay className='carousel'>
                <div><img src={Carousel1}></img></div>
                <div><img src={Carousel2}></img></div>
                <div><img src={Carousel3}></img></div>
                <div><img src={Carousel4}></img></div>
                <div><img src={Carousel5}></img></div>
            </Carousel>
        )
    }
}

export default CarouselApp;





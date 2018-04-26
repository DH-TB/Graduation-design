import React, {Component} from 'react';
import {Carousel,Card} from 'antd';
import Carousel1 from '../img/1.png';
import Carousel2 from '../img/2.png';
import Carousel3 from '../img/3.png';
import Carousel4 from '../img/4.png';
import Carousel5 from '../img/5.png';
import LoadMoreList from './hotList';
import Header from './Header';

class CarouselApp extends Component {
    render() {
        return (
            <div>
                {/*<Header searchMusicByName={this.props.searchMusicByName}*/}
                        {/*searchMusic={this.props.searchMusic}/>*/}

                <Carousel autoplay className='carousel'>
                    <div><img src={Carousel1}></img></div>
                    <div><img src={Carousel2}></img></div>
                    <div><img src={Carousel3}></img></div>
                    <div><img src={Carousel4}></img></div>
                    <div><img src={Carousel5}></img></div>
                </Carousel>
                <Card title="每日推荐" extra={<a href="#">More</a>} style={{ width: 300 }}>
                    <p>111111</p>
                    <p>111111</p>
                    <p>111111</p>

                </Card>

                <div className='line'/>
                <div className='music-recommend'>
                    <h3>热门推荐</h3>
                    <LoadMoreList/>
                </div>
            </div>

        )
    }
}

export default CarouselApp;





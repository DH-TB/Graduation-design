import * as request from '../constant/request';

export const getMusicByName = (music)=>{
    return {
        type:'GET_MUSIC_BY_NAME',
        music
    }
};

export const getReceiptText = (receipt)=>{
    return {
        type:'GET_RECEIPT_TEXT',
        receipt
    }
};

export const searchMusicByName = (music) => {
    return dispatch => {
        (async () => {
            const res = await request.get(`../api/music/${music}`);
            if (res.status === 200) {
                dispatch(getMusicByName(res.body))
            }
        })()
    }
};


export const getReceipt = (shopCart) => {
    return dispatch => {
        (async () => {
            const res = await request.get(`../api/receipt/${shopCart}`);
            if (res.status === 200) {
                dispatch(getReceiptText(res.body))
            }
        })()
    }
};

export default (state = [], action) => {
    switch (action.type) {
        case 'GET_MUSIC_BY_NAME':
            return action.music;
        default:
            return state
    }
}

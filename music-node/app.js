const express = require('express');
const app = express();

import Music from './server/route';

app.get(`/music/:music`, (req, res, next) => {
    const music = req.params.music;
    console.log(21);
    Music.find({music: { $regex: music, $options: 'i' }}, (err, data) => {
        if (err)
            return next(err);
        if (data) {
            console.log(data);
            res.send(data)
        }
    })
});

app.listen(8888, function () {
    console.log('server started at http://localhost:8888');
});

module.exports = app;



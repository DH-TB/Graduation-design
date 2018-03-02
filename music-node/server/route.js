let mongoose = require('mongoose');

let db = mongoose.connect('mongodb://localhost:27017/music163');
db.connection.on("open", function () {
    console.log("数据库连接成功");
});

db.connection.on("error", function (error) {
    console.log("数据库连接失败：" + error);
});

let Schema = mongoose.Schema;
let ObjectId = Schema.Types.ObjectId;

let Music1 = mongoose.model('Music1',{
    _id: { type: ObjectId},
    id: String,
    album: String,
    music: String,
    comments: [{
        content: String,
        nickname:String,
        likedcount:Number,
        avatarurl:String
    }],
    artist:String
});

module.exports = Music1;
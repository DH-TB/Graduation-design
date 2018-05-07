INSERT INTO user(id,username,password,image) VALUES (1,'huanglizhen','123456','http://p1.music.126.net/G3XBOolilpjP16HComDnaw==/18605935767337300.jpg');
INSERT INTO user(id,username,password,image) VALUES (2,'huangli','123456','http://p1.music.126.net/G3XBOolilpjP16HComDnaw==/18605935767337300.jpg');
INSERT INTO user(id,username,password,image) VALUES (3,'huang','123456','http://p1.music.126.net/G3XBOolilpjP16HComDnaw==/18605935767337300.jpg');

INSERT INTO userStyle(id,userId,jazz,rock,hiphop,light,blues,classical,pop,heavyMetal,rap,folk) VALUES (1,1,'0.1','0.2','0.3','0.4','0.5','0.5','0.6','0.7','0.8','0.9');


INSERT  INTO singer(id,tag,singer) VALUES (1,'classical','许嵩');
INSERT  INTO singer(id,tag,singer) VALUES (2,'light','周杰伦');
INSERT  INTO singer(id,tag,singer) VALUES (3,'rock','陈奕迅');

INSERT  INTO singerLeaderBoard(id,singerId,tag,hot) VALUES (1,1,'classical',10000);
INSERT  INTO singerLeaderBoard(id,singerId,tag,hot) VALUES (2,2,'light',10000);
INSERT  INTO singerLeaderBoard(id,singerId,tag,hot) VALUES (3,3,'rock',10000);

INSERT  INTO music(id,singerId,music,album,type,collected,image) VALUES (1,1,'有何不可','有何不可','classical',1,'http://p1.music.126.net/DMQkydqulX-yjX0U9RLKmQ==/109951163102605700.jpg');
INSERT  INTO music(id,singerId,music,album,type,collected,image) VALUES (2,2,'最长的电影','最长的电影','light',1,'http://p1.music.126.net/DMQkydqulX-yjX0U9RLKmQ==/109951163102605700.jpg');
INSERT  INTO music(id,singerId,music,album,type,collected,image) VALUES (3,3,'红玫瑰','红玫瑰','rock',0,'http://p1.music.126.net/DMQkydqulX-yjX0U9RLKmQ==/109951163102605700.jpg');

INSERT  INTO musicLeaderBoard(id,musicId,type,hot,singerId) VALUES (1,1,'classical',10000,1);
INSERT  INTO musicLeaderBoard(id,musicId,type,hot,singerId) VALUES (2,2,'light',10000,2);
INSERT  INTO musicLeaderBoard(id,musicId,type,hot,singerId) VALUES (3,3,'rock',10000,3);

INSERT  INTO collection(id,userId,musicId,love,count) VALUES (1,1,1,5,1000);
INSERT  INTO collection(id,userId,musicId,love,count) VALUES (2,2,2,5,1000);
INSERT  INTO collection(id,userId,musicId,love,count) VALUES (3,3,3,5,1000);

INSERT  INTO comment(id,musicId,userId,count,content,isLike,nickName,image) VALUES (1,1,1,1,'非常好听',1,'小苹果','http://p1.music.126.net/G3XBOolilpjP16HComDnaw==/18605935767337300.jpg');
INSERT  INTO comment(id,musicId,userId,count,content,isLike,nickName,image) VALUES (2,2,2,1,'喜欢这首歌',0,'吧拉拉','http://p1.music.126.net/G3XBOolilpjP16HComDnaw==/18605935767337300.jpg');
INSERT  INTO comment(id,musicId,userId,count,content,isLike,nickName,image) VALUES (3,3,3,1,'超赞',1,'小兔子','http://p1.music.126.net/G3XBOolilpjP16HComDnaw==/18605935767337300.jpg');


-- INSERT  INTO musicStyle(id,musicId,jazz,rock,hiphop,light,blues,classical,pop,heavyMetal,rap,folk) VALUES (1,1,'123456');
-- INSERT  INTO userStyle(id,userId,jazz,rock,hiphop,light,blues,classical,pop,heavyMetal,rap,folk) VALUES (1,1,'123456');

-- music -> comment
-- comment - user
--
-- collection - music
-- user -> collection
--
-- user - userStyle
-- music - musicStyle
--
-- musicLeaderBoard -> music
-- signerLeaderBoard -> signer
-- signer -> music
INSERT INTO user(id,username,password) VALUES (1,'huanglizhen','123456');
INSERT INTO user(id,username,password) VALUES (2,'huangli','123456');
INSERT INTO user(id,username,password) VALUES (3,'huang','123456');

INSERT  INTO signer(id,tag,signer) VALUES (1,'classical','许嵩');
INSERT  INTO signer(id,tag,signer) VALUES (2,'light','周杰伦');
INSERT  INTO signer(id,tag,signer) VALUES (3,'rock','陈奕迅');

INSERT  INTO signerLeaderBoard(id,signerId,tag,hot) VALUES (1,1,'classical',10000);
INSERT  INTO signerLeaderBoard(id,signerId,tag,hot) VALUES (2,2,'light',10000);
INSERT  INTO signerLeaderBoard(id,signerId,tag,hot) VALUES (3,3,'rock',10000);

INSERT  INTO music(id,signerId,musicName,album,type) VALUES (1,1,'有何不可','有何不可','classical');
INSERT  INTO music(id,signerId,musicName,album,type) VALUES (2,2,'最长的电影','最长的电影','light');
INSERT  INTO music(id,signerId,musicName,album,type) VALUES (3,3,'红玫瑰','红玫瑰','rock');

INSERT  INTO musicLeaderBoard(id,musicId,tag,type,hot) VALUES (1,1,'classical','classical',10000);
INSERT  INTO musicLeaderBoard(id,musicId,tag,type,hot) VALUES (2,2,'light','light',10000);
INSERT  INTO musicLeaderBoard(id,musicId,tag,type,hot) VALUES (3,3,'rock','rock',10000);

INSERT  INTO collection(id,userId,musicId,love,playCount) VALUES (1,1,1,5,1000);
INSERT  INTO collection(id,userId,musicId,love,playCount) VALUES (2,2,2,5,1000);
INSERT  INTO collection(id,userId,musicId,love,playCount) VALUES (3,3,3,5,1000);

INSERT  INTO comment(id,musicId,userId,count,content) VALUES (1,1,1,1,'非常好听');
INSERT  INTO comment(id,musicId,userId,count,content) VALUES (2,2,2,1,'喜欢这首歌');
INSERT  INTO comment(id,musicId,userId,count,content) VALUES (3,3,3,1,'超赞');

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
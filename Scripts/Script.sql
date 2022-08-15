SELECT * FROM ship_image;
DELETE FROM ship_image;
-- MySQL
    create table ship_image(
        shipId int ,
        fileName varchar(100) not null,
        uploadPath varchar(200) not null,
        uuid varchar(100)not null ,
        primary key (uuid),
        foreign key (shipId) references shipTBL(shipId)
    );
    
   create table shipTBL(
    shipId int primary key auto_increment,
    shipName varchar(50)   not null,
    shipPrice int not null,
    shipStock int not null,
    shipIntro text,
    regDate timestamp default now(),
    updateDate timestamp default now()
);
SELECT * FROM shipTBL;


CREATE Database spaceDB;

create database spaceDB;

create database spaceDB;
use spaceDB

CREATE table userTBL(
user_no int primary key auto_increment,
user_id varchar(30) not null unique, -- 회원아이디
user_pw char(60) not null, -- 패스워드
user_nick varchar(20) not null unique, -- 닉네임
user_phone varchar(20) not null, -- 전화번호 
user_email varchar(50) not null unique, -- 이메일
user_adult boolean default false not null, -- 성인여부
email_check boolean default false not null, -- 이메일 수신여부
adminCk int default false not null,
user_insertDate datetime default(current_time) -- 가입날짜
);
drop table userTBL;
UPDATE userTBL set adminCk=1 WHERE user_nick='방사능라면'   ;
select * from userTBL;
select * from userTBL;
DELETE FROM userTBL  WHERE user_email='jmlee7100@naver.com';


